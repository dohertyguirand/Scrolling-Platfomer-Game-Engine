package ooga.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ooga.OogaDataException;

import java.io.IOException;

public class Visualizer extends Application {

  private static final String GAME_NOT_FOUND_MESSAGE = "Error: Game not found";
  private static final String START_MENU_TITLE = "Choose a Game";
  private Stage stage;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    stage = primaryStage;
    Scene display = setUpStartMenuDisplay();
    primaryStage.setScene(display);
    primaryStage.setTitle(START_MENU_TITLE);
    primaryStage.show();
    primaryStage.setResizable(false);
  }

  private Scene setUpStartMenuDisplay() {
    ProfileMenu profileMenu = new ProfileMenu();
    profileMenu.addImages(); // to be removed when getting profile thumbnails from backend is implemented
    StartMenu startMenu = new StartMenu();
    profileMenu.selectedProperty().addListener((o, oldVal, newVal) -> { stage.setScene(startMenu.getScene()); });
    startMenu.selectedProperty().addListener((o, oldVal, newVal) -> startGame(newVal));
    return profileMenu.getScene();
  }

  private void startGame(String gameName) {
    if (gameName != null) {
      try {
        new ViewerGame(gameName);
      } catch (OogaDataException e) {
        //Sam added this, because he made it possible for the OogaGame constructor to throw
        // an exception, so that the view can decide what to do when no game is found.
        showError();
      }
    }
  }

  private void showError() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText(GAME_NOT_FOUND_MESSAGE);
    alert.showAndWait();
  }
}
