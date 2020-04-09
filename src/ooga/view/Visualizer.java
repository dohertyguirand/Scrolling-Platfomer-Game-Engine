package ooga.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Visualizer extends Application {

  //private ResourceBundle myResources;
  private static final String START_MENU_TITLE = "Choose a Game";

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    Scene display = setUpStartMenuDisplay();
    primaryStage.setScene(display);
    primaryStage.setTitle(START_MENU_TITLE);
    primaryStage.show();
  }

  private Scene setUpStartMenuDisplay() throws IOException {
    StartMenu startMenu = new StartMenu();
    startMenu.gameSelectedProperty().addListener((o, oldVal, newVal) -> startGame(newVal));
    return startMenu.getScene();
  }

  private void startGame(String gameName) {
    new ViewerGame(gameName);
  }
}
