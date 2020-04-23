package ooga.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Consumer;
import javafx.beans.property.*;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.ConditionalBehavior;
import ooga.Entity;
import ooga.game.EntityInternal;

public abstract class OogaEntity implements Entity, EntityInternal {

  public static double FRICTION_ACCELERATION = 30.0 / 1000.0;

  private BooleanProperty activeInView = new SimpleBooleanProperty(true);
  protected DoubleProperty xPos = new SimpleDoubleProperty();
  protected DoubleProperty yPos = new SimpleDoubleProperty();
  protected DoubleProperty width = new SimpleDoubleProperty();
  protected DoubleProperty height = new SimpleDoubleProperty();
  protected String myName;
  protected Map<String, String> propertyVariableDependencies = new HashMap<>();
  protected Map<String, Consumer<Double>> propertyUpdaters = new HashMap<>(){{
    put("XPos", variableValue -> xPos.set(variableValue));
    put("YPos", variableValue -> yPos.set(variableValue));
    put("Width", variableValue -> width.set(variableValue));
    put("Height", variableValue -> height.set(variableValue));
  }};

  private List<Double> myVelocity;
  private Stack<List<Double>> myVelocityVectors; //keeps track of one-frame movements.

  private List<ConditionalBehavior> myConditionalBehaviors;
  private boolean isDestroyed;
  private List<Entity> myCreatedEntities = new ArrayList<>();
  private static final String[] directions = new String[]{"Up", "Down", "Left", "Right"};
  private Map<String, Boolean> blockedMovements = new HashMap<>();
  private Map<String, String> myVariables = new HashMap<>();

  public OogaEntity(double xPos, double yPos, double width, double height) {
    myVelocity = List.of(0.,0.);
    this.xPos.set(xPos);
    this.yPos.set(yPos);
    this.width.set(width);
    this.height.set(height);
    myConditionalBehaviors = new ArrayList<>();
    myVelocityVectors = new Stack<>();
    myName = "";
    for(String direction : directions){
      blockedMovements.put(direction, false);
    }
  }

  @Override
  public String getName() { return myName; }

  @Override
  public double getX() { return xPos.get(); }

  @Override
  public DoubleProperty xProperty() { return xPos; }

  @Override
  public double getY() { return yPos.get(); }

  @Override
  public DoubleProperty yProperty() { return yPos; }

  @Override
  public boolean isActiveInView() { return activeInView.get();}

  @Override
  public BooleanProperty activeInViewProperty() { return activeInView; }

  @Override
  public void setActiveInView(boolean activeInView) { this.activeInView.set(activeInView); }

  @Override
  public DoubleProperty widthProperty(){ return width; }

  @Override
  public DoubleProperty heightProperty(){ return height; }

  @Override
  public double getWidth() { return width.get(); }

  @Override
  public double getHeight() { return height.get(); }

  @Override
  public void setWidth(double width) { this.width.set(width); }

  @Override
  public void setHeight(double height) { this.height.set(height); }

  @Override
  public void setPosition(List<Double> newPosition) {
    xPos.set(newPosition.get(0));
    yPos.set(newPosition.get(1));
  }

  @Override
  public void updateSelf(double elapsedTime, Map<String, String> variables,
                         GameInternal game) {
    applyFrictionHorizontal(elapsedTime);
    applyFrictionVertical(elapsedTime);
  }

  private void applyFrictionHorizontal(double elapsedTime) {
    if (Math.abs(myVelocity.get(0)) < FRICTION_ACCELERATION) {
      setVelocity(0,getVelocity().get(1));
    }
    else {
      changeVelocity(FRICTION_ACCELERATION * -1 * Math.signum(myVelocity.get(0)),0);
    }
  }

  private void applyFrictionVertical(double elapsedTime) {

    if (Math.abs(myVelocity.get(1)) < FRICTION_ACCELERATION) {
      setVelocity(getVelocity().get(0),0);
    }
    else {
      changeVelocity(0,FRICTION_ACCELERATION * -1 * Math.signum(myVelocity.get(1)));
    }
  }


  @Override
  public void executeMovement(double elapsedTime) {
    double[] directionalVelocities = new double[]{-myVelocity.get(1), myVelocity.get(1), -myVelocity.get(0), myVelocity.get(0)};
    int[] velocityIndexes = new int[]{1, 1, 0, 0};
    for(int i=0; i<directions.length; i++){
      double[] newVelocity = new double[]{myVelocity.get(0), myVelocity.get(1)};
      newVelocity[velocityIndexes[i]] = 0.0;
      if(blockedMovements.get(directions[i]) && directionalVelocities[i] > 0) {
//        System.out.println("blocked info");
//        System.out.println(blockedMovements.toString());
        setVelocity(newVelocity[0], newVelocity[1]);
      }
    }
    moveByVelocity(elapsedTime);
  }

  /**
   * assigns the conditional behaviors of this entity
   *
   * @param conditionalBehaviors list of conditional behaviors
   */
  @Override
  public void setConditionalBehaviors(List<ConditionalBehavior> conditionalBehaviors) {
    myConditionalBehaviors = new ArrayList<>(conditionalBehaviors);
  }

  @Override
  public List<Double> getPosition() {
    return List.of(xPos.get(), yPos.get());
  }

  @Override
  public List<Double> getVelocity() {
    List<Double> ret = new ArrayList<>(myVelocity);
    for (List<Double> vector : myVelocityVectors) {
      ret.set(0,ret.get(0)+vector.get(0));
      ret.set(1,ret.get(1)+vector.get(1));
    }
    return ret;
  }

  @Override
  public boolean isDestroyed() {
    return isDestroyed;
  }

  @Override
  public void destroySelf() {
    isDestroyed = true;
  }

  private void moveByVelocity(double elapsedTime) {
//    move(myVelocity.get(0) * elapsedTime,myVelocity.get(1) * elapsedTime);
    changePosition(myVelocity,elapsedTime);
//    while (!myVelocityVectors.isEmpty()) {
//      changePosition(myVelocityVectors.pop(),elapsedTime);
//      myVelocityVectors.pop();
  }

  private void changePosition(List<Double> velocity, double elapsedTime) {
    xPos.set(xPos.get() + (velocity.get(0) * elapsedTime));
    yPos.set(yPos.get() + (velocity.get(1) * elapsedTime));
  }

  @Override
  public void changeVelocity(double xChange, double yChange) {
    setVelocity(myVelocity.get(0) + xChange, myVelocity.get(1) + yChange);
  }

  @Override
  public void changeVelocity(List<Double> change) {
    changeVelocity(change.get(0),change.get(1));
  }

  @Override
  public void setVelocity(double xVelocity, double yVelocity) {
    myVelocity = List.of(xVelocity, yVelocity);
  }

  @Override
  public void createEntity(Entity e) {
    myCreatedEntities.add(e);
  }

  @Override
  public List<Entity> popCreatedEntities() {
    List<Entity> ret = myCreatedEntities;
    myCreatedEntities = new ArrayList<>();
    return ret;
  }

  @Override
  public void reactToVariables(Map<String, String> variables) {
    //TODO: make this work for entity variables?
    for (String varName : variables.keySet()) {
      if (propertyVariableDependencies.containsKey(varName)) {
        String propertyName = propertyVariableDependencies.get(varName);
        if (propertyUpdaters.containsKey(propertyName)) {
          try{
            propertyUpdaters.get(propertyName).accept(Double.parseDouble(variables.get(varName)));
          } catch (NumberFormatException e){
            System.out.println(variables.get(varName));
            System.out.println("Could not set variable property dependency because variable could not be parsed to double");
          }
        } else {
          System.out.println("no method defined for setting " + propertyName + " property to a variable");
        }
      }
    }
    updateAutomaticEntityVariables();
  }

  /**
   * automatically create/set entity variables containing basic entity information, allowing it to be used in conditions and effects
   */
  private void updateAutomaticEntityVariables() {
    myVariables.put("XVelocity", String.valueOf(myVelocity.get(0)));
    myVariables.put("YVelocity", String.valueOf(myVelocity.get(1)));
    myVariables.put("XPos", String.valueOf(this.xPos));
    myVariables.put("YPos", String.valueOf(this.yPos));
    myVariables.put("Width", String.valueOf(this.width));
    myVariables.put("Height", String.valueOf(this.height));
  }

  @Override
  public String getEntityID(){
    return getVariable("ID");
  }

  @Override
  public Map<String, String> getVariables() {
    return new HashMap<>(myVariables);
  }

  @Override
  public void setPropertyVariableDependencies(Map<String, String> propertyVariableDependencies){
    this.propertyVariableDependencies = propertyVariableDependencies;
  }

  /**
   * Execute the do method on each of this entity's conditional behaviors, which will check the conditions and execute the
   * assigned behavior if true
   */
  @Override
  public void doConditionalBehaviors(double elapsedTime, List<String> inputs, Map<String, String> variables,
                                     Map<Entity, Map<String, List<Entity>>> collisionInfo, GameInternal gameInternal) {
    //System.out.println(getName() + " is updating!");
    for (ConditionalBehavior conditionalBehavior : myConditionalBehaviors) {
      //System.out.println("\tbehavior: " + conditionalBehavior.getClass().toString());
      conditionalBehavior.doConditionalUpdate(elapsedTime, this, variables, inputs, collisionInfo, gameInternal);
    }
  }

  @Override
  public boolean hasCollisionWith(String entityType) {
    //TODO: remove this method
    return true;
    //return myCollisionBehaviors.containsKey(entityType);
  }

  /**
   * change the value in this entity's blockedMovements map to the specified value
   * @param direction up, down, left, or right
   * @param isBlocked true if the entity is blocked in the direction, otherwise false
   */
  @Override
  public void blockInDirection(String direction, boolean isBlocked){
    blockedMovements.put(direction, isBlocked);
  }

  /**
   * change every value in this entity's blockedMovements map to the specified value
   * @param isBlocked true if the entity is blocked in the direction, otherwise false
   */
  @Override
  public void blockInAllDirections(boolean isBlocked){
    blockedMovements.replaceAll((d, v) -> isBlocked);
  }

  /**
   * Adds (or sets) a variable to this entity's variable map
   * @param name name of the variable
   * @param value value of the variable
   */
  @Override
  public void addVariable(String name, String value){ myVariables.put(name, value); }

  /**
   * returns the value of entity variable mapped to name
   * @param name key
   * @return value
   */
  @Override
  public String getVariable(String name){ return myVariables.get(name); }

  /**
   * add all variables to the specified map to this entity's variable map
   *
   * @param variables map of variable names to values
   */
  @Override
  public void setVariables(Map<String, String> variables) { myVariables.putAll(variables); }
}
