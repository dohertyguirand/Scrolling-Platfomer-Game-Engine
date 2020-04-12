package ooga.game;

import java.util.List;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import ooga.Entity;

public class VelocityCollisionDetector implements CollisionDetector {
  @Override
  public boolean isColliding(Entity a, Entity b) {
    Shape aShape = makeShapeFromEntity(a, a.getVelocity().get(0),a.getVelocity().get(1));
    Shape bShape = makeShapeFromEntity(b, b.getVelocity().get(0),a.getVelocity().get(1));
    boolean ret = aShape.getBoundsInParent().intersects(bShape.getBoundsInParent());
    if (a.getName().equals("dino")) {
//      System.out.println("Dino: " + a.getPosition() + " + " + a.getVelocity());
//      System.out.println("Cactus: " + b.getPosition() + " + " + b.getVelocity());
//      System.out.println(ret);
    }
    if (ret && a.getName().equals("dino")) {
      if (isCollidingVertically(a,b)) {
        System.out.println("Vertical collision");
      }
      if (isCollidingHorizontally(a,b)) {
        System.out.println("Horizontal collision");

      }
      else {
        System.out.println("Not vert or horizontal");
      }
    }
    return (aShape.getBoundsInParent().intersects(bShape.getBoundsInParent()));
  }

  @Override
  public boolean isCollidingVertically(Entity a, Entity b) {
//    System.out.println("a: " + a.getPosition() + " + " + a.getVelocity());
//    System.out.println("b: " + b.getPosition() + " + " + b.getVelocity());

    Shape aShape = makeShapeFromEntity(a, 0,a.getVelocity().get(1));
    Shape bShape = makeShapeFromEntity(b, 0,b.getVelocity().get(1));
    return (aShape.getBoundsInParent().intersects(bShape.getBoundsInParent()));
  }

  @Override
  public boolean isCollidingHorizontally(Entity a, Entity b) {
    Shape aShape = makeShapeFromEntity(a, a.getVelocity().get(0),0);
    Shape bShape = makeShapeFromEntity(b, b.getVelocity().get(0),0);
    return (aShape.getBoundsInParent().intersects(bShape.getBoundsInParent()));
  }

  private Shape makeShapeFromEntity(Entity e, double xVelocity, double yVelocity) {
    double xPos = e.getPosition().get(0) + xVelocity;
    double yPos = e.getPosition().get(1) + yVelocity;
    return new Rectangle(xPos, yPos, e.getWidth(), e.getHeight());
  }
}
