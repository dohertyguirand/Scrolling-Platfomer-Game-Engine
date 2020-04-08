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
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.UserInputListener;
import ooga.data.*;
import ooga.game.Game;
import ooga.game.OogaGame;

import java.util.ResourceBundle;

public class ViewerGame {

  private static final double MILLISECOND_DELAY = 1000;
  public static final double WINDOW_WIDTH = 1000;
  public static final double WINDOW_HEIGHT = 800; //TODO: put these in resource file or in data
  private ResourceBundle myResources;
  private Group myEntityGroup;
  private Group myRoot;
  private String myGameName;
  private Scene myGameScene;
  private Stage myGameStage;
  private PauseMenu myPauseMenu;
  private OogaDataReader myDataReader;

  public ViewerGame(String gameName, OogaDataReader dataReader){
    myDataReader = dataReader;
    myGameName = gameName;
    Game game = new OogaGame(gameName);
    setUpGameEntities(game);
    setUpGameStage();
    setUpPauseButton();
    setUpInputListeners(game);
  }

  private void setUpGameEntities(Game game){
    ObservableList<Entity> gameEntities = game.getEntities();
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
    if(entity.getClass().equals(ImageEntity.class)){
      return (new ViewImageEntity((ImageEntity)entity)).getNode();
    }
    else if(entity.getClass().equals(TextEntity.class)){
      return (new ViewTextEntity((TextEntity)entity)).getNode();
    }
    return null;
  }

  private void setUpPauseButton() {
    myPauseMenu = new PauseMenu();
    Scene pauseScene = new Scene(myPauseMenu);
    Button pauseButton = new Button();
//    pauseButton.setGraphic(myDataReader.getPauseButtonImage());
    pauseButton.setOnAction(e -> {
      myGameStage.setScene(pauseScene);
      myPauseMenu.setResumed(false);
    });
    pauseButton.setLayoutX(0);
    pauseButton.setLayoutY(0);
    myRoot.getChildren().add(pauseButton);
  }

  private void setUpGameStage() {
    myGameStage = new Stage();
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      try {
        step();
      } catch (Exception ex) {
        // note that this should ideally never be thrown
        showError("Animation Error");
        //myErrorMessage.setText(myLanguageResources.getString("IOError"));
      }
    });
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();

    myRoot = new Group();
    myRoot.getChildren().add(myEntityGroup);
    myGameScene = new Scene(myRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
    myGameStage.setScene(myGameScene);
    myGameStage.setTitle(myGameName);
    myGameStage.show();
  }

  private void step() {
    myRoot.requestLayout();
  }

  private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText(message);
    alert.showAndWait();
  }

  private void setUpInputListeners(Game game) {
    UserInputListener userInputListener = game.makeUserInputListener();
    setUpPauseMenuListeners(userInputListener);
    myGameScene.setOnKeyPressed(e -> userInputListener.reactToKeyPress(e.getCharacter()));
    myGameScene.setOnMouseClicked(e -> userInputListener.reactToMouseClick(e.getX(), e.getY()));
    // add more input types here as needed, like mouse drag events
  }

  private void setUpPauseMenuListeners(UserInputListener userInputListener) {
    myPauseMenu.resumedProperty().addListener((o, oldVal, newVal) -> {
      if(newVal){
        myGameStage.setScene(myGameScene);
        userInputListener.reactToPauseButton(false);
      }
    });
    myPauseMenu.quitProperty().addListener((o, oldVal, newVal) -> {
      userInputListener.reactToGameQuit();
      quitGame();
    });
    myPauseMenu.saveProperty().addListener((o, oldVal, newVal) -> userInputListener.reactToGameSave());
  }

  private void quitGame() {
    // close the window?
  }
}
