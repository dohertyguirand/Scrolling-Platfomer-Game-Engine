package ooga.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.UserInputListener;
import ooga.data.DataReader;
import ooga.data.Entity;
import ooga.data.ImageEntity;
import ooga.data.TextEntity;
import ooga.game.Game;
import ooga.game.OogaGame;

import java.util.ResourceBundle;

public class ViewerGame {

  private static final double MILLISECOND_DELAY = 1000;
  private static final double WINDOW_WIDTH = 1000;
  private static final double WINDOW_HEIGHT = 800; //TODO: put these in resource file or in data
  private ResourceBundle myResources;
  private Group myEntityGroup;
  private String myGameName;
  private Scene myGameScene;

  public ViewerGame(String gameName, DataReader dataReader){
    myGameName = gameName;
    Game game = new OogaGame(gameName);
    setUpGameEntities(game);
    setUpInputListeners(game);
    setUpGameStage();
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

  private void setUpGameStage() {
    Stage gameStage = new Stage();
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      try {
        step();
      } catch (Exception ex) {
        // note that this should ideally never be thrown
        showError(gameStage, "Animation Error", myResources);
        //myErrorMessage.setText(myLanguageResources.getString("IOError"));
      }
    });
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();

    myGameScene = new Scene(myEntityGroup, WINDOW_WIDTH, WINDOW_HEIGHT);
    gameStage.setScene(myGameScene);
    gameStage.setTitle(myGameName);
    gameStage.show();
  }

  private void step() {
    myEntityGroup.requestLayout();
  }

  private void showError(Stage stage, String animation_error, ResourceBundle myResources) {
  }

  private void setUpInputListeners(Game game) {
    UserInputListener userInputListener = game.makeUserInputListener();
    myGameScene.setOnKeyPressed(e -> userInputListener.reactToKeyPress(e.getCharacter()));
    myGameScene.setOnMouseClicked(e -> userInputListener.reactToMouseClick(e.getX(), e.getY()));
    // add more input types here as needed, like mouse drag events
  }
}
