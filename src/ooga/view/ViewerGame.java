package ooga.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.Entity;
import ooga.OogaDataException;
import ooga.UserInputListener;
import ooga.data.*;
import ooga.game.ControlsTestGameCreation;
import ooga.game.Game;
import ooga.game.OogaGame;

import java.util.ResourceBundle;

public class ViewerGame {

  private static final double MILLISECOND_DELAY = 33.33;
  private ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
  private final String PAUSE_BUTTON_LOCATION = myResources.getString("pauseButtonLocation");
  private final double PAUSE_BUTTON_SIZE = Double.parseDouble(myResources.getString("pauseButtonSize"));
  private final double PAUSE_BUTTON_IMAGE_SIZE = PAUSE_BUTTON_SIZE - 10;
  private final double WINDOW_WIDTH = Double.parseDouble(myResources.getString("windowWidth"));
  private final double WINDOW_HEIGHT = Double.parseDouble(myResources.getString("windowHeight"));
  private Group myEntityGroup;
  private Group myRoot;
  private String myGameName;
  private Scene myGameScene;
  private Stage myGameStage;
  private PauseMenu myPauseMenu;
  private Game myGame;
  private Timeline myAnimation;
  private String myProfileName;


  public ViewerGame(String gameName) throws OogaDataException {
    myGameName = gameName;
    OogaGame targetGame =  new OogaGame(gameName, new OogaDataReader());
//    targetGame = ControlsTestGameCreation.getGame();
    myGame = targetGame;
    //SAM added this as the way to make a Game once file loading works.
    setUpGameEntities();
    setUpGameStage();
    setUpPauseButton();
    setUpInputListeners(targetGame);
  }
  public ViewerGame(String gameName, String profileName) throws OogaDataException {
    this(gameName);
    myProfileName = profileName;
    System.out.println(myProfileName);
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
            myEntityGroup.getChildren().add(makeViewEntity(addedItem));
          }
        }
      }
    });

    myEntityGroup = new Group();
    for(Entity entity : gameEntities){
      Node viewEntity = makeViewEntity(entity);
      myEntityGroup.getChildren().add(viewEntity);
      entity.activeInViewProperty().addListener((o, oldVal, newVal) -> {
        if(newVal) myEntityGroup.getChildren().add(viewEntity);
        else myEntityGroup.getChildren().remove(viewEntity);
      });
    }
  }

  private Node makeViewEntity(Entity entity){
    // TODO: use reflection here or something
    if(entity instanceof ImageEntity){
      return (new ViewImageEntity((ImageEntity)entity)).getNode();
    }
    else if(entity instanceof TextEntity){
      return (new ViewTextEntity((TextEntity)entity)).getNode();
    }
    return null;
  }

  private void setUpPauseButton() {
    myPauseMenu = new PauseMenu();
    Scene pauseScene = new Scene(myPauseMenu, myGameScene.getWidth(), myGameScene.getHeight());
    Button pauseButton = new Button();
    pauseButton.setGraphic(getPauseButtonImage());
    pauseButton.setOnAction(e -> {
      myGameStage.setScene(pauseScene);
      myPauseMenu.setResumed(false);
      myAnimation.stop();
    });
    pauseButton.setLayoutX(0);
    pauseButton.setLayoutY(0);
    myRoot.getChildren().add(pauseButton);
  }

  private ImageView getPauseButtonImage(){
    ImageView imageView = new ImageView(PAUSE_BUTTON_LOCATION);
    imageView.setFitHeight(PAUSE_BUTTON_IMAGE_SIZE);
    imageView.setFitWidth(PAUSE_BUTTON_IMAGE_SIZE);
    return imageView;
  }

  private void setUpGameStage() {
    myGameStage = new Stage();
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      try {
        step();
      } catch (Exception ex) {
        // note that this should ideally never be thrown
        //ex.printStackTrace();
        System.out.println("Animation Error, something went horribly wrong. Cannot display error window because it is" +
                "not allowed during animation processing");
      }
    });
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames().add(frame);
    myAnimation.play();

    myRoot = new Group();
    myRoot.getChildren().add(myEntityGroup);
    myRoot.getChildren().add(new Line(0, PAUSE_BUTTON_SIZE, WINDOW_WIDTH, PAUSE_BUTTON_SIZE));
    myGameScene = new Scene(myRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
    myGameStage.setScene(myGameScene);
    myGameStage.setTitle(myGameName);
    myGameStage.show();
  }

  private void step() {
    myGame.doGameStep(myAnimation.getCurrentTime().toMillis());
    myRoot.requestLayout();
  }

  private void showError(String message) {
    System.out.println(message);
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText(message);
    alert.showAndWait();
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
      userInputListener.reactToGameQuit();
      myGameStage.close();
      myAnimation.stop();
      myGame = null;
    });
    myPauseMenu.saveProperty().addListener((o, oldVal, newVal) -> userInputListener.reactToGameSave());
  }
}
