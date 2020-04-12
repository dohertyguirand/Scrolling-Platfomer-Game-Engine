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
   * @return True if entities a and b are colliding (touching) and should thus run their
   * collision actions.
   */
  boolean isColliding(Entity a, Entity b);

  /**
   * NOTE: The order of a and b doesn't matter.
   * @param a The entity to check for collision with Entity a.
   * @param b The entity to check for collision with Entity b.
   * @return True if entities a and b are colliding vertically (not diagonally).
   */
  boolean isCollidingVertically(Entity a, Entity b);

  /**
   * NOTE: The order of a and b doesn't matter.
   * @param a The entity to check for collision with Entity a.
   * @param b The entity to check for collision with Entity b.
   * @return True if entities a and b are colliding sideways (not diagonally).
   */
  boolean isCollidingHorizontally(Entity a, Entity b);
}
