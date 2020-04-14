package ooga.game;


import java.util.ArrayList;
import java.util.HashMap;
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
import ooga.game.asyncbehavior.DestroySelfBehavior;
import ooga.game.framebehavior.GravityBehavior;
import ooga.game.framebehavior.MoveForwardBehavior;
import ooga.game.inputbehavior.JumpBehavior;

public class OogaGame implements Game, UserInputListener {

  //TODO: Remove hard-coded filepath
  public static final String GAME_LIBRARY_PATH = "data/games-library/";

  private List<String> myLevelIds;
  private int myLevel;
  private Level currentLevel;
  private String myName;
  private DataReader myDataReader;
  private CollisionDetector myCollisionDetector;
  private ControlsInterpreter myControlsInterpreter;
  private List<String> myActiveKeys = new ArrayList<>();

  public OogaGame() {
    myName = "Unnamed";
    myControlsInterpreter = new KeyboardControls();
    myCollisionDetector = new OogaCollisionDetector();
    //TODO: Remove dependency between OogaGame and ImageEntity
    List<Entity> entities = new ArrayList<>();
    Entity sampleEntity = new ImageEntity("dino", "file:data/games-library/example-dino/blue_square.jpg");
//    sampleEntity.setMovementBehaviors(List.of(new MoveForwardBehavior(100/1000.0,0),
//                                              new GravityBehavior(0,100.0/1000)));
    sampleEntity.setMovementBehaviors(List.of(new GravityBehavior(0,100.0/1000)));
    sampleEntity.setControlsBehaviors(Map.of("UpKey",List.of(new JumpBehavior(-1200.0/1000))));
    sampleEntity.setCollisionBehaviors(Map.of("cactus",List.of(new DestroySelfBehavior())));
    sampleEntity.setPosition(List.of(400.0-300,400.0));
    entities.add(sampleEntity);

    for (int i = 0; i < 10; i ++) {
      Entity otherEntity = new ImageEntity("cactus","file:data/games-library/example-dino/black_square.png");
      otherEntity.setMovementBehaviors(List.of(new MoveForwardBehavior(-450.0/1000,0)));
      otherEntity.setPosition(List.of(1200.0 + 800 * i,400.0));
      entities.add(otherEntity);
    }

    currentLevel = new OogaLevel(entities);
    myControlsInterpreter = new KeyboardControls();
    myCollisionDetector = new OogaCollisionDetector();
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

  public OogaGame(Level startingLevel, CollisionDetector collisions) {
    myName = "Unnamed";
    myCollisionDetector = collisions;
    myControlsInterpreter = new KeyboardControls();
    currentLevel = startingLevel;
  }

  @Override
  public ObservableList<Entity> getEntities() {
    System.out.println("currentLevel = " + currentLevel);
    System.out.println("currentLevel.getEntities() = " + currentLevel.getEntities());
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


    doUpdateLoopNew(elapsedTime, new HashMap<>());
//    doCollisionLoop();
  }

  @Override
  public void doCollisionLoop(double elapsedTime) {
    List<Entity> destroyedEntities = new ArrayList<>();
    for (Entity target : currentLevel.getEntities()) {
      for (Entity collidingWith : currentLevel.getEntities()) {
        if (collidingWith != target && myCollisionDetector.isColliding(target,collidingWith, elapsedTime)) {
          target.handleCollision(collidingWith, elapsedTime);
          if (target.isDestroyed()) {
            destroyedEntities.add(target);
          }
        }
      }
    }
    for (Entity destroyed : destroyedEntities) {
      if (destroyed.isDestroyed()) {
        currentLevel.removeEntity(destroyed);
      }
    }
  }

  private Map<Entity,List<Entity>> findVerticalCollisions(double elapsedTime) {
    CollisionType<Entity> collisionType = (a,b,t) -> myCollisionDetector.isCollidingVertically(a,b,t);
    return findCollisions(collisionType,elapsedTime);
  }

  private Map<Entity,List<Entity>> findHorizontalCollisions(double elapsedTime) {
    //TODO: Modify this so that elapsedTime isn't a parameter to findCollisions.
    CollisionType<Entity> collisionType = (a,b,t) -> myCollisionDetector.isCollidingHorizontally(a,b,t);
    return findCollisions(collisionType,elapsedTime);
  }

  private Map<Entity,List<Entity>> findCollisions(CollisionType<Entity> collisionType, double elapsedTime) {
    Map<Entity,List<Entity>> ret = new HashMap<>();
    for (Entity target : currentLevel.getEntities()) {
      List<Entity> entityCollisions = new ArrayList<>();
      for (Entity collidingWith : currentLevel.getEntities()) {
        if (collidingWith != target) {
          if (collisionType.isColliding(target,collidingWith,elapsedTime)) {
            entityCollisions.add(collidingWith);
            break;
          }
        }
      }
      ret.put(target,entityCollisions);
    }
    return ret;
  }

  @Override
  public void doUpdateLoop(double elapsedTime) {
    List<Entity> destroyedEntities = new ArrayList<>();
    for (Entity e : currentLevel.getEntities()) {
      for (String input : myActiveKeys) {
        e.reactToControls(input);
      }
      e.updateSelf(elapsedTime, new ArrayList<>());
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

  public void doUpdateLoopNew(double elapsedTime, Map<Entity,List<Entity>> collisions) {
    //1. calculate all automatic movement
    //2. calculate all controls-based movement
    //3. find collisions
    //4. calculate effect of collisions.
    //5. execute movement.
    for (Entity e : currentLevel.getEntities()) {
      for (String input : myActiveKeys) {
        e.reactToControls(input);
      }
      e.updateSelf(elapsedTime, new ArrayList<>());
    }
    Map<Entity,List<Entity>> verticalCollisions = findVerticalCollisions(elapsedTime);
    Map<Entity,List<Entity>> horizontalCollisions = findHorizontalCollisions(elapsedTime);
    for (Entity e : currentLevel.getEntities()) {
      for (Entity collidingWith : verticalCollisions.get(e)) {
        e.handleVerticalCollision(collidingWith, elapsedTime);
      }
      for (Entity collidingWith : horizontalCollisions.get(e)) {
        e.handleHorizontalCollision(collidingWith, elapsedTime);
      }
      e.executeMovement(elapsedTime);
    }

//    List<Entity> destroyedEntities = new ArrayList<>();
//    for (Entity e : currentLevel.getEntities()) {
//      for (String input : myActiveKeys) {
//        e.reactToControls(input);
//      }
//      e.updateSelf(elapsedTime, collisions.get(e));
//      if (e.isDestroyed()) {
//        destroyedEntities.add(e);
//      }
//    }
//    for (Entity destroyed : destroyedEntities) {
//      if (destroyed.isDestroyed()) {
//        currentLevel.removeEntity(destroyed);
//      }
//    }
  }

  @Override
  public void handleUserInput(String input) {
    String inputType = myControlsInterpreter.translateInput(input);
    for (Entity e : currentLevel.getEntities()) {
//      e.reactToControls(inputType);
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
    String inputType = myControlsInterpreter.translateInput(keyName);
    System.out.println(keyName + "pressed.");
    if (!myActiveKeys.contains(inputType)) {
      myActiveKeys.add(inputType);
    }
    handleUserInput(keyName);
  }

  @Override
  public void reactToKeyRelease(String keyName) {
    String inputType = myControlsInterpreter.translateInput(keyName);
    System.out.println(keyName + "released.");
    myActiveKeys.remove(inputType);
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
