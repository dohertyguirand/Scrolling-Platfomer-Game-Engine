package ooga.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Consumer;
import javafx.beans.property.*;
import ooga.game.GameInternal;
import ooga.game.behaviors.CollisionBehavior;
import ooga.game.behaviors.ConditionalBehavior;
import ooga.game.behaviors.ControlsBehavior;
import ooga.Entity;
import ooga.game.behaviors.FrameBehavior;
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

  private List<FrameBehavior> myFrameBehaviors;
  private Map<String,List<CollisionBehavior>> myCollisionBehaviors;
  private Map<String,List<ControlsBehavior>> myControls;
  private List<ConditionalBehavior> myConditionalBehaviors;
  private boolean isDestroyed;
  private List<Entity> myCreatedEntities = new ArrayList<>();

  public OogaEntity(double xPos, double yPos, double width, double height) {
    myVelocity = List.of(0.,0.);
    this.xPos.set(xPos);
    this.yPos.set(yPos);
    this.width.set(width);
    this.height.set(height);
    myCollisionBehaviors = new HashMap<>();
    myFrameBehaviors = new ArrayList<>();
    myControls = new HashMap<>();
    myConditionalBehaviors = new ArrayList<>();
    myVelocityVectors = new Stack<>();
    myName = "";
  }

  @Override
  public String getName() {
    return myName;
  }

  @Override
  public double getX() {
    return xPos.get();
  }

  @Override
  public DoubleProperty xProperty() {
    return xPos;
  }

  @Override
  public double getY() {
    return yPos.get();
  }

  @Override
  public DoubleProperty yProperty() {
    return yPos;
  }

  @Override
  public boolean isActiveInView() {
    return activeInView.get();
  }

  @Override
  public BooleanProperty activeInViewProperty() {
    return activeInView;
  }

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
  public void setMovementBehaviors(List<FrameBehavior> behaviors) {
    myFrameBehaviors = behaviors;
  }

  @Override
  public void reactToControls(String controls) {
    if (!myControls.containsKey(controls)) {
      return;
    }
    for (ControlsBehavior behavior : myControls.get(controls)) {
      behavior.reactToControls(this);
    }
  }

  @Override
  public void reactToControlsPressed(String controls) {
    //TODO: remove this method?
    System.out.println(controls);
    reactToControls(controls);
  }

  @Override
  public void updateSelf(double elapsedTime, Map<String, Double> variables) {
    for (FrameBehavior behavior : myFrameBehaviors) {
      behavior.doFrameUpdate(elapsedTime,this, variables);
    }
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
    moveByVelocity(elapsedTime);
  }

  @Override
  public void setCollisionBehaviors(Map<String, List<CollisionBehavior>> behaviorMap) {
    myCollisionBehaviors = new HashMap<>(behaviorMap);
  }

  @Override
  public void setControlsBehaviors(Map<String, List<ControlsBehavior>> behaviors) {
    myControls = new HashMap<>(behaviors);
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

  //TODO: Implement the lambda (after testing) to vertical collisions
  @Override
  public void handleVerticalCollision(Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    if (myCollisionBehaviors.containsKey(collidingEntity.getName())) {
      for (CollisionBehavior behavior : myCollisionBehaviors.get(collidingEntity.getName())) {
        behavior.doVerticalCollision(this, collidingEntity,elapsedTime, variables, game);
      }
    }
  }

  @Override
  public void handleHorizontalCollision(Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    doAllCollisions(collidingEntity, behavior -> behavior.doHorizontalCollision(this,collidingEntity, elapsedTime,
        variables, game));
  }

  private void doAllCollisions(Entity collidingEntity, Consumer<CollisionBehavior> collisionType) {
    if (myCollisionBehaviors.containsKey(collidingEntity.getName())) {
      for (CollisionBehavior behavior : myCollisionBehaviors.get(collidingEntity.getName())) {
        collisionType.accept(behavior);
      }
    }
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
    myVelocity = List.of(myVelocity.get(0) + xChange, myVelocity.get(1) + yChange);
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
  public void reactToVariables(Map<String, Double> variables) {
    //for each variable,
    for (String varName : variables.keySet()) {
      if (propertyVariableDependencies.containsKey(varName)) {
        String propertyName = propertyVariableDependencies.get(varName);
        if (propertyUpdaters.containsKey(propertyName)) {
          propertyUpdaters.get(propertyName).accept(variables.get(varName));
        } else {
          System.out.println("no method defined for setting " + propertyName + " property to a variable");
        }
      }
    }
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
  public void doConditionalBehaviors(double elapsedTime, List<String> inputs, Map<String, Double> variables,
                                     List<Entity> verticalCollisions, List<Entity> horizontalCollisions, GameInternal gameInternal) {
    for (ConditionalBehavior conditionalBehavior : myConditionalBehaviors) {
      conditionalBehavior.doConditionalUpdate(elapsedTime, this, variables, inputs, verticalCollisions, horizontalCollisions, gameInternal);
    }
  }

  @Override
  public boolean hasCollisionWith(String entityType) {
    return myCollisionBehaviors.containsKey(entityType);
  }
}
