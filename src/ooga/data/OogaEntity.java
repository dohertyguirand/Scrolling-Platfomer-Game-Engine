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
  private DoubleProperty myXPos = new SimpleDoubleProperty();
  private DoubleProperty myYPos = new SimpleDoubleProperty();
  private DoubleProperty myWidth;
  private DoubleProperty myHeight;

  private List<Double> myVelocity;

  private List<MovementBehavior> myMovementBehaviors;
  private Map<String,List<CollisionBehavior>> myCollisionBehaviors;
  private Map<String,List<ControlsBehavior>> myControls;
  private boolean isDestroyed;
  private String myName;

  public OogaEntity() {
    myVelocity = List.of(0.,0.);
    myXPos.set(0);
    myYPos.set(0);
    myWidth = new SimpleDoubleProperty(100);
    myHeight = new SimpleDoubleProperty(100);
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
    if (myControls.get(controls) == null) { return; }
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
    myXPos.set(myXPos.get() + xDistance);
    myYPos.set(myYPos.get() + yDistance);
  }

  @Override
  public List<Double> getPosition() {
    return List.of(myXPos.get(),myYPos.get());
  }

  @Override
  public List<Double> getVelocity(){return myVelocity;}

  @Override
  public double getWidth() {
    //TODO: Make this reflect the entity's width.
    return myWidth.getValue();
  }

  @Override
  public double getHeight() {
    //TODO: Make this reflect the entity's height.
    return myHeight.getValue();
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
