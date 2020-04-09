package ooga.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.*;
import ooga.CollisionBehavior;
import ooga.ControlsBehavior;
import ooga.EntityAPI;
import ooga.MovementBehavior;
import ooga.game.EntityInternal;

public abstract class Entity implements EntityAPI, EntityInternal {

  private BooleanProperty activeInView = new SimpleBooleanProperty(true);
  private DoubleProperty myXPos = new SimpleDoubleProperty();
  private DoubleProperty myYPos = new SimpleDoubleProperty();
  private List<Double> myVelocity;

  private List<ControlsBehavior> myControlsBehaviors;
  private List<MovementBehavior> myMovementBehaviors;
  private Map<String,List<CollisionBehavior>> myCollisionBehaviors;
  private Map<String,List<ControlsBehavior>> myControls;
  private boolean isDestroyed;

  public Entity() {
    myVelocity = List.of(0.,0.);
    myXPos.set(0);
    myYPos.set(0);
  }

  @Override
  public double getX() {
    return myXPos.get();
  }

  @Override
  public DoubleProperty xProperty() {
    return myXPos;
  }

  @Override
  public double getY() {
    return myYPos.get();
  }

  @Override
  public DoubleProperty yProperty() {
    return myYPos;
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
  public void setActiveInView(boolean activeInView) {
    this.activeInView.set(activeInView);
  }

  @Override
  public void reactToControls(String controls) {
    for (ControlsBehavior behavior : myControls.get(controls)) {
      behavior.reactToControls(this);
    }
  }

  @Override
  public void updateSelf(double elapsedTime) {
    for (MovementBehavior behavior : myMovementBehaviors) {
      behavior.doMovementUpdate(elapsedTime,this);
    }
  }

  @Override
  public void setCollisionBehaviors(Map<String, List<CollisionBehavior>> behaviorMap) {
    myCollisionBehaviors = new HashMap<>(behaviorMap);
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
    myXPos.set(myXPos.get() + xDistance);
    myYPos.set(myYPos.get() + yDistance);
  }

  @Override
  public List<Double> getPosition() {
    return List.of(myXPos.get(),myYPos.get());
  }

  @Override
  public double getWidth() {
    //TODO: Make this reflect the entity's width.
    return 100;
  }

  @Override
  public double getHeight() {
    //TODO: Make this reflect the entity's height.
    return 100;
  }

  @Override
  public boolean isDestroyed() {
    return isDestroyed;
  }

  @Override
  public void setPosition(List<Double> newPosition) {
    myXPos.set(newPosition.get(0));
    myYPos.set(newPosition.get(1));
  }

  @Override
  public void destroySelf() {
    isDestroyed = true;
  }

  @Override
  public void moveByVelocity() {
    move(myVelocity.get(0),myVelocity.get(1));
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
}
