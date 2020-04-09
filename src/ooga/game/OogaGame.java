package ooga.game;


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
    }

  public OogaGame(Level startingLevel) {
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
  }

  @Override
  public void doUpdateLoop(double elapsedTime) {
    for (EntityAPI e : currentLevel.getEntities()) {
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
