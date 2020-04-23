package ooga.game;

import ooga.Entity;

/**
 * Detects collisions between entities. Can likely be static.
 * May use JavaFX algorithms, so should be encapsulated from the back end.
 */
public interface CollisionDetector {

  /**
   * NOTE: The order of a and b doesn't matter.
   * @param a The entity to check for collision with Entity a.
   * @param b The entity to check for collision with Entity b.
   * @param elapsedTime
   * @return True if entities a and b are colliding (touching) and should thus run their
   * collision actions.
   */
  boolean isColliding(Entity a, Entity b, double elapsedTime);

  /**
   * Figure out if the entities are colliding and return the direction that a is colliding with b (e.g. a,b -> right)
   * @param a entity a
   * @param b entity b
   * @param elapsedTime time passed in ms
   * @return direction of collision, or null if no collision
   */
  String getCollisionDirection(Entity a, Entity b, double elapsedTime);
}
