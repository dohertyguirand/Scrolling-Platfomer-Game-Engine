package ooga.game;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.behaviors.ConditionalBehavior;

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
   * Handles updates that happen every frame, regardless of context. Can still have logic.
   * Example: An enemy might move forward every frame.
   * @param elapsedTime
   *
   *
   */
  void updateSelf(double elapsedTime);

  /**
   * Actually moves the entity in space by its velocity. Should happen after all movement and
   * collision logic.
   * @param elapsedTime Time in milliseconds since last step.
   */
  void executeMovement(double elapsedTime);


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
   * Handles any behavior that depends on the values of variables.
   * @param variables
   */
  void reactToVariables(Map<String, String> variables);

  /**
   * Add a dependency to the map so that when the variable with the given name changes, the property with the given name is updated
   * @param propertyVariableDependencies
   */
  void setPropertyVariableDependencies(Map<String, String> propertyVariableDependencies);

  /**
   * Execute the do method on each of this entity's conditional behaviors, which will check the conditions and execute the
   * assigned actions if true
   */
  void doConditionalBehaviors(double elapsedTime, Map<String, String> inputs, Map<String, String> variables,
      Map<EntityInternal, Map<String, List<EntityInternal>>> collisionInfo, GameInternal gameInternal);

  /**
   * assigns the conditional behaviors of this entity
   * @param conditionalBehaviors list of conditional behaviors
   */
  void setConditionalBehaviors(List<ConditionalBehavior> conditionalBehaviors);

  /**
   * change every value in this entity's blockedMovements map to the specified value
   * @param isBlocked true if the entity is blocked in the direction, otherwise false
   */
  void blockInAllDirections(boolean isBlocked);

  /**
   * set myVariables to the specified map
   * @param variables map of variable names to values
   */
  void setVariables(Map<String, String> variables);


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
