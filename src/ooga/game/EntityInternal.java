package ooga.game;

import java.util.List;
import java.util.Map;
import ooga.Entity;

/**
 * (PROPOSED) The side of an entity that can be accessed by behaviors.
 */
public interface EntityInternal extends Entity {

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

  /**
   * @return The name of this Entity. Is often not unique.
   */
  String getName();

  /**
   * @return The velocity of this entity as a list of two doubles.
   */
  List<Double> getVelocity();

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
   * @return The width of this entity.
   */
  double getWidth();

  /**
   * @return The height of this entity.
   */
  double getHeight();

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
   * @param direction The direction in which to modify movement blocking.
   * @param blocked True if movement should be blocked, false if it should be unblocked.
   */
  void blockInDirection(String direction, boolean blocked);

  /**
   * @return A copy of this entity's internal variables, as String-String name-value pairs.
   */
  Map<String,String> getVariables();
}
