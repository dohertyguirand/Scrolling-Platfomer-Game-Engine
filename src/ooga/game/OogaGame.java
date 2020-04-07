package ooga.game;


import java.util.List;
import javafx.collections.ObservableList;
import ooga.Entity;
import ooga.UserInputListener;

public class OogaGame implements Game {
  public OogaGame(String gameName) {
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

  }

  @Override
  public void handleUserInput(String input) {

  }
  @Override
  public UserInputListener makeUserInputListener() {
    return null;
  }
}
