package ooga.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.*;
import ooga.CollisionBehavior;
import ooga.ControlsBehavior;
import ooga.Entity;
import ooga.MovementBehavior;
import ooga.game.EntityInternal;

public abstract class OogaEntity implements Entity, EntityInternal {

  private BooleanProperty activeInView = new SimpleBooleanProperty(true);
  protected DoubleProperty xPos = new SimpleDoubleProperty();
  protected DoubleProperty yPos = new SimpleDoubleProperty();
  protected DoubleProperty width = new SimpleDoubleProperty();
  protected DoubleProperty height = new SimpleDoubleProperty();

  private List<Double> myVelocity;

  private List<MovementBehavior> myMovementBehaviors;
  private Map<String,List<CollisionBehavior>> myCollisionBehaviors;
  private Map<String,List<ControlsBehavior>> myControls;
  private boolean isDestroyed;
  private String myName;

  public OogaEntity() {
    myVelocity = List.of(0.,0.);
    xPos.set(0);
    yPos.set(0);
    width.set(100);
    height.set(100);
    myCollisionBehaviors = new HashMap<>();
    myMovementBehaviors = new ArrayList<>();
    myControls = new HashMap<>();
  }

  public OogaEntity(String name) {
    this();
    myName = name;
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
  public boolean isDestroyed() {
    return isDestroyed;
  }

  @Override
  public void setPosition(List<Double> newPosition) {
    xPos.set(newPosition.get(0));
    yPos.set(newPosition.get(1));
  }

  @Override
  public void destroySelf() {
    isDestroyed = true;
  }

  private void moveByVelocity(double elapsedTime) {
    move(myVelocity.get(0) * elapsedTime,myVelocity.get(1) * elapsedTime);
  }

  @Override
  public void changeVelocity(double xChange, double yChange) {
    myVelocity = List.of(myVelocity.get(0) + xChange, myVelocity.get(1) + yChange);
  }

  @Override
  public void setVelocity(double xVelocity, double yVelocity) {
    myVelocity = List.of(xVelocity, yVelocity);
  }

  @Override
  public void setMovementBehaviors(List<MovementBehavior> behaviors) {
    myMovementBehaviors = behaviors;
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
  public void updateSelf(double elapsedTime) {
    for (MovementBehavior behavior : myMovementBehaviors) {
      behavior.doMovementUpdate(elapsedTime,this);
    }
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

  @Override
  public void handleCollision(String collidingEntity) {
    if (myCollisionBehaviors.containsKey(collidingEntity)) {
      for (CollisionBehavior behavior : myCollisionBehaviors.get(collidingEntity)) {
        behavior.doCollision(this, collidingEntity);
      }
    }
  }

  @Override
  public void move(double xDistance, double yDistance) {
    xPos.set(xPos.get() + xDistance);
    yPos.set(yPos.get() + yDistance);
  }

  @Override
  public List<Double> getPosition() {
    return List.of(xPos.get(), yPos.get());
  }

  @Override
  public List<Double> getVelocity() {
    return new ArrayList<>(myVelocity);
  }
}
