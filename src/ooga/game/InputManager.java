package ooga.game;

import java.util.List;

public interface InputManager {

  void keyPressed(String key);

  void keyReleased(String key);

  List<String> getActiveKeys();

  List<String> getPressedKeys();

  void update();
}
