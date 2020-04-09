package ooga.game;

import java.util.ResourceBundle;

public class KeyboardControls implements ControlsInterpreter {

  private ResourceBundle myResources;

  public KeyboardControls() {
    myResources = ResourceBundle.getBundle("ooga/game/resources/inputs/keyboard");
  }

  @Override
  public String translateInput(String input) {
    return myResources.getString(input);
  }
}
