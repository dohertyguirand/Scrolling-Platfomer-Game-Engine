package ooga.game;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import ooga.Entity;

public class VelocityCollisionDetector implements CollisionDetector {
  @Override
  public boolean isColliding(Entity a, Entity b, double elapsedTime) {
    Shape aShape = makeShapeFromEntity(a, a.getVelocity().get(0) * elapsedTime,a.getVelocity().get(1) * elapsedTime);
    Shape bShape = makeShapeFromEntity(b, b.getVelocity().get(0) * elapsedTime,b.getVelocity().get(1) * elapsedTime);
//    System.out.println(aShape);
//    System.out.println(bShape);
    boolean ret = aShape.getBoundsInParent().intersects(bShape.getBoundsInParent());
    if (a.getName().equals("dino")) {
//      System.out.println("Dino: " + a.getPosition() + " + " + a.getVelocity());
//      System.out.println("Cactus: " + b.getPosition() + " + " + b.getVelocity());
//      System.out.println(ret);
    }
    if (ret && a.getName().equals("dino")) {
//      System.out.println("Vertical: " + isCollidingVertically(a,b));
//      System.out.println("Horizontal: " + isCollidingHorizontally(a,b));
      if (isCollidingHorizontally(a,b, elapsedTime) && !(isCollidingVertically(a,b, elapsedTime))) {
        System.out.println("Horizontal collision");
      }
      else if (isCollidingVertically(a,b, elapsedTime) && !(isCollidingHorizontally(a,b, elapsedTime))) {

      }
      else {
//        System.out.println("A: " + a.getPosition() + " + " + a.getVelocity());
//        System.out.println("B: " + b.getPosition() + " + " + b.getVelocity());
//        System.out.println();
      }

//      else {
//        System.out.println("Not vert or horizontal");
//      }
    }
    return (aShape.getBoundsInParent().intersects(bShape.getBoundsInParent()));
  }

  @Override
  public boolean isCollidingVertically(Entity a, Entity b, double elapsedTime) {
//    System.out.println("a: " + a.getPosition() + " + " + a.getVelocity());
//    System.out.println("b: " + b.getPosition() + " + " + b.getVelocity());

    Shape aShape = makeShapeFromEntity(a, 0,a.getVelocity().get(1) * elapsedTime);
    Shape bShape = makeShapeFromEntity(b, 0,b.getVelocity().get(1) * elapsedTime);
    return (aShape.getBoundsInParent().intersects(bShape.getBoundsInParent()));
  }

  @Override
  public boolean isCollidingHorizontally(Entity a, Entity b, double elapsedTime) {
    Shape aShape = makeShapeFromEntity(a, a.getVelocity().get(0) * elapsedTime,0);
    Shape bShape = makeShapeFromEntity(b, b.getVelocity().get(0) * elapsedTime,0);
    return (aShape.getBoundsInParent().intersects(bShape.getBoundsInParent()));
  }

  private Shape makeShapeFromEntity(Entity e, double xVelocity, double yVelocity) {
    double xPos = e.getPosition().get(0) + xVelocity;
    double yPos = e.getPosition().get(1) + yVelocity;
    return new Rectangle(xPos, yPos, e.getWidth(), e.getHeight());
  }
}
