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

public class OogaGame implements Game, UserInputListener, GameInternal {

  private List<String> myLevelIds;
  private Level currentLevel;
  private String myName;
  private DataReader myDataReader;
  private CollisionDetector myCollisionDetector;
  private ControlsInterpreter myControlsInterpreter;
  private InputManager myInputManager = new OogaInputManager();
  private Map<String, Double> myVariables;
  private ObservableList<Entity> myEntities;
  Map<String, ImageEntityDefinition> myEntityDefinitions;

  public OogaGame(String gameName, DataReader dataReader) throws OogaDataException {
//  public OogaGame(String gameName, String userName, DataReader dataReader) throws OogaDataException {
    myDataReader = dataReader;
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
    myEntityDefinitions = myDataReader.getImageEntityMap(gameName);

    myVariables = new HashMap<>();
    for(int i=0; i<basicGameInfo.get(1).size(); i++){
      myVariables.put(basicGameInfo.get(1).get(i), Double.parseDouble(basicGameInfo.get(2).get(i)));
    }
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

//  private List<Map<Entity, List<Entity>>> findAllCollisions(double elapsedTime) {
//
//  }

  private List<Entity> collidingEntities(CollisionType<Entity> collisionType, Entity target) {
    List<Entity> entityCollisions = new ArrayList<>();
    for (Entity collidingWith : currentLevel.getEntities()) {
      if (collidingWith != target && target.hasCollisionWith(collidingWith.getName())
          && collisionType.isColliding(target,collidingWith)) {
        currentLevel.registerCollision(target.getName(),collidingWith.getName());
        entityCollisions.add(collidingWith);
//        break;
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
    List<String> activeKeys = myInputManager.getActiveKeys();
    List<String> pressedKeys = new ArrayList<>();
    for(String keyPressed : myInputManager.getPressedKeys()){
      //TODO: Smarten this up, so that it doesn't just change the String
      pressedKeys.add(keyPressed + "Pressed");
    }
    List<String> allInputs = new ArrayList<>(activeKeys);
    allInputs.addAll(pressedKeys);

    doEntityInputReactions(elapsedTime, activeKeys, pressedKeys);

    doEntityCollisionsAndConditionals(elapsedTime, allInputs);
//    doVariableUpdates();
    doEntityCleanup();
    executeEntityMovement(elapsedTime);
    doEntityCreation();
    checkLevelEnd();
  }

  private void doEntityInputReactions(double elapsedTime, List<String> activeKeys, List<String> pressedKeys) {
    for (Entity entity : currentLevel.getEntities()) {
      for (String input : activeKeys) {
        entity.reactToControls(input);
      }
      for (String input : pressedKeys) {
        entity.reactToControlsPressed(input);
      }
      entity.updateSelf(elapsedTime, myVariables);
      entity.reactToVariables(myVariables);
    }
  }

  private void doEntityCollisionsAndConditionals(double elapsedTime, List<String> allInputs) {
//    List<Map<Entity, List<Entity>>> allCollisions = findAllCollisions(elapsedTime);
//    Map<Entity, List<Entity>> verticalCollisions = allCollisions.get(0);
//    Map<Entity,List<Entity>> horizontalCollisions = allCollisions.get(1);
    Map<Entity, List<Entity>> verticalCollisions = findVerticalCollisions(elapsedTime);
    Map<Entity,List<Entity>> horizontalCollisions = findHorizontalCollisions(elapsedTime);
    for (Entity entity : currentLevel.getEntities()) {
      for (Entity collidingWith : verticalCollisions.get(entity)) {
        entity.handleVerticalCollision(collidingWith, elapsedTime, myVariables, this);
      }
      for (Entity collidingWith : horizontalCollisions.get(entity)) {
        entity.handleHorizontalCollision(collidingWith, elapsedTime, myVariables, this);
      }
      entity.doConditionalBehaviors(elapsedTime, allInputs, myVariables, verticalCollisions.get(entity),
              horizontalCollisions.get(entity), this);
    }
  }

  private void checkLevelEnd() {
    if (currentLevel.checkEndCondition()) {
      try {
        currentLevel = loadGameLevel(myName,currentLevel.nextLevelID());
        System.out.println("LOADED LEVEL");
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
        for (Entity e : myEntities) {
          System.out.println(e.getName());
        }
        System.out.println("destroyed.getName() = " + destroyed.getName());
        myEntities.remove(destroyed);
        for (Entity e : myEntities) {
          System.out.println(e.getName());
        }
      }
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

  @Override
  public void createEntity(String type, List<Double> position) {
    ImageEntityDefinition definition = myEntityDefinitions.get(type);
    Entity created = definition.makeInstanceAt(position.get(0),position.get(1));
    myEntities.add(created);
    currentLevel.addEntity(created);
  }
}
