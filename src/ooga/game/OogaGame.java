package ooga.game;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import ooga.Entity;
import ooga.OogaDataException;
import ooga.data.DataReader;
import ooga.UserInputListener;
import ooga.data.ImageEntity;
import ooga.data.OogaEntity;
import ooga.data.OogaDataReader;
import ooga.game.framebehavior.MoveForwardBehavior;
import ooga.game.inputbehavior.JumpBehavior;

public class OogaGame implements Game, UserInputListener {

  //TODO: Remove hard-coded filepath
  public static final String gameLibraryPath = "data/GamesLibrary/";

  private List<String> myLevelIds;
  private int myLevel;
  private Level currentLevel;
  private String myName;
  private DataReader myDataReader;
  private CollisionDetector myCollisionDetector;
  private ControlsInterpreter myControlsInterpreter;

  public OogaGame() {
    myName = "Unnamed";
    //TODO: Remove dependency between OogaGame and ImageEntity
    Entity sampleEntity = new ImageEntity("entity1");
    sampleEntity.setMovementBehaviors(List.of(new MoveForwardBehavior(1.0,0.1)));
    sampleEntity.setPosition(List.of(-100.0,100.0));
    Entity otherEntity = new ImageEntity("entity2");
    sampleEntity.setControlsBehaviors(Map.of("UpKey",List.of(new JumpBehavior(1.0))));
    currentLevel = new OogaLevel(List.of(sampleEntity,otherEntity));
  }

  public OogaGame(String gameName, DataReader dataReader) throws OogaDataException {
    myName = gameName;
    myDataReader = dataReader;
    myLevelIds = myDataReader.getBasicGameInfo(gameName);
  }

  public OogaGame(String gameName) throws OogaDataException {
    myLevel = 0;
    myName = gameName;
    //TODO: Remove dependency between OogaGame and OogaDataReader in constructor
    myDataReader = new OogaDataReader();
    myLevelIds = myDataReader.getBasicGameInfo(gameName);
    //TODO: Make the type of collision detector configurable.
    myCollisionDetector = new OogaCollisionDetector();
    //TODO: Remove dependency between controls interpreter implementation and this
    myControlsInterpreter = new KeyboardControls();
    myLevelIds = myDataReader.getBasicGameInfo(gameName);
    currentLevel = myDataReader.loadLevel(gameName,myLevelIds.get(0));
  }

  public OogaGame(Level startingLevel) {
    myName = "Unnamed";
    myCollisionDetector = new OogaCollisionDetector();
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

  /**
   * Updates things in the gaem according to how much time has passed
   *
   * @param elapsedTime time passed in milliseconds
   */
  @Override
  public void doGameStep(double elapsedTime) {

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

  /**
   * @param mouseX The X-coordinate of the mouse click, in in-game screen coordinates
   *               (not "view-relative" coordinates).
   * @param mouseY The Y-coordinate of the mouse click, in-game screen coordinates.
   */
  @Override
  public void reactToMouseClick(double mouseX, double mouseY) {

  }

  /**
   * React to a key being pressed. Use data files to determine appropriate action
   *
   * @param keyName string name of key
   */
  @Override
  public void reactToKeyPress(String keyName) {

  }

  /**
   * Handles when the command is given to save the game to a file.
   */
  @Override
  public void reactToGameSave() {

  }

  /**
   * Handles when the command is given to quit the currently running game.
   * This might be modified to account for identifying which game must close when
   * there are multiple games open.
   */
  @Override
  public void reactToGameQuit() {

  }

  /**
   * indicates the pause button was clicked in the ui
   *
   * @param paused whether or not the button clicked was pause or resume
   */
  @Override
  public void reactToPauseButton(boolean paused) {

  }
}
