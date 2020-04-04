package ooga;

/**
 * Determines how an entity reacts to in-game user input of any kind.
 * Example: In Super Mario Bros, Mario can be moved with left and right keys.
 * Relies on a mapping (usually data-driven) between Strings and types of inputs.
 */
public interface ControlsBehavior {

  /**
   * Handles reaction to controls. Requires the ControlsBehavior to have a reference to the
   * instance that uses it in order to have an effect on that instance.
   * @param input The String identifying the type of input ("Right" or "Left").
   */
  void reactToControls(String input);
}
