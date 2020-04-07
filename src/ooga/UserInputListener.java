package ooga;

/**
 * Uses the Observer pattern so that the engine can subscribe to input and handle user interaction
 * asynchronously (Tell, don't ask).
 */
public interface UserInputListener {

  /**
   * Handles reactions to each of the respective buttons.
   * What actually counts as "Right","Left", etc. can vary based on the implementation of the
   * user input class, since control schemes differ.
   */
  void reactToRightButton();
  void reactToLeftButton();
  void reactToUpButton();
  void reactToDownButton();
  void reactToActionButton();
  /**
   * @param mouseX The X-coordinate of the mouse click, in in-game screen coordinates
   *               (not "view-relative" coordinates).
   * @param mouseY The Y-coordinate of the mouse click, in-game screen coordinates.
   */
  void reactToMouseClick(double mouseX, double mouseY);

  /**
   * Can use a named button, as enumerated in a shared resource file that lists all
   * the possible inputs. The implementation allows the listener to use reflection to
   * associate an arbitrary number of buttons with an arbitrary number of objects with
   * reaction behavior.
   * @param buttonID
   */
  void reactToButton(String buttonID);

  /**
   * React to a key being pressed. Use data files to determine appropriate action
   * @param keyName string name of key
   */
  void reactToKeyPress(String keyName);

  /**
   * Handles the higher-level action of selecting a game to play through the UI.
   * The implementation CANNOT assume that this will only be called if there isn't any
   * game already active.
   * @param filePath The filepath of the game the user has selected.
   */
  void reactToGameSelect(String filePath);

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
