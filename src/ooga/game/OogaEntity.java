package ooga.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.CollisionBehavior;
import ooga.ControlsBehavior;
import ooga.EntityAPI;
import ooga.MovementBehavior;

public class OogaEntity implements EntityAPI {

  private List<ControlsBehavior> myControlsBehaviors;
  private List<MovementBehavior> myMovementBehaviors;
  private Map<String,List<CollisionBehavior>> myCollisionBehaviors;
  private double myXPos;
  private double myYPos;
  private boolean isDestroyed;
  //TODO: Use or remove
  private PhysicsEntity myPhysics;

  public OogaEntity() {
    myControlsBehaviors = new ArrayList<>();
    myMovementBehaviors = new ArrayList<>();
    isDestroyed = false;

  }

  //TODO: Consider making more flexible constructors or setter methods so that behaviors
  //TODO: are swappable.
  public OogaEntity(MovementBehavior perFrameBehavior, ControlsBehavior controls) {
    this();
    myMovementBehaviors.add(perFrameBehavior);
    myControlsBehaviors.add(controls);
  }

  public OogaEntity(MovementBehavior perFrameBehavior) {
    this();
    myMovementBehaviors.add(perFrameBehavior);
  }


  @Override
  public void reactToControls(String controls) {
    for (ControlsBehavior behavior : myControlsBehaviors) {
      behavior.reactToControls(this);
    }
  }

  @Override
  public void updateSelf(double elapsedTime) {
    for (MovementBehavior behavior : myMovementBehaviors) {
      behavior.doMovementUpdate(elapsedTime,this);
    }
    myPhysics.updateSelf(elapsedTime);
  }

  @Override
  public void setCollisionBehaviors(Map<String, List<CollisionBehavior>> behaviorMap) {
    myCollisionBehaviors = new HashMap<>(behaviorMap);
  }

  @Override
  public void setMovementBehaviors(List<MovementBehavior> behaviors) {

  }

  @Override
  public void handleCollision(String collidingEntity) {
    if (myCollisionBehaviors.containsKey(collidingEntity)) {
      for (CollisionBehavior behavior : myCollisionBehaviors.get(collidingEntity)) {
        behavior.doCollision(collidingEntity);
      }
    }
  }

  @Override
  public void move(double xDistance, double yDistance) {
    myXPos += xDistance;
    myYPos += yDistance;
  }

  @Override
  public void moveByVelocity() {

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
  public void changeVelocity(double xChange, double yChange) {

  }

  @Override
  public void setVelocity(double xVelocity, double yVelocity) {

  }
}
