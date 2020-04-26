package ooga.game.controls;

import java.util.ResourceBundle;

/**
 * @author sam thompson
 */
public class KeyboardControls implements ControlsInterpreter {

  public static final String UNKNOWN_INPUT = "UNKNOWN";

  private final ResourceBundle myResources;

  public KeyboardControls(String inputFilePath) {
    myResources = ResourceBundle.getBundle(inputFilePath);
  }

  @Override
  public String translateInput(String input) {
    if (!myResources.containsKey(input)) {
      return UNKNOWN_INPUT;
    }
    return myResources.getString(input);
  }
}
