package ooga.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.CollisionBehavior;
import ooga.ControlsBehavior;
import ooga.data.Entity;
import ooga.MovementBehavior;

@Deprecated
public class OogaPhysicsEntity extends Entity implements PhysicsEntity {

  private List<ControlsBehavior> myControlsBehaviors;
  private List<MovementBehavior> myMovementBehaviors;
  private Map<String,List<CollisionBehavior>> myCollisionBehaviors;
  private double myXPos;
  private double myYPos;
  private boolean isDestroyed;
  //TODO: Use or remove
  private PhysicsEntity myPhysics;

  public OogaPhysicsEntity() {
    myControlsBehaviors = new ArrayList<>();
    myMovementBehaviors = new ArrayList<>();
    isDestroyed = false;

  }

  //TODO: Consider making more flexible constructors or setter methods so that behaviors
  //TODO: are swappable.
  public OogaPhysicsEntity(MovementBehavior perFrameBehavior, ControlsBehavior controls) {
    this();
    myMovementBehaviors.add(perFrameBehavior);
    perFrameBehavior.setTarget(this);
    myControlsBehaviors.add(controls);
  }

  public OogaPhysicsEntity(MovementBehavior perFrameBehavior) {
    this();
    perFrameBehavior.setTarget(this);
    myMovementBehaviors.add(perFrameBehavior);
  }


  @Override
  public void reactToControls(String controls) {
    for (ControlsBehavior behavior : myControlsBehaviors) {
      behavior.reactToControls(controls);
    }
  }

  @Override
  public void setVelocity(List<Double> velocityVector) {

  }

  @Override
  public void setAcceleration(List<Double> accelVector) {

  }

  @Override
  public void updateSelf(double elapsedTime) {
    for (MovementBehavior behavior : myMovementBehaviors) {
      behavior.doMovementUpdate(elapsedTime);
    }
    myPhysics.updateSelf(elapsedTime);
  }

  @Override
  public void setCollisionBehaviors(Map<String, List<CollisionBehavior>> behaviorMap) {
    myCollisionBehaviors = new HashMap<>(behaviorMap);
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
  public List<Double> getPosition() {
    return List.of(myXPos,myYPos);
  }

  @Override
  public void setPosition(List<Double> newPosition) {

  }

  @Override
  public void destroySelf() {
    isDestroyed = true;
  }
}
