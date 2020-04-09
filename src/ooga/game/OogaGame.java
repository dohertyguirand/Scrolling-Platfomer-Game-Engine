package ooga.game;


import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import ooga.EntityAPI;
import ooga.OogaDataException;
import ooga.data.DataReader;
import ooga.UserInputListener;
import ooga.data.Entity;
import ooga.data.OogaDataReader;

public class OogaGame implements Game {

    private List<Integer> myLevelIds;
    private Level currentLevel;
    private String myName;
    private DataReader myDataReader;
    private CollisionDetector myCollisionDetector;

  public OogaGame(String gameName, DataReader dataReader) throws OogaDataException {
    myName = gameName;
    myDataReader = dataReader;
    myLevelIds = myDataReader.getBasicGameInfo(gameName);
  }

  public OogaGame(String gameName) throws OogaDataException {
    myName = gameName;
    //TODO: Remove dependency between OogaGame and OogaDataReader in constructor
    myDataReader = new OogaDataReader();
    myLevelIds = myDataReader.getBasicGameInfo(gameName);
    //TODO: Make the type of collision detector configurable.
    myCollisionDetector = new OogaCollisionDetector();
  }

  public OogaGame(Level startingLevel) {
    myName = "Unnamed";
    myCollisionDetector = new OogaCollisionDetector();
    currentLevel = startingLevel;
  }

  @Override
  public ObservableList<EntityAPI> getEntities() {
    return currentLevel.getEntities();
  }

  @Override
  public ObservableList<Entity> getAbstractEntities() {
    return null;
  }

  @Override
  public void doGameStart() {

  }

  @Override
  public void doCollisionLoop() {
    for (EntityAPI target : currentLevel.getEntities()) {
      for (EntityAPI collidingWith : currentLevel.getEntities()) {
        if (myCollisionDetector.isColliding(target,collidingWith)) {
          target.handleCollision(collidingWith.getName());
        }
      }
    }
  }

  @Override
  public void doUpdateLoop(double elapsedTime) {
    List<EntityAPI> destroyedEntities = new ArrayList<>();
    for (EntityAPI e : currentLevel.getEntities()) {
      e.updateSelf(elapsedTime);
      if (e.isDestroyed()) {
        destroyedEntities.add(e);
      }
    }
    for (EntityAPI destroyed : destroyedEntities) {
      if (destroyed.isDestroyed()) {
        currentLevel.removeEntity(destroyed);
      }
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
