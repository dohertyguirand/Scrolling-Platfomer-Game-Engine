package ooga.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.*;
import ooga.CollisionBehavior;
import ooga.ControlsBehavior;
import ooga.EntityAPI;
import ooga.MovementBehavior;
import ooga.game.PhysicsEntity;

public abstract class Entity implements EntityAPI {

  private BooleanProperty activeInView = new SimpleBooleanProperty(true);
  private DoubleProperty x = new SimpleDoubleProperty();
  private DoubleProperty y = new SimpleDoubleProperty();

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

  private List<ControlsBehavior> myControlsBehaviors;
  private List<MovementBehavior> myMovementBehaviors;
  private Map<String,List<CollisionBehavior>> myCollisionBehaviors;
  private double myXPos;
  private double myYPos;
  private boolean isDestroyed;
  //TODO: Use or remove
  private PhysicsEntity myPhysics;

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
    myXPos = newPosition.get(0);
    myYPos = newPosition.get(1);
  }

  @Override
  public void destroySelf() {
    isDestroyed = true;
  }
}
