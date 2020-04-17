package ooga.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ooga.OogaDataException;

public class Visualizer extends Application {

  private static final String ERROR_MESSAGE = "Error: Internal Data Error";
  private static final String START_MENU_TITLE = "Choose a Game";
  private String profileNameSelected;
  private Stage stage;



  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    stage = primaryStage;
    Scene display = setUpStartMenuDisplay();
    primaryStage.setScene(display);
    primaryStage.setTitle(START_MENU_TITLE);
    primaryStage.show();
    primaryStage.setResizable(false);
  }

  private Scene setUpStartMenuDisplay() {
    ProfileMenu profileMenu = new ProfileMenu();
    profileMenu.profileSelected().addListener((p, poldVal, pnewVal) -> {
      showStartMenu(pnewVal);
    });
    return profileMenu.getScene();
  }

  private void showStartMenu(ViewProfile profile){
    StartMenu startMenu = new StartMenu(profile);
    profileNameSelected = profile.getProfileName();
    startMenu.selectedProperty().addListener((o, oldVal, newVal) -> startGame(newVal, profileNameSelected));
    stage.setScene(startMenu.getScene());
  }

  private void startGame(String gameName, String profileName) {
    if (gameName != null) {
      try {
        new ViewerGame(gameName, profileName);
      } catch (OogaDataException e) {
        //Sam added this, because he made it possible for the OogaGame constructor to throw
        // an exception, so that the view can decide what to do when no game is found.
        showError(e.getMessage());
      }
    }
  }

  private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(ERROR_MESSAGE);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
