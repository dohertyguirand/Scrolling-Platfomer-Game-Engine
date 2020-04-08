package ooga;

import ooga.data.Entity;

/**
 * Determines how an entity reacts to in-game user input of any kind.
 * Example: In Super Mario Bros, Mario can be moved with left and right keys.
 * Relies on a mapping (usually data-driven) between Strings and types of inputs.
 */
public interface ControlsBehavior {

  /**
   * Handles reaction to controls. Requires the ControlsBehavior to have a reference to the
   * instance that uses it in order to have an effect on that instance.
   * @param subject The entity that owns this controls behavior. This is the entity that should
   *                be modified.
   */
  void reactToControls(Entity subject);
}
