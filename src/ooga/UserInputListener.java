package ooga;

/**
 * Uses the Observer pattern so that the engine can subscribe to input and handle user interaction
 * asynchronously (Tell, don't ask).
 */
public interface UserInputListener {

  /**
   * @param mouseX The X-coordinate of the mouse click, in in-game screen coordinates
   *               (not "view-relative" coordinates).
   * @param mouseY The Y-coordinate of the mouse click, in-game screen coordinates.
   */
  void reactToMouseClick(double mouseX, double mouseY);

  /**
   * React to a key being pressed. Use data files to determine appropriate action
   * @param keyName string name of key
   */
  void reactToKeyPress(String keyName);

  /**
   * React to a key being released. Use data files to determine appropriate reaction.
   * @param keyName String name of key.
   */
  void reactToKeyRelease(String keyName);

  /**
   * Handles when the command is given to save the game to a file.
   */
  void reactToGameSave();

  /**
   * Handles when the command is given to quit the currently running game.
   * This might be modified to account for identifying which game must close when
   * there are multiple games open.
   */
  void reactToGameQuit();

  /**
   * indicates the pause button was clicked in the ui
   * @param paused whether or not the button clicked was pause or resume
   */
  void reactToPauseButton(boolean paused);
}
