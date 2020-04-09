package ooga.game;


import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import ooga.Entity;
import ooga.OogaDataException;
import ooga.data.DataReader;
import ooga.UserInputListener;
import ooga.data.OogaEntity;
import ooga.data.OogaDataReader;

public class OogaGame implements Game {

  //TODO: Remove hard-coded filepath
  public static final String GAME_LIBRARY_PATH = "data/GamesLibrary/";

  private List<String> myLevelIds;
  private int myLevel;
  private Level currentLevel;
  private String myName;
  private DataReader myDataReader;
  private CollisionDetector myCollisionDetector;
  private ControlsInterpreter myControlsInterpreter;

  public OogaGame() {
    myName = "Unnamed";
    currentLevel = new OogaLevel(new ArrayList<>());
  }

  public OogaGame(String gameName, DataReader dataReader) throws OogaDataException {
    myDataReader = dataReader;
    myLevel = 0;
    myLevelIds = myDataReader.getBasicGameInfo(gameName);
    myName = gameName;
    myDataReader = dataReader;
    myLevelIds = myDataReader.getBasicGameInfo(gameName);
    //TODO: Make the type of collision detector configurable.
    myCollisionDetector = new OogaCollisionDetector();
    //TODO: Remove dependency between controls interpreter implementation and this
    myControlsInterpreter = new KeyboardControls();

    myLevelIds = myDataReader.getBasicGameInfo(gameName);
    currentLevel = myDataReader.loadLevel(gameName,myLevelIds.get(0));
  }

  public OogaGame(String gameName) throws OogaDataException {
    //TODO: Remove dependency between OogaGame and OogaDataReader in constructor
    this(gameName, new OogaDataReader(GAME_LIBRARY_PATH));
  }

  public OogaGame(Level startingLevel) {
    myName = "Unnamed";
    myCollisionDetector = new OogaCollisionDetector();
    myControlsInterpreter = new KeyboardControls();
    currentLevel = startingLevel;
  }

  @Override
  public ObservableList<Entity> getEntities() {
    return currentLevel.getEntities();
  }

  @Override
  public ObservableList<OogaEntity> getAbstractEntities() {
    return null;
  }

  @Override
  public void doGameStart() {

  }

  @Override
  public void doCollisionLoop() {
    for (Entity target : currentLevel.getEntities()) {
      for (Entity collidingWith : currentLevel.getEntities()) {
        if (myCollisionDetector.isColliding(target,collidingWith)) {
          target.handleCollision(collidingWith.getName());
        }
      }
    }
  }

  @Override
  public void doUpdateLoop(double elapsedTime) {
    List<Entity> destroyedEntities = new ArrayList<>();
    for (Entity e : currentLevel.getEntities()) {
      e.updateSelf(elapsedTime);
      if (e.isDestroyed()) {
        destroyedEntities.add(e);
      }
    }
    for (Entity destroyed : destroyedEntities) {
      if (destroyed.isDestroyed()) {
        currentLevel.removeEntity(destroyed);
      }
    }
  }

  @Override
  public void handleUserInput(String input) {
    String inputType = myControlsInterpreter.translateInput(input);
    for (Entity e : currentLevel.getEntities()) {
      e.reactToControls(inputType);
    }
  }

  @Override
  public UserInputListener makeUserInputListener() {
    return null;
  }
}
