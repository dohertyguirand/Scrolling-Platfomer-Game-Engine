package ooga.game;

import java.util.ArrayList;
import java.util.List;

public class OogaInputManager implements InputManager {

  private List<String> myActiveKeys;
  private List<String> myPressedKeys;

  public OogaInputManager() {
    myActiveKeys = new ArrayList<>();
    myPressedKeys = new ArrayList<>();
  }

  @Override
  public void keyPressed(String key) {
    if (!myActiveKeys.contains(key)) {
      myActiveKeys.add(key);
    }
    if (!myPressedKeys.contains(key)) {
      myPressedKeys.add(key);
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
  public void update() {
    myPressedKeys.clear();
  }
}
