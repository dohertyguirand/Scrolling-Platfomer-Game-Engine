package ooga.game.collisiondetection;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import ooga.Entity;
import ooga.game.CollisionDetector;

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
    return isCollidingInDirection(a, b, elapsedTime, true);

//    Shape aShape = makeShapeFromEntity(a, 0,a.getVelocity().get(1) * elapsedTime);
//    Shape bShape = makeShapeFromEntity(b, 0,b.getVelocity().get(1) * elapsedTime);
//    return (aShape.getBoundsInParent().intersects(bShape.getBoundsInParent()));
  }

  @Override
  public boolean isCollidingHorizontally(Entity a, Entity b, double elapsedTime) {
    return isCollidingInDirection(a, b, elapsedTime, false);
//    Shape aShape = makeShapeFromEntity(a, a.getVelocity().get(0) * elapsedTime,0);
//    Shape bShape = makeShapeFromEntity(b, b.getVelocity().get(0) * elapsedTime,0);
//    return (aShape.getBoundsInParent().intersects(bShape.getBoundsInParent()));
  }

  private boolean isCollidingInDirection(Entity a, Entity b, double elapsedTime, boolean mustBeVertical) {
    Rectangle aShape = makeShapeFromEntity(a, a.getVelocity().get(0) * elapsedTime,a.getVelocity().get(1) * elapsedTime);
    Rectangle bShape = makeShapeFromEntity(b, b.getVelocity().get(0) * elapsedTime,b.getVelocity().get(1) * elapsedTime);
    if(aShape.getBoundsInParent().intersects(bShape.getBoundsInParent())) {
      // scenario 1.1: a is left of b and below b
      // scenario 1.2: a is left of b and above b
      // scenario 2.1: a is right of b and below b
      // scenario 2.2: a is right of b and above b
      Rectangle leftShape = aShape;
      Rectangle rightShape = bShape;
      if (aShape.getX() > bShape.getX()) {
        leftShape = bShape;
        rightShape = aShape;
      }
      Rectangle topShape = leftShape;
      Rectangle bottomShape = rightShape;
      if (leftShape.getY() > bottomShape.getY()) {
        topShape = rightShape;
        bottomShape = leftShape;
      }
      double leftShapeRightEdge = leftShape.getX() + leftShape.getWidth();
      double rightShapeLeftEdge = rightShape.getX();
      double topShapeBottomEdge = topShape.getY() + topShape.getHeight();
      double bottomShapeTopEdge = bottomShape.getY();
      // ignore corner collisions
      if(leftShapeRightEdge - rightShapeLeftEdge == bottomShapeTopEdge - topShapeBottomEdge) return false;
      boolean isHorizontal = leftShapeRightEdge - rightShapeLeftEdge < -(bottomShapeTopEdge - topShapeBottomEdge);
      // if they are equal, deciding factor: check that someone actually has a nonzero velocity in the desired direction, otherwise a collision doesn't make sense
//        boolean nonZeroVerticalVelocity = a.getVelocity().get(1) != 0 || b.getVelocity().get(1) != 0;
//        boolean nonZeroHorizontalVelocity = a.getVelocity().get(0) != 0 || b.getVelocity().get(0) != 0;
//        if (!mustBeVertical && !nonZeroVerticalVelocity && nonZeroHorizontalVelocity) {
//          return true;
//        } else if (mustBeVertical && !nonZeroHorizontalVelocity && nonZeroVerticalVelocity) {
//          return true;
//        }
      if(mustBeVertical){
        return !isHorizontal;
      }
      return isHorizontal;
    }
    return false;
  }

  private Rectangle makeShapeFromEntity(Entity e, double xChange, double yChange) {
    double xPos = e.getPosition().get(0) + xChange;
    double yPos = e.getPosition().get(1) + yChange;
    return new Rectangle(xPos, yPos, e.getWidth(), e.getHeight());
  }
}
