package ooga.game.collisiondetection;

import ooga.Entity;

/**
 * Detects collisions between entities. Can likely be static.
 * May use JavaFX algorithms, so should be encapsulated from the back end.
 */
public interface CollisionDetector {

  /**
   * Figure out if the entities are colliding and return the direction that a is colliding with b (e.g. a,b -> right)
   * @param a entity a
   * @param b entity b
   * @param elapsedTime time passed in ms
   * @return direction of collision, or null if no collision
   */
  String getCollisionDirection(Entity a, Entity b, double elapsedTime);
}
