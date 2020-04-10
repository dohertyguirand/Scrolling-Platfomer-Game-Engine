package ooga.game;

import java.util.List;
import java.util.Map;
import ooga.CollisionBehavior;
import ooga.MovementBehavior;

/**
 * (PROPOSED) The side of an entity that can be accessed by behaviors.
 */
public interface EntityInternal {

  /**
   * Moves the entity by the specified amount in the x and y direction.
   * Useful for behaviors that own entity references. Might be moved to an "internal" entity
   * interface because it is meant for behavior classes rather than the main game loop.
   * @param xDistance Distance to move in the x direction. Can be negative.
   * @param yDistance Distance to move in the y direction. Can be negative.
   */
  void move(double xDistance, double yDistance);

  /**
   * Moves the entity by its internally stored velocity
   */
  void moveByVelocity(double elapsedTime);

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

  /**
   *
   * @param xChange The x-value of the change in velocity.
   * @param yChange The y-value of the change in velocity.
   */
  void changeVelocity(double xChange, double yChange);

  /**
   *
   * @param xVelocity The x-value of the new velocity.
   * @param yVelocity The y-value of the new velocity.
   */
  void setVelocity(double xVelocity, double yVelocity);
}
