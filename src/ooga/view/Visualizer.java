package ooga.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ooga.OogaDataException;
import ooga.data.DataReader;
import ooga.data.OogaDataReader;

public class Visualizer extends Application {

  private static final String ERROR_MESSAGE = "Error: Internal Data Error";
  private static final String START_MENU_TITLE = "Choose a Game";
  private String profileNameSelected;
  private Stage stage;
  private DataReader dataReader = new OogaDataReader();
  private String dateSelected;

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
    Scene profileMenuScene = profileMenu.getScene();
    profileMenu.profileSelected().addListener((p, poldVal, pnewVal) -> {
      showStartMenu(pnewVal, profileMenuScene);
    });
    return profileMenuScene;
  }

  private void showStartMenu(ViewProfile profile, Scene returnScene){
    Button backToProfileMenu = makeBackButton(returnScene);
    StartMenu startMenu = new StartMenu(profile,backToProfileMenu);
    Scene gameMenuScene = startMenu.getScene();
    profileNameSelected = profile.getProfileName();
    startMenu.selectedProperty().addListener((o, oldVal, newVal) -> showLoadMenu(newVal, gameMenuScene));
    stage.setScene(gameMenuScene);
  }

  private void showLoadMenu(String gameName, Scene returnScene){
    Button backToStartMenu = makeBackButton(returnScene);
    LoadMenu loadMenu = new LoadMenu(gameName, profileNameSelected,dataReader, backToStartMenu);
    loadMenu.getDateSelected().addListener((d,dold,dnew)->{
        startGame(gameName,profileNameSelected,dnew);
    });
    stage.setScene(loadMenu.getScene());
  }

  private void startGame(String gameName, String profileName, String date) {
    if (gameName != null) {
      try {
        new ViewerGame(gameName,profileName,date);
      } catch (OogaDataException e) {
        //Sam added this, because he made it possible for the OogaGame constructor to throw
        // an exception, so that the view can decide what to do when no game is found.
        showError(e.getMessage());
      }
    }
  }

  private Button makeBackButton(Scene backScene){
    Button button = new Button();
    button.setText("Back");
    button.setOnAction(e->stage.setScene(backScene));
    return button;
  }

  private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(ERROR_MESSAGE);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
