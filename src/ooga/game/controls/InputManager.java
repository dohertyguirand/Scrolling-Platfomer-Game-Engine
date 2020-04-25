package ooga.game.controls;

import java.util.List;

public interface InputManager {

  void keyPressed(String key);

  void keyReleased(String key);

  List<String> getActiveKeys();

  List<String> getPressedKeys();

  void mouseClicked(double mouseX, double mouseY);

  List<List<Double>> getMouseClickPos();

  void update();
}
