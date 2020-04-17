package ooga;

import java.util.List;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import ooga.game.GameInternal;
import ooga.game.behaviors.CollisionBehavior;
import ooga.game.behaviors.ControlsBehavior;
import ooga.game.behaviors.MovementBehavior;

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
   * 'Controls' will be a String mapping to a controls type from a shared back end resource file.
   * @param controls The String identifier of the type of control input that must be handled.
   */
  void reactToControls(String controls);

  /**
   * @see Entity#reactToControls(String)
   * Reacts to the event of the key being pressed, i.e. the first frame that the key is active,
   * so that button presses can be reacted to just once.
   * @param controls The String identifier of the type of control input that must be handled.
   */
  void reactToControlsPressed(String controls);

  /**
   * Handles updates that happen every frame, regardless of context. Can still have logic.
   * Example: An enemy might move forward every frame.
   * @param elapsedTime
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
   * Sets the mappings of behaviors that will be carried out when the entity collides with
   * another entity, based on what type of entity is being collided into.
   * @param behaviorMap A Map that connects the name of the entity that is being collided with
   *                    with the list of behaviors that should happen upon this collision.
   */
  void setCollisionBehaviors(Map<String,List<CollisionBehavior>> behaviorMap);

  /**
   * Sets the behaviors that will be carried out for every frame
   * @param behaviors A List of MovementBehaviors
   */
  void setMovementBehaviors(List<MovementBehavior> behaviors);

  /**
   * Sets the mappings of behaviors that will be carried out when controls are inputted,
   * by mapping behaviors to controls.
   * @param behaviors The Map from standardized control input strings to ControlsBehaviors that
   *                  define this entity's reaction to controls.
   */
  void setControlsBehaviors(Map<String,List<ControlsBehavior>> behaviors);

  /**
   * Reacts to colliding with a specific entity type based on its list of reactions mapped to
   * entity names, as defined by the game data.
   * Example: A Goomba might map a RemoveSelf behavior object to 'Fireball', so that it
   * dies when hit by a fireball.
   * @param collidingEntity The String identifier of the enemy being collided with.
   * @param elapsedTime
   */
  void handleVerticalCollision(Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game);

  void handleHorizontalCollision(Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game);

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
   * @param entityType The type of entity to check for collision behavior with.
   * @return True if the entity has a defined collision behavior with the type given.
   */
  boolean hasCollisionWith(String entityType);
}
