package ooga.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.Entity;
import ooga.OogaDataException;
import ooga.UserInputListener;
import ooga.data.*;
import ooga.game.OogaGame;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewerGame {

  private static final double MILLISECOND_DELAY = 33.33;
  private ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
  private final String PAUSE_BUTTON_LOCATION = myResources.getString("pauseButtonLocation");
  private final double PAUSE_BUTTON_SIZE = Double.parseDouble(myResources.getString("pauseButtonSize"));
  private final double PAUSE_BUTTON_IMAGE_SIZE = PAUSE_BUTTON_SIZE - 10;
  private final double WINDOW_WIDTH = Double.parseDouble(myResources.getString("windowWidth"));
  private final double WINDOW_HEIGHT = Double.parseDouble(myResources.getString("windowHeight"));
  private List<ViewTextEntity> myTexts = new ArrayList<>();
  private Group myEntityGroup;
  private Group myRoot;
  private String myGameName;
  private Scene myGameScene;
  private Stage myGameStage;
  private PauseMenu myPauseMenu;
  private OogaGame myGame;
  private Timeline myAnimation;
  private String myProfileName;
  private ParallelCamera myCamera;
  private ViewImageEntity focus;
  private boolean cameraOn = false;



  public ViewerGame(String gameName, String profileName, String saveDate) throws OogaDataException {
    myGameName = gameName;
    myProfileName = profileName;
    myCamera = new ParallelCamera();
    //TODO: Update to match the new constructors by adding the date of the save to load
    setGame(saveDate);
    //SAM added this as the way to make a Game once file loading works.
    setUpGameEntities();
    setUpGameStage();
    myRoot.getChildren().add(setUpPauseButton());
    myGameScene.setCamera(myCamera);
    setUpInputListeners(myGame);
  }

  public ViewerGame(String gameName, String profileName, String saveDate ,boolean camera) throws OogaDataException {
    this(gameName,profileName,saveDate);
    cameraOn = camera;
    if(focus!= null){
      myCamera.layoutXProperty().bind(focus.getXProperty());
    }
  }

  private void setGame(String saveDate) throws OogaDataException {
    if(saveDate.equals("")){
      myGame = new OogaGame(myGameName, new OogaDataReader(),myProfileName);
    }
    else myGame = new OogaGame(myGameName, new OogaDataReader(), myProfileName,saveDate);
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
            System.out.println(removedItem.isActiveInView());
            removedItem.setActiveInView(false);
          }
          for (Entity addedItem : c.getAddedSubList()) {
            addedItem.setActiveInView(true);
            addToEntityGroup(addedItem);
          }
        }
      }
    });

    myEntityGroup = new Group();
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

//  private DoubleProperty getOutOfBounds(){
//    if(myCamera.getBoundsInParent().intersects(focus.getNode().getBoundsInParent())){
//      return new SimpleDoubleProperty(myCamera.getLayoutX());
//    }
//    else {
//      return focus.getNode().layoutXProperty();
//    }
//  }
  private Node makeViewEntity(Entity entity){
    // TODO: use reflection here or something
    if(entity instanceof ImageEntity){
      System.out.println(cameraOn);
      ViewImageEntity viewImageEntity = (new ViewImageEntity((ImageEntity)entity));
      if(entity.getName().equals("SmallMario")){
        focus = viewImageEntity;
        //myCamera.layoutYProperty().bind(focus.getYProperty().add(new SimpleDoubleProperty(-450.0)));
      }
      return viewImageEntity.getNode();
    }
    else if(entity instanceof TextEntity){
      ViewTextEntity viewTextEntity = new ViewTextEntity((TextEntity)entity);
      viewTextEntity.getXProperty().bind(myCamera.layoutXProperty().add(new SimpleDoubleProperty(viewTextEntity.getX())));
      return viewTextEntity.getNode();
    }
    return null;
  }



  private Node setUpPauseButton() {
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
    // note: need the below because buttons consume certain key press events (like arrow keys)
    pauseButton.setOnKeyPressed(e -> {
      pauseButton.getParent().fireEvent(e);
    });
    return pauseButton;
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
        // TODO: remove print stack trace
        ex.printStackTrace();
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
    myGameScene = new Scene(myRoot, WINDOW_WIDTH , WINDOW_HEIGHT);
    myGameStage.setScene(myGameScene);
    myGameStage.setTitle(myGameName);
    myGameStage.show();
  }

//  private Node setUpTopBar(){
//    HBox hBox = new HBox();
//    hBox.getChildren().add(setUpPauseButton());
//    for(ViewTextEntity textEntity: myTexts){
//      hBox.getChildren().add(textEntity.getNode());
//    }
//    hBox.layoutXProperty().bind(myCamera.layoutXProperty());
//
//    return hBox;
//  }

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
