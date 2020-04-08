package ooga.game;

import ooga.EntityAPI;

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
  boolean isColliding(EntityAPI a, EntityAPI b);
}
