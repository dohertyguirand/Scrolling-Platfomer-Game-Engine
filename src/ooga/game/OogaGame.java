package ooga.game;


import java.util.List;
import javafx.collections.ObservableList;
import ooga.data.Entity;
import ooga.UserInputListener;

  public class OogaGame implements Game {

    private List<Level> myLevels;
    private Level currentLevel;
    private String myName;

//  public OogaGame(List<Level> levels) {
//    myLevels = levels;
//    if (!levels.isEmpty()) {
//      currentLevel = levels.get(0);
//    }
//  }
  public OogaGame(String gameName) {
    myName = gameName;
  }

  @Override
  public ObservableList<Entity> getEntities() {
    return null;
  } //return myLevel.getEntities

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
