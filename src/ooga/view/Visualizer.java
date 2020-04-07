package ooga.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.data.DataReader;
import ooga.game.Game;

import java.util.ResourceBundle;

public class Visualizer extends Application {

  private Stage myStage;
  private ResourceBundle myResources;
  private DataReader myDataReader;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    myDataReader = new DataReader();
    myStage = primaryStage;
    Scene display = setUpStartMenuDisplay();
    myStage.setScene(display);
    myStage.setTitle(myResources.getString("StartMenuTitle"));
    myStage.show();
  }

  private Scene setUpStartMenuDisplay() {
    StartMenu startMenu = new StartMenu(myDataReader);
    startMenu.gameSelectedProperty().addListener((o, oldVal, newVal) -> startGame(newVal));
    return startMenu.getScene();
  }

  private void startGame(String gameName) {
    ViewerGame game = new ViewerGame(gameName, myDataReader);
  }

  private void showError(Stage stage, String animation_error, ResourceBundle myResources) {
  }


}
