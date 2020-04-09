package ooga;

import java.util.List;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;

/**
 * Represents any in-game object that has a physical place in the level.
 * Examples include terrain tiles, the player in a platformer, or an enemy.
 * Relies on access to the list of all entities if it wants to do
 * anything besides affect itself.
 */
public interface Entity {

  public double getX();

  public DoubleProperty xProperty();

  public double getY();

  public DoubleProperty yProperty();

  public boolean isActiveInView();

  public BooleanProperty activeInViewProperty();

  public void setActiveInView(boolean activeInView);

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
   * Moves the entity by its internally stored velocity
   */
  void moveByVelocity();

  /**
   * @return The X and Y position of the Entity, in that order.
   */
  List<Double> getPosition();

  /**
   * @return The X and Y velocity of the Entity, in that order.
   */
  List<Double> getVelocity();

  /**
   * @return The width of the entity.
   */
  double getWidth();

  /**
   * @return The height of the entity.
   */
  double getHeight();

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
   *
   * @param xVelocity The x-value of the new velocity.
   * @param yVelocity The y-value of the new velocity.
   */
  void setVelocity(double xVelocity, double yVelocity);



}
