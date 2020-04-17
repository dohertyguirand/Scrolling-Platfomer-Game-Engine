package ooga.game.behaviors;

import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;

/**
 * Defines a behavior to perform upon colliding with another entity.
 * If this behavior depends on what entity is colliding with the one who owns this instance,
 * than that instance is in charge of determining which CollisionBehavior to use.
 * Example: Goombas stop existing when a fireball touches them.
 */
public interface CollisionBehavior {

  /**
   * Performs the specific collision behavior implementation.
   * @param subject The entity that is reacting to a collision with the colliding entity, which
   *                   should own this behavior.
   * @param collidingEntity The String identifying the type of entity that the calling entity
   */
//  void doCollision(Entity subject, Entity collidingEntity);

  /**
   * Performs the specific collision behavior implementation, with a vertical collision.
   * @param subject The entity that is reacting to a collision with the colliding entity, which
   *                   should own this behavior.
   * @param collidingEntity The String identifying the type of entity that the calling entity
   * @param elapsedTime
   * @param variables
   * @param game
   */
  default void doVerticalCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    //do nothing by default.
  }

  /**
   * Performs the specific collision behavior implementation, with a horizontal collision.
   * @param subject The entity that is reacting to a collision with the colliding entity, which
   *                   should own this behavior.
   * @param collidingEntity The String identifying the type of entity that the calling entity
   * @param elapsedTime
   * @param variables
   * @param game
   */
  default void doHorizontalCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    //do nothing by default.
  }
}
