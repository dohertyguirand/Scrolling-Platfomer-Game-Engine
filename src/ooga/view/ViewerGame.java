package ooga.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.Entity;
import ooga.OogaDataException;
import ooga.UserInputListener;
import ooga.data.ImageEntity;
import ooga.data.OogaDataReader;
import ooga.data.TextEntity;
import ooga.game.KeyboardControls;
import ooga.game.OogaGame;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import ooga.game.collisiondetection.DirectionalCollisionDetector;

public class ViewerGame {

  private static final double MILLISECOND_DELAY = 33.33;
  public static final int NORMAL_BUTTON_XPOS = 300;
  public static final int ALIEN_BUTTON_XPOS = 100;
  public static final String KEYBOARD_INPUT_FILE = "ooga/game/resources/inputs/keyboard";
  private final ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
  private final String PAUSE_BUTTON_LOCATION = myResources.getString("pauseButtonLocation");
  private final String ALIEN_BUTTON_LOCATION = myResources.getString("alienButtonLocation");
  private final String NORMAL_BUTTON_LOCATION = myResources.getString("normalButtonLocation");
  private final double PAUSE_BUTTON_SIZE = Double.parseDouble(myResources.getString("pauseButtonSize"));
  private final double PAUSE_BUTTON_IMAGE_SIZE = PAUSE_BUTTON_SIZE - 10;
  private final double WINDOW_WIDTH = Double.parseDouble(myResources.getString("windowWidth"));
  private final double WINDOW_HEIGHT = Double.parseDouble(myResources.getString("windowHeight"));
  private final Group myEntityGroup = new Group();
  private Group myRoot;
  private final String myGameName;
  private Scene myGameScene;
  private Stage myGameStage;
  private PauseMenu myPauseMenu;
  private OogaGame myGame;
  private Timeline myAnimation;
  private final ObjectProperty<Effect> colorEffectProperty = new SimpleObjectProperty<>();
  private Scene pauseScene;
  private final String myProfileName;
  private final List<DoubleProperty> cameraShift = new ArrayList<>();
  private Exception currentError = null;

  public ViewerGame(String gameName, String profileName, String saveDate) throws OogaDataException {
    myGameName = gameName;
    myProfileName = profileName;
    //TODO: Update to match the new constructors by adding the date of the save to load
    setGame(saveDate);
    setUpGameStage();
    setCameraProperties();
    setUpGameEntities();
    myRoot.getChildren().addAll(setUpPauseButton(), setUpDarkModeButton(), setUpNormalModeButton());
    colorEffectProperty.set(new ColorAdjust());
    setUpInputListeners(myGame);
  }

  private void setCameraProperties(){
    cameraShift.add(new SimpleDoubleProperty());
    cameraShift.add(new SimpleDoubleProperty());
    myGame.setCameraShiftProperties(cameraShift);
  }

  private void setGame(String saveDate) throws OogaDataException {
    if(saveDate == null || saveDate.equals("")){
      myGame = new OogaGame(myGameName, new OogaDataReader(), new DirectionalCollisionDetector(), new KeyboardControls(
          KEYBOARD_INPUT_FILE),myProfileName);
    }
    else {
      System.out.println("USING ALT GAME CONSTRUCTOR");
      myGame = new OogaGame(myGameName, new OogaDataReader(), myProfileName,saveDate);
    }
  }

  private void setUpGameEntities(){
    ObservableList<Entity> gameEntities = myGame.getEntities();
    // add listener here to handle entities being created/removed
    // this listener should set the "active" property of the entity,
    //    which will trigger a listener that removes it from the group
    gameEntities.addListener((ListChangeListener<Entity>) c -> {
      while (c.next()) {
        if(c.wasAdded() || c.wasRemoved()){
          for (Entity removedItem : c.getRemoved()) {
            removedItem.setActiveInView(false);
          }
          for (Entity addedItem : c.getAddedSubList()) {
            addedItem.setActiveInView(true);
            addToEntityGroup(addedItem);
          }
        }
      }
    });
    for(Entity entity : gameEntities){
      addToEntityGroup(entity);
    }
  }


  private void addToEntityGroup(Entity entity) {
    Node viewEntity = makeViewEntity(entity);
    myEntityGroup.getChildren().add(viewEntity);
    entity.activeInViewProperty().addListener((o, oldVal, newVal) -> {
      if(newVal) myEntityGroup.getChildren().add(viewEntity);
      else myEntityGroup.getChildren().remove(viewEntity);
    });
  }

  private Node makeViewEntity(Entity entity){
    // TODO: use reflection here?
    if(entity instanceof ImageEntity){
      ViewImageEntity viewImageEntity = (new ViewImageEntity((ImageEntity)entity, colorEffectProperty,cameraShift));
      return viewImageEntity.getNode();
    }
    else if(entity instanceof TextEntity){
      ViewTextEntity viewTextEntity = new ViewTextEntity((TextEntity)entity,cameraShift);
      return viewTextEntity.getNode();
    }
    return null;
  }

  private Node setUpPauseButton() {
    myPauseMenu = new PauseMenu();
    pauseScene = new Scene(myPauseMenu, myGameScene.getWidth(), myGameScene.getHeight());
    return makeButton(getImage(PAUSE_BUTTON_LOCATION, PAUSE_BUTTON_IMAGE_SIZE), null, 0, "pause");
  }

  @SuppressWarnings("unused")
  private void pause() {
    myGameStage.setScene(pauseScene);
    myPauseMenu.setResumed(false);
    myAnimation.stop();
  }

  private Node setUpDarkModeButton() {
    return makeButton(getImage(ALIEN_BUTTON_LOCATION, PAUSE_BUTTON_IMAGE_SIZE), "Alien Mode", ALIEN_BUTTON_XPOS, "setDarkMode");
  }

  private Node setUpNormalModeButton(){
    return makeButton(getImage(NORMAL_BUTTON_LOCATION, PAUSE_BUTTON_IMAGE_SIZE), "Normal Mode", NORMAL_BUTTON_XPOS, "setNormalMode");
  }

  @SuppressWarnings("unused")
  private void setNormalMode(){
    colorEffectProperty.set(new ColorAdjust());
    myGameScene.getRoot().setStyle("-fx-base: rgba(255, 255, 255, 255)");
  }

  @SuppressWarnings("unused")
  private void setDarkMode(){
    colorEffectProperty.set(new ColorAdjust(0.5, 0.2, 0.0 ,0.0));
    myGameScene.getRoot().setStyle("-fx-base: rgba(60, 63, 65, 255)");
  }

  private Node makeButton(Node graphic, String text, double xPos, String methodName) {
    Button button = new Button(text);
    button.setGraphic(graphic);
    button.setOnAction(e -> {
      try {
        this.getClass().getDeclaredMethod(methodName).invoke(this);
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
      }
    });
    button.setLayoutX(xPos);
    button.setLayoutY(0);
    // note: need the below because buttons consume certain key press events (like arrow keys)
    button.setOnKeyPressed(e -> button.getParent().fireEvent(e));
    return button;
  }

  private ImageView getImage(String location, double size){
    ImageView imageView = new ImageView(location);
    imageView.setFitHeight(size);
    imageView.setFitWidth(size);
    return imageView;
  }

  private void setUpGameStage() {
    myGameStage = new Stage();
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      try {
        step();
      } catch (Exception ex) {
        // note that this should ideally never be thrown
        if(currentError == null || !ex.getClass().equals(currentError.getClass())) {
          myAnimation.stop();
          showError(ex.getMessage());
          currentError = ex;
        }
      }
    });
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames().add(frame);
    myAnimation.play();

    myRoot = new Group();
    myRoot.getChildren().add(myEntityGroup);
    myRoot.getChildren().add(new Line(0, PAUSE_BUTTON_SIZE, WINDOW_WIDTH, PAUSE_BUTTON_SIZE));
    myGameScene = new Scene(myRoot, WINDOW_WIDTH , WINDOW_HEIGHT);
    myGameStage.setScene(myGameScene);
    myGameStage.setTitle(myGameName);
    myGameStage.show();
  }

  private void step() {
    myGame.doGameStep(myAnimation.getCurrentTime().toMillis());
  }

  @SuppressWarnings("unused")
  private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText(message);
    alert.show();
    alert.setOnCloseRequest(e -> myAnimation.play());
  }

  private void setUpInputListeners(UserInputListener userInputListener) {
    setUpPauseMenuListeners(userInputListener);
    myGameScene.setOnKeyPressed(e -> userInputListener.reactToKeyPress(e.getCode().getName()));
    myGameScene.setOnKeyReleased(e -> userInputListener.reactToKeyRelease(e.getCode().getName()));
    myGameScene.setOnMouseClicked(e -> userInputListener.reactToMouseClick(e.getX(), e.getY()-PAUSE_BUTTON_SIZE));
    // add more input types here as needed, like mouse drag events
  }

  private void setUpPauseMenuListeners(UserInputListener userInputListener) {
    myPauseMenu.resumedProperty().addListener((o, oldVal, newVal) -> {
      if(newVal){
        myGameStage.setScene(myGameScene);
        userInputListener.reactToPauseButton(false);
        myAnimation.play();
      }
    });
    myPauseMenu.quitProperty().addListener((o, oldVal, newVal) -> {
      myGameStage.close();
      myAnimation.stop();
      myGame = null;
    });
    myPauseMenu.saveProperty().addListener((o, oldVal, newVal) -> userInputListener.reactToGameSave());
  }
}
