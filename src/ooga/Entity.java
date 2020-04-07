package ooga;

import java.util.List;
import java.util.Map;

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
   * Sets the mappings of behaviors that will be carried out when the entity collides with
   * another entity, based on what type of entity is being collided into.
   * @param behaviorMap A Map that connects the name of the entity that is being collided with
   *                    with the list of behaviors that should happen upon this collision.
   */
  void setCollisionBehaviors(Map<String,List<CollisionBehavior>> behaviorMap);

  /**
   * Reacts to colliding with a specific entity type based on its list of reactions mapped to
   * entity names, as defined by the game data.
   * Example: A Goomba might map a RemoveSelf behavior object to 'Fireball', so that it
   * dies when hit by a fireball.
   * @param collidingEntity The String identifier of the enemy being collided with.
   */
  void handleCollision(String collidingEntity);

  /**
   * Moves the entity by the specified amount in the x and y direction.
   * Useful for behaviors that own entity references. Might be moved to an "internal" entity
   * interface because it is meant for behavior classes rather than the main game loop.
   * @param xDistance Distance to move in the x direction. Can be negative.
   * @param yDistance Distance to move in the y direction. Can be negative.
   */
  void move(double xDistance, double yDistance);

  /**
   * @return The X and Y position of the Entity, in that order.
   */
  List<Double> getPosition();

  /**
   * @param newPosition The new position for the entity to have in the level.
   */
  void setPosition(List<Double> newPosition);

  /**
   * Marks this entity for removal by the next frame, and prevents it from taking further actions.
   */
  void destroySelf();
}
