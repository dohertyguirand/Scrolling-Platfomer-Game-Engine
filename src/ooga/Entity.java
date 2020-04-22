package ooga;

import java.util.List;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.ConditionalBehavior;
import ooga.game.behaviors.Effect;

/**
 * Represents any in-game object that has a physical place in the level.
 * Examples include terrain tiles, the player in a platformer, or an enemy.
 * Relies on access to the list of all entities if it wants to do
 * anything besides affect itself.
 */
public interface Entity {

  double getX();

  DoubleProperty xProperty();

  double getY();

  DoubleProperty yProperty();

  boolean isActiveInView();

  BooleanProperty activeInViewProperty();

  void setActiveInView(boolean activeInView);

  DoubleProperty heightProperty();

  DoubleProperty widthProperty();

  void setWidth(double width);

  void setHeight(double height);

  /**
   * @return The width of the entity.
   */
  double getWidth();

  /**
   * @return The height of the entity.
   */
  double getHeight();

  /**
   * @return The name identifying what type of entity this is, as defined in the game file.
   */
  public String getName();

  /**
   * Handles updates that happen every frame, regardless of context. Can still have logic.
   * Example: An enemy might move forward every frame.
   * @param elapsedTime
   * @param variables
   * @param game
   *
   */
  void updateSelf(double elapsedTime, Map<String, Double> variables, GameInternal game);

  /**
   * Actually moves the entity in space by its velocity. Should happen after all movement and
   * collision logic.
   * @param elapsedTime Time in milliseconds since last step.
   */
  void executeMovement(double elapsedTime);

  /**
   * @return The X and Y position of the Entity, in that order.
   */
  List<Double> getPosition();

  /**
   * @return The X and Y velocity of the Entity, in that order.
   */
  List<Double> getVelocity();

  /**
   * @param newPosition The new position for the entity to have in the level.
   */
  void setPosition(List<Double> newPosition);

  /**
   * Marks this entity for removal by the next frame, and prevents it from taking further actions.
   */
  void destroySelf();

  /**
   * @return True if this entity has been destroyed and should be removed.
   */
  boolean isDestroyed();

  /**
   *
   * @param xChange The x-value of the change in velocity.
   * @param yChange The y-value of the change in velocity.
   */
  void changeVelocity(double xChange, double yChange);

  /**
   * @param change A list with the coordinates of the change in velocity.
   */
  void changeVelocity(List<Double> change);

  /**
   *
   * @param xVelocity The x-value of the new velocity.
   * @param yVelocity The y-value of the new velocity.
   */
  void setVelocity(double xVelocity, double yVelocity);

  /**
   * Adds an entity to the buffer of entities that will be created next frame. May be removed
   * in favor of the game making the new entity immediately.
   * @param e
   */
  void createEntity(Entity e);

  /**
   * @return Any entities that were created by this one this frame. This is emptied by the call.
   */
  List<Entity> popCreatedEntities();

  /**
   * Handles any behavior that depends on the values of variables.
   */
  void reactToVariables(Map<String,Double> variables);

  /**
   * Add a dependency to the map so that when the variable with the given name changes, the property with the given name is updated
   * @param propertyVariableDependencies
   */
  void setPropertyVariableDependencies(Map<String, String> propertyVariableDependencies);

  /**
<<<<<<< HEAD
   * Execute the do method on each of this entity's conditional behaviors, which will check the conditions and execute the
   * assigned actions if true
   */
  void doConditionalBehaviors(double elapsedTime, List<String> inputs, Map<String, Double> variables,
                              Map<Entity, Map<String, List<Entity>>> collisionInfo, GameInternal gameInternal);

  /**
   * assigns the conditional behaviors of this entity
   * @param conditionalBehaviors list of conditional behaviors
   */
  void setConditionalBehaviors(List<ConditionalBehavior> conditionalBehaviors);

  /**
   * @param entityType The type of entity to check for collision behavior with.
   * @return True if the entity has a defined collision behavior with the type given.
   */
  boolean hasCollisionWith(String entityType);

  /**
   * change the value in this entity's blockedMovements map to the specified value
   * @param direction up, down, left, or right
   * @param isBlocked true if the entity is blocked in the direction, otherwise false
   */
  void blockInDirection(String direction, boolean isBlocked);

  /**
   * change every value in this entity's blockedMovements map to the specified value
   * @param isBlocked true if the entity is blocked in the direction, otherwise false
   */
  void blockInAllDirections(boolean isBlocked);

  /**
   * Adds (or sets) a variable to this entity's variable map
   * @param name name of the variable
   * @param value value of the variable
   */
  void addVariable(String name, String value);

  /**
   * returns the value of entity variable mapped to name
   * @param name key
   * @return value
   */
  String getVariable(String name);

  /**
   * set myVariables to the specified map
   * @param variables map of variable names to values
   */
  void setVariables(Map<String, String> variables);

  public String getEntityID();
}
