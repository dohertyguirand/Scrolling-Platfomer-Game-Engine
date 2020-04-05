package ooga;

import java.util.ArrayList;
import java.util.List;

public class OogaEntity implements Entity {

  private List<ControlsBehavior> myControlsBehaviors;
  private List<MovementBehavior> myMovementBehaviors;
  private double myXPos;
  private double myYPos;

  public OogaEntity() {
    myControlsBehaviors = new ArrayList<>();
    myMovementBehaviors = new ArrayList<>();
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
  public void handleCollision(String collidingEntity) {

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
}
