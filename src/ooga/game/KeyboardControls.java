package ooga.game;

import java.util.ResourceBundle;

public class KeyboardControls implements ControlsInterpreter {

  public static final String UNKNOWN_INPUT = "UNKNOWN";

  private ResourceBundle myResources;

  public KeyboardControls() {
    myResources = ResourceBundle.getBundle("ooga/game/resources/inputs/keyboard");
  }

  @Override
  public String translateInput(String input) {
    if (!myResources.containsKey(input)) {
      return UNKNOWN_INPUT;
    }
    return myResources.getString(input);
  }
}
