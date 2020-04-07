//package ooga.view;
//
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.application.Application;
//import javafx.beans.value.ChangeListener;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//import ooga.data.DataReader;
//import ooga.game.Game;
//
//import java.util.ResourceBundle;
//
//public class Visualizer extends Application {
//
//  private static final double MILLISECOND_DELAY = 1000;
//  private Stage myStage;
//  private ResourceBundle myResources;
//  private DataReader myDataReader;
//
//  public static void main(String[] args) {
//    launch(args);
//  }
//
//  @Override
//  public void start(Stage primaryStage) {
//    myDataReader = new DataReader();
//    myStage = primaryStage;
//    Scene display = setUpStartMenuDisplay();
//    myStage.setScene(display);
//    myStage.setTitle(myResources.getString("StartMenuTitle"));
//    myStage.show();
//  }
//
//  private Scene setUpStartMenuDisplay() {
//    StartMenu startMenu = new StartMenu(myDataReader);
//    startMenu.gameSelectedProperty().addListener((o, oldVal, newVal) -> startGame(newVal));
//    return startMenu.getScene();
//  }
//
//  private void startGame(String gameName) {
//    Game game = new Game();
//    // do stuff to set up entities and bindings here
//    // set up user input listeners and handlers
//    // set up a new stage and animation
//    // don't think we need to keep track of what games are running?
//    Stage gameStage = new Stage();
//    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
//      try {
//        step();
//      } catch (Exception ex) {
//        // note that this should ideally never be thrown
//        showError(gameStage, "Animation Error", myResources);
//        //myErrorMessage.setText(myLanguageResources.getString("IOError"));
//      }
//    });
//    Timeline animation = new Timeline();
//    animation.setCycleCount(Timeline.INDEFINITE);
//    animation.getKeyFrames().add(frame);
//    animation.play();
//
//    Scene display = setUpGameScene();
//    gameStage.setScene(display);
//    gameStage.setTitle(myResources.getString("StartMenuTitle"));
//    gameStage.show();
//  }
//
//  private Scene setUpGameScene() {
//    return null;
//  }
//
//  private void step() {
//  }
//
//  private void showError(Stage stage, String animation_error, ResourceBundle myResources) {
//  }
//
//
//}
