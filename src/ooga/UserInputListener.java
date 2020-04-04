package ooga;

public interface UserInputListener {

  void reactToRightButton();
  void reactToLeftButton();

  void reactToUpButton();
  void reactToDownButton();

  void reactToActionButton();

  /**
   * Can use a named button, as enumerated in a shared resource file that lists all
   * the possible inputs. The implementation allows the listener to use reflection to
   * associate an arbitrary number of buttons with an arbitrary number of objects with
   * reaction behavior.
   * @param buttonID
   */
  void reactToButton(String buttonID);

  void reactToMouseClick(double mouseX, double mouseY);

  void reactToGameSelect(String filePath);

  void reactToGameSave();

  void reactToGameQuit();

}
