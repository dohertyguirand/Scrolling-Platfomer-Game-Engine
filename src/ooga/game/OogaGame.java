package ooga.game;

import java.util.List;
import ooga.Entity;

public class OogaGame implements Game {

  private List<Level> myLevels;
  private Level currentLevel;

  public OogaGame(List<Level> levels) {
    myLevels = levels;
    if (!levels.isEmpty()) {
      currentLevel = levels.get(0);
    }
  }

  @Override
  public List<Entity> getEntities() {
    return null;
  }

  @Override
  public void doGameStart() {

  }

  @Override
  public void doCollisionLoop() {
  }

  @Override
  public void doUpdateLoop(double elapsedTime) {
    for (Entity e : currentLevel.getEntities()) {
      e.updateSelf(elapsedTime);
    }
  }

  @Override
  public void handleUserInput(String input) {

  }
}
