package ooga.game;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import ooga.Entity;

public class OogaCollisionDetector implements CollisionDetector {

  @Override
  public boolean isColliding(Entity a, Entity b) {
    return (makeShapeFromEntity(a).getBoundsInParent().intersects(makeShapeFromEntity(b).getBoundsInParent()));
  }

  private Shape makeShapeFromEntity(Entity e) {
    Shape s = new Rectangle( e.getPosition().get(0),e.getPosition().get(1),
        e.getWidth(), e.getHeight());
    System.out.println("e.getWidth() = " + e.getWidth());
    System.out.println("s.getBoundsInParent().getWidth() = " + s.getBoundsInParent().getWidth());
    return new Rectangle( e.getPosition().get(0),e.getPosition().get(1),
                          e.getWidth(), e.getHeight());
  }
}
