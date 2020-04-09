package ooga;

/**
 * Defines a behavior to perform upon colliding with another entity.
 * If this behavior depends on what entity is colliding with the one who owns this instance,
 * than that instance is in charge of determining which CollisionBehavior to use.
 * Example: Goombas stop existing when a fireball touches them.
 */
public interface CollisionBehavior {

  /**
   * Performs the specific collision behavior implementation.
   * @param thisEntity The entity that is reacting to a collision with the colliding entity, which
   *                   should own this behavior.
   * @param collidingEntity The String identifying the type of entity that the calling entity
   *                        is colliding with.
   */
  void doCollision(Entity thisEntity, String collidingEntity);
}
