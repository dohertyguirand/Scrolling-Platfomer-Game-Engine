package ooga.game.controls.inputmanagers;

import java.util.ArrayList;
import java.util.List;
import ooga.game.controls.InputManager;

public class OogaInputManager implements InputManager {

  private final List<String> myActiveKeys;
  private final List<String> myPressedKeys;
  private final List<List<Double>> mouseClickedPos;

  public OogaInputManager() {
    myActiveKeys = new ArrayList<>();
    myPressedKeys = new ArrayList<>();
    mouseClickedPos = new ArrayList<>();
  }

  @Override
  public void keyPressed(String key) {
    if (!myActiveKeys.contains(key)) {
      myActiveKeys.add(key);
      if (!myPressedKeys.contains(key)) {
        myPressedKeys.add(key);
      }
    }
  }

  @Override
  public void keyReleased(String key) {
    myActiveKeys.remove(key);
  }

  @Override
  public List<String> getActiveKeys() {
    return new ArrayList<>(myActiveKeys);
  }

  @Override
  public List<String> getPressedKeys() {
    return new ArrayList<>(myPressedKeys);
  }

  @Override
  public void mouseClicked(double mouseX, double mouseY) {
    mouseClickedPos.add(List.of(mouseX,mouseY));
  }

  @Override
  public List<List<Double>> getMouseClickPos() {
    return new ArrayList<>(mouseClickedPos);
  }

  @Override
  public void update() {
    myPressedKeys.clear();
    mouseClickedPos.clear();
  }
}
