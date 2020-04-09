package ooga.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.*;
import ooga.*;
import ooga.game.EntityInternal;
import ooga.game.PhysicsEntity;

public abstract class Entity implements EntityAPI, EntityInternal {

  private BooleanProperty activeInView = new SimpleBooleanProperty(true);
  private DoubleProperty x = new SimpleDoubleProperty();
  private DoubleProperty y = new SimpleDoubleProperty();
  private List<Double> myVelocity;

  private List<ControlsBehavior> myControlsBehaviors;
  private List<MovementBehavior> myMovementBehaviors;
  private Map<String, ControlsBehavior> myReactions;
  private Map<String,List<CollisionBehavior>> myCollisionBehaviors;
  private Map<String,List<ControlsBehavior>> myControls;
  private ReactionBehavior reactionBehavior;
  private double myXPos;
  private double myYPos;
  private boolean isDestroyed;

  public Entity() {
    myVelocity = List.of(0.,0.);
    myXPos = 0;
    myYPos = 0;
  }

  public double getX() {
    return x.get();
  }

  public DoubleProperty xProperty() {
    return x;
  }

  public double getY() {
    return y.get();
  }

  public DoubleProperty yProperty() {
    return y;
  }

  public boolean isActiveInView() {
    return activeInView.get();
  }

  public BooleanProperty activeInViewProperty() {
    return activeInView;
  }

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
  public void react(String controlKey, String collidingEntity) {
    String reaction = reactionBehavior.reactToInputs(controlKey,collidingEntity);
    ControlsBehavior behavior = myReactions.get(reaction);
    behavior.reactToControls(this);
//    if (myCollisionBehaviors.containsKey(collidingEntity)) {
//      for (CollisionBehavior behavior : myCollisionBehaviors.get(collidingEntity)) {
//        behavior.doCollision(collidingEntity);
//      }
//    }
  }

  @Override
  public void move(double xDistance, double yDistance) {
    myXPos += xDistance;
    myYPos += yDistance;
  }

  @Override
  public List<Double> getPosition() {
    return List.of(myXPos,myYPos);
  }

  @Override
  public void setPosition(List<Double> newPosition) {
    myXPos = newPosition.get(0);
    myYPos = newPosition.get(1);
  }

  @Override
  public void destroySelf() {
    isDestroyed = true;
  }

  @Override
  public void moveByVelocity() {
    myXPos += myVelocity.get(0);
    myYPos += myVelocity.get(1);
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
