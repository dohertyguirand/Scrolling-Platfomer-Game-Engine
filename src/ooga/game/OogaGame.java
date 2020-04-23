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
import ooga.game.collisiondetection.DirectionalCollisionDetector;

public class OogaGame implements Game, UserInputListener, GameInternal {

  public static final String ID_VARIABLE_NAME = "ID";
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
    myDataReader = dataReader;
    myName = gameName;
    //ist<List<String>> basicGameInfo = myDataReader.getBasicGameInfo(gameName);
    myLevelIds = myDataReader.getLevelIDs(gameName);
    //TODO: Make the type of collision detector configurable.
    myCollisionDetector = new DirectionalCollisionDetector();
    //TODO: Remove dependency between controls interpreter implementation and this
    myControlsInterpreter = new KeyboardControls();
    myEntities = FXCollections.observableArrayList(new ArrayList<>());
    myEntityDefinitions = myDataReader.getImageEntityMap(gameName);

    myVariables = new HashMap<>();
//    for(int i=0; i<basicGameInfo.get(1).size(); i++){
//      myVariables.put(basicGameInfo.get(1).get(i), Double.parseDouble(basicGameInfo.get(2).get(i)));
//    }

    for (String key : myDataReader.getVariableMap(gameName).keySet()){
      myVariables.put(key, Double.parseDouble(myDataReader.getVariableMap(gameName).get(key)));
    }
  }

  public OogaGame(String gameName, DataReader dataReader, String profileName) throws OogaDataException {
    this(gameName, dataReader);
    currentLevel = loadGameLevel(gameName, myLevelIds.get(0));
  }

  public OogaGame(String gameName, DataReader dataReader, String profileName, String date) throws OogaDataException {
    this(gameName, dataReader);
    currentLevel = loadGameLevel(gameName, myLevelIds.get(0));
  }

  private Level loadGameLevel(String gameName, String id) throws OogaDataException {
    Level level = myDataReader.loadLevel(gameName,id);
    myEntities.clear();
    myEntities.addAll(level.getEntities());
    return level;
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

  private Map<Entity, Map<String, List<Entity>>> findDirectionalCollisions(double elapsedTime) {
    Map<Entity, Map<String, List<Entity>>> collisionInfo = new HashMap<>();
    for(Entity entity : currentLevel.getEntities()){
      Map<String, List<Entity>> collisionsByDirection = new HashMap<>();
      String[] directions = new String[]{"Up", "Down", "Left", "Right"};
      for(String direction : directions){
        collisionsByDirection.put(direction, new ArrayList<>());
      }
      for(Entity collidingWith : currentLevel.getEntities()){
        //TODO: if needed, compare the exact objects instead of the names (allowing entities with same name to register collisions)
        if(!entity.getName().equals(collidingWith.getName())) {
          String collisionDirection = myCollisionDetector.getCollisionDirection(entity, collidingWith, elapsedTime);
          if (collisionDirection != null){
            collisionsByDirection.get(collisionDirection).add(collidingWith);
          }
        }
      }
      collisionInfo.put(entity, collisionsByDirection);
    }
    return collisionInfo;
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

    doEntityFrameUpdates(elapsedTime);

    doEntityBehaviors(elapsedTime, allInputs);
//    doVariableUpdates();
    doEntityCleanup();
    executeEntityMovement(elapsedTime);
    doEntityCreation();
    checkLevelEnd();
  }

  private void doEntityFrameUpdates(double elapsedTime) {
    for (Entity entity : currentLevel.getEntities()) {
      entity.blockInAllDirections(false);
      entity.updateSelf(elapsedTime, myVariables, this);
      entity.reactToVariables(myVariables);
    }
  }

  private void doEntityBehaviors(double elapsedTime, List<String> allInputs) {
    Map<Entity, Map<String, List<Entity>>> collisionInfo = findDirectionalCollisions(elapsedTime);
    for (Entity entity : currentLevel.getEntities()) {
      entity.doConditionalBehaviors(elapsedTime, allInputs, myVariables, collisionInfo, this);
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

  @Override
  public String getCurrentLevelId() {
    return currentLevel.getLevelId();
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
    //TODO: make this do something??
  }

  @Override
  public void createEntity(String type, List<Double> position) {
    ImageEntityDefinition definition = myEntityDefinitions.get(type);
    Entity created = definition.makeInstanceAt(position.get(0),position.get(1));
    myEntities.add(created);
    currentLevel.addEntity(created);
  }

  @Override
  public Entity getEntityWithId(String id) {
    for (Entity e : myEntities) {
      String entityId = e.getEntityID();
      if (entityId != null && entityId.equals(id)) {
        return e;
      }
    }
    return null;
  }

  @Override
  public List<Entity> getEntitiesWithName(String name) {
    List<Entity> ret = new ArrayList<>();
    for (Entity e : myEntities) {
      if (e.getName().equals(name)) {
        ret.add(e);
      }
    }
    return ret;
  }

  @Override
  public void goToLevel(String levelID) {
    try {
      currentLevel = loadGameLevel(myName,levelID);
    }
    catch (OogaDataException e) {
      //To preserve the pristine gameplay experience, we do nothing (rather than crash).
    }
  }

  @Override
  public void goToNextLevel() {
    goToLevel(currentLevel.nextLevelID());
  }
}
