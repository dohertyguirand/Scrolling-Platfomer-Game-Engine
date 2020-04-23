package ooga.game;

public interface ControlsInterpreter {

  /**
   * @param input The String representing the raw key/button input.
   * @return The standardized String representing the type of input.
   */
  String translateInput(String input);
}
