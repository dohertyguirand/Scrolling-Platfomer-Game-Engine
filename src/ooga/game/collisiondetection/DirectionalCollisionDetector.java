package ooga.game.collisiondetection;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import ooga.Entity;
import ooga.game.CollisionDetector;

public class DirectionalCollisionDetector implements CollisionDetector {

  /**
   * Figure out if the entities are colliding and return the direction that a is colliding with b (e.g. a,b -> right)
   * @param a entity a
   * @param b entity b
   * @param elapsedTime time passed in ms
   * @return direction of collision, or null if no collision
   */
  @Override
  public String getCollisionDirection(Entity a, Entity b, double elapsedTime) {
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
      if(leftShapeRightEdge - rightShapeLeftEdge == bottomShapeTopEdge - topShapeBottomEdge) return null;
      boolean isHorizontal = leftShapeRightEdge - rightShapeLeftEdge < -(bottomShapeTopEdge - topShapeBottomEdge);
      // if they are equal, deciding factor: check that someone actually has a nonzero velocity in the desired direction, otherwise a collision doesn't make sense
//        boolean nonZeroVerticalVelocity = a.getVelocity().get(1) != 0 || b.getVelocity().get(1) != 0;
//        boolean nonZeroHorizontalVelocity = a.getVelocity().get(0) != 0 || b.getVelocity().get(0) != 0;
//        if (!mustBeVertical && !nonZeroVerticalVelocity && nonZeroHorizontalVelocity) {
//          return true;
//        } else if (mustBeVertical && !nonZeroHorizontalVelocity && nonZeroVerticalVelocity) {
//          return true;
//        }
      if(isHorizontal){
        if(leftShape == aShape){
          return "Right";
        }
        return "Left";
      }
      if(topShape == aShape){
        return "Down";
      }
      return "Up";
    }
    return null;
  }

  /**
   * NOTE: The order of a and b doesn't matter.
   * @param a The entity to check for collision with Entity a.
   * @param b The entity to check for collision with Entity b.
   * @param elapsedTime
   * @return True if entities a and b are colliding (touching) and should thus run their
   * collision actions.
   */
  @Override
  public boolean isColliding(Entity a, Entity b, double elapsedTime) {
    Rectangle aShape = makeShapeFromEntity(a, a.getVelocity().get(0) * elapsedTime,a.getVelocity().get(1) * elapsedTime);
    Rectangle bShape = makeShapeFromEntity(b, b.getVelocity().get(0) * elapsedTime,b.getVelocity().get(1) * elapsedTime);
    return aShape.getBoundsInParent().intersects(bShape.getBoundsInParent());
  }

  private Rectangle makeShapeFromEntity(Entity e, double xChange, double yChange) {
    double xPos = e.getPosition().get(0) + xChange;
    double yPos = e.getPosition().get(1) + yChange;
    return new Rectangle(xPos, yPos, e.getWidth(), e.getHeight());
  }
}
