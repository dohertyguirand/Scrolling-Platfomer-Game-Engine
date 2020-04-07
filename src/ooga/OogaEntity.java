package ooga;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OogaEntity implements Entity {

  private List<ControlsBehavior> myControlsBehaviors;
  private List<MovementBehavior> myMovementBehaviors;
  private Map<String,List<CollisionBehavior>> myCollisionBehaviors;
  private double myXPos;
  private double myYPos;
  private boolean isDestroyed;
  //TODO: Use or remove
  private List<Double> myMovementVector;

  public OogaEntity() {
    myControlsBehaviors = new ArrayList<>();
    myMovementBehaviors = new ArrayList<>();
    myMovementVector = List.of(0.0,0.0);
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
  public void updateSelf(double elapsedTime) {
    for (MovementBehavior behavior : myMovementBehaviors) {
      behavior.doMovementUpdate(elapsedTime);
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
  public void destroySelf() {
    isDestroyed = true;
  }
}
