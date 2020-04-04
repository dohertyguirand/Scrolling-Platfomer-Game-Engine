package ooga;

/**
 * Represents any in-game object that has a physical place in the level.
 * Examples include terrain tiles, the player in a platformer, or an enemy.
 * Relies on access to the list of all entities if it wants to do
 * anything besides affect itself.
 */
public interface Entity {

  /**
   * 'Controls' will be a String mapping to a controls type from a shared back end resource file.
   * @param controls The String identifier of the type of control input that must be handled.
   */
  void reactToControls(String controls);

  /**
   * Handles updates that happen every frame, regardless of context. Can still have logic.
   * Example: An enemy might move forward every frame.
   * @param elapsedTime
   */
  void updateSelf(double elapsedTime);

  /**
   * Reacts to colliding with a specific entity type based on its list of reactions mapped to
   * entity names, as defined by the game data.
   * Example: A Goomba might map a RemoveSelf behavior object to 'Fireball', so that it
   * dies when hit by a fireball.
   * @param collidingEntity The String identifier of the enemy being collided with.
   */
  void handleCollision(String collidingEntity);
}
