package ooga;

/**
 * Handles per-frame actions that an entity does regardless of triggers like collisions
 * or input.
 * Must have access to the updating entity if it wants to make changes, and this access
 * must come from the constructor.
 * Example: Goomba moving forward.
 */
public interface MovementBehavior {

  /**
   * Sets the Entity who will enact this behavior, and will then have the behavior affect them.
   * @param e The Entity to have the behavior control.
   */
  void setTarget(EntityAPI e);

  /**
   * Performs the subclass-specific implementation that happens per frame.
   * @param elapsedTime The time since the previous frame.
   */
  void doMovementUpdate(double elapsedTime);
}
