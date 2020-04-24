package ooga.game.collisiondetection;

import ooga.Entity;
import ooga.game.CollisionDetector;

@Deprecated
public class OogaCollisionDetector implements CollisionDetector {

  /**
   * Figure out if the entities are colliding and return the direction that a is colliding with b (e.g. a,b -> right)
   *
   * @param a           entity a
   * @param b           entity b
   * @param elapsedTime time passed in ms
   * @return direction of collision, or null if no collision
   */
  @Override
  public String getCollisionDirection(Entity a, Entity b, double elapsedTime) {
    return null;
  }

}
