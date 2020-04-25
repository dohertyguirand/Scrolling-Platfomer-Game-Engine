package ooga.game;

import java.util.List;
import java.util.Map;
import ooga.Entity;

/**
 * The side of an entity that behaviors can see. Provides tools to modify or destroy
 * the entity.
 */
public interface EntityInternal extends Entity {

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

  /**
   * Sets the variable with the specified name to the specified value.
   * @param variableName The name of the
   * @param value
   */
  void addVariable(String variableName, String value);

  /**
   * @param variableName The name of the variable to look up.
   * @return The value stored by this entity in a variable with the given name. Returns null
   * if nothing is found.
   */
  String getVariable(String variableName);

  /**
   * @param width The width to set the entity's width to.
   */
  void setWidth(double width);

  /**
   * @param height The hight to apply to this entity.
   */
  void setHeight(double height);

  /**
   * @param filepath The filepath of the image for this entity to use from now on.
   */
  void setImageLocation(String filepath);

  /**
   * change the value in this entity's blockedMovements map to the specified value
   * @param direction up, down, left, or right
   * @param blocked true if the entity is blocked in the direction, otherwise false
   */
  void blockInDirection(String direction, boolean blocked);
}
