package ooga.game;

import javafx.collections.ObservableList;
import ooga.UserInputListener;
import ooga.data.Entity;

public class OogaGame implements Game {

<<<<<<< HEAD
  private List<Level> myLevels;
  private Level currentLevel;

  public OogaGame(List<Level> levels) {
    myLevels = levels;
    if (!levels.isEmpty()) {
      currentLevel = levels.get(0);
    }
=======
  public OogaGame(String gameName) {
>>>>>>> master
  }

  @Override
  public ObservableList<Entity> getEntities() {
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

  @Override
  public UserInputListener makeUserInputListener() {
    return null;
  }
}
