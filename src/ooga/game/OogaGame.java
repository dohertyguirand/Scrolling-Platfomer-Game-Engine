package ooga.game;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ooga.Entity;
import ooga.OogaDataException;
import ooga.data.*;
import ooga.UserInputListener;
import ooga.game.collisiondetection.OogaCollisionDetector;
import ooga.game.collisiondetection.VelocityCollisionDetector;

public class OogaGame implements Game, UserInputListener {

  private List<String> myLevelIds;
  private int myLevel;
  private Level currentLevel;
  private String myName;
  private DataReader myDataReader;
  private CollisionDetector myCollisionDetector;
  private ControlsInterpreter myControlsInterpreter;
  private InputManager myInputManager = new OogaInputManager();
  private Map<String, Double> myVariables;
  private ObservableList<Entity> myEntities;

  public OogaGame(String gameName, DataReader dataReader) throws OogaDataException {
//  public OogaGame(String gameName, String userName, DataReader dataReader) throws OogaDataException {
    myDataReader = dataReader;
    myLevel = 0;
    myName = gameName;
    List<List<String>> basicGameInfo = myDataReader.getBasicGameInfo(gameName);
    myLevelIds = basicGameInfo.get(0);
    //TODO: Make the type of collision detector configurable.
    myCollisionDetector = new VelocityCollisionDetector();
    //TODO: Remove dependency between controls interpreter implementation and this
    myControlsInterpreter = new KeyboardControls();
    myEntities = FXCollections.observableArrayList(new ArrayList<>());
    currentLevel = loadGameLevel(gameName, myLevelIds.get(0));
//    currentLevel = loadLevel(gameName,userName)
    myVariables = new HashMap<>();
    for(int i=0; i<basicGameInfo.get(1).size(); i++){
      myVariables.put(basicGameInfo.get(1).get(i), Double.parseDouble(basicGameInfo.get(2).get(i)));
    }
    System.out.println("myVariables = " + myVariables);
  }

  private Level loadGameLevel(String gameName, String id) throws OogaDataException {
    Level level = myDataReader.loadLevel(gameName,id);
    myEntities.clear();
    myEntities.addAll(level.getEntities());
    return level;
  }

  public OogaGame(Level startingLevel) {
    myName = "Unnamed";
    myCollisionDetector = new OogaCollisionDetector();
    myControlsInterpreter = new KeyboardControls();
    currentLevel = startingLevel;
  }

  public OogaGame(Level startingLevel, CollisionDetector collisions) {
    myName = "Unnamed";
    myCollisionDetector = collisions;
    myControlsInterpreter = new KeyboardControls();
    currentLevel = startingLevel;
  }

  @Override
  public ObservableList<Entity> getEntities() {
    return myEntities;
  }

  @Override
  public ObservableList<OogaEntity> getAbstractEntities() {
    return null;
  }

  @Override
  public void doGameStart() {

  }

  /**
   * Updates things in the game according to how much time has passed
   *
   * @param elapsedTime time passed in milliseconds
   */
  @Override
  public void doGameStep(double elapsedTime) {
    doUpdateLoop(elapsedTime);
    myInputManager.update();
  }

  private Map<Entity,List<Entity>> findVerticalCollisions(double elapsedTime) {
    CollisionType<Entity> collisionType = (a,b) -> myCollisionDetector.isCollidingVertically(a,b,elapsedTime);
    return findCollisions(collisionType);
  }

  private Map<Entity,List<Entity>> findHorizontalCollisions(double elapsedTime) {
    CollisionType<Entity> collisionType = (a,b) -> myCollisionDetector.isCollidingHorizontally(a,b,elapsedTime);
    return findCollisions(collisionType);
  }

  private Map<Entity,List<Entity>> findCollisions(CollisionType<Entity> collisionType) {
    Map<Entity,List<Entity>> ret = new HashMap<>();
    for (Entity target : currentLevel.getEntities()) {
      ret.put(target,collidingEntities(collisionType, target));
    }
    return ret;
  }

  private List<Entity> collidingEntities(CollisionType<Entity> collisionType, Entity target) {
    List<Entity> entityCollisions = new ArrayList<>();
    for (Entity collidingWith : currentLevel.getEntities()) {
      if (collidingWith != target && collisionType.isColliding(target,collidingWith)) {
        entityCollisions.add(collidingWith);
        break;
      }
    }
    return entityCollisions;
  }

  private void doUpdateLoop(double elapsedTime) {
    //1. calculate all automatic movement
    //2. calculate all controls-based movement
    //3. find collisions
    //4. calculate effect of collisions.
    //5. execute movement.
    doEntityUpdates(elapsedTime);
    doEntityCollisions(elapsedTime);
//    doVariableUpdates();
    doEntityCleanup();
    executeEntityMovement(elapsedTime);
    doEntityCreation();
    checkLevelEnd();
  }

  private void checkLevelEnd() {
    if (currentLevel.checkEndCondition()) {
      try {
        Level next = myDataReader.loadLevel(myName,currentLevel.nextLevelID());
        currentLevel = next;
      } catch (OogaDataException e) {
        //if the next level fails to load, continue this level.
      }
    }
  }

  private void executeEntityMovement(double elapsedTime) {
    for (Entity e : currentLevel.getEntities()) {
      e.executeMovement(elapsedTime);
    }
  }

  private void doEntityCreation() {
    List<Entity> createdEntities = new ArrayList<>();
    for (Entity e : currentLevel.getEntities()) {
        createdEntities.addAll(e.popCreatedEntities());
    }
    currentLevel.addEntities(createdEntities);
  }

  private void doEntityCleanup() {
    List<Entity> destroyedEntities = new ArrayList<>();
    for (Entity e : currentLevel.getEntities()) {
      if (e.isDestroyed()) {
        destroyedEntities.add(e);
      }
    }
    for (Entity destroyed : destroyedEntities) {
      if (destroyed.isDestroyed()) {
        currentLevel.removeEntity(destroyed);
        System.out.println("destroyed.getName() = " + destroyed.getName());
        myEntities.remove(destroyed);
      }
    }
  }

  private void doEntityCollisions(double elapsedTime) {
    Map<Entity, List<Entity>> verticalCollisions = findVerticalCollisions(elapsedTime);
    Map<Entity,List<Entity>> horizontalCollisions = findHorizontalCollisions(elapsedTime);
    for (Entity e : currentLevel.getEntities()) {
      for (Entity collidingWith : verticalCollisions.get(e)) {
        e.handleVerticalCollision(collidingWith, elapsedTime, myVariables);
      }
      for (Entity collidingWith : horizontalCollisions.get(e)) {
        e.handleHorizontalCollision(collidingWith, elapsedTime, myVariables);
      }
    }
  }

  private void doEntityUpdates(double elapsedTime) {
    for (Entity e : currentLevel.getEntities()) {
      for (String input : myInputManager.getActiveKeys()) {
        e.reactToControls(input);
      }
      for (String input : myInputManager.getPressedKeys()) {
        e.reactToControlsPressed(input);
      }
      e.updateSelf(elapsedTime, myVariables);
      e.reactToVariables(myVariables);
    }
  }

  @Override
  public UserInputListener makeUserInputListener() {
    return this;
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
    String inputType = myControlsInterpreter.translateInput(keyName);
    System.out.println(keyName + " pressed.");
    myInputManager.keyPressed(inputType);
  }

  @Override
  public void reactToKeyRelease(String keyName) {
    String inputType = myControlsInterpreter.translateInput(keyName);
    System.out.println(keyName + " released.");
    myInputManager.keyReleased(inputType);
  }

  /**
   * Handles when the command is given to save the game to a file.
   */
  @Override
  public void reactToGameSave() {
//    myDataReader.saveGameState(String userName, myName);
    System.out.println("GAME SAVED");
    try {
      currentLevel = loadGameLevel(myName, myLevelIds.get(1));
    } catch (OogaDataException e) {
      System.out.println("FAILED TO LOAD LEVEL 1");
    }
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
