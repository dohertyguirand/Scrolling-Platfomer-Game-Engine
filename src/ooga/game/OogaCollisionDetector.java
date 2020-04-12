package ooga.game;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import ooga.Entity;
import ooga.UserInputListener;

public class OogaCollisionDetector implements CollisionDetector {

  @Override
  public boolean isColliding(Entity a, Entity b) {
    return (makeShapeFromEntity(a).getBoundsInParent().intersects(makeShapeFromEntity(b).getBoundsInParent()));
  }

  @Override
  public boolean isCollidingVertically(Entity a, Entity b) {
    return false;
  }

  @Override
  public boolean isCollidingHorizontally(Entity a, Entity b) {
    return false;
  }

  private Shape makeShapeFromEntity(Entity e) {
    Shape s = new Rectangle(e.getPosition().get(0), e.getPosition().get(1),
            e.getWidth(), e.getHeight());
    return new Rectangle(e.getPosition().get(0), e.getPosition().get(1),
            e.getWidth(), e.getHeight());
  }
}
