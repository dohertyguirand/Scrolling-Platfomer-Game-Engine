package ooga.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ooga.OogaDataException;
import ooga.data.DataReader;
import ooga.data.OogaDataReader;

import java.util.ResourceBundle;

public class Visualizer extends Application {
  private final ResourceBundle gameLanguage = ResourceBundle.getBundle("ooga/view/Resources/languages.French");
  private final String ERROR_MESSAGE = gameLanguage.getString("ErrorMessage");
  private final String START_MENU_TITLE = gameLanguage.getString("StageTitle");
  private final String BACK_BUTTON_TEXT = gameLanguage.getString("Back");
  private String profileNameSelected;
  private Stage stage;
  private final DataReader dataReader = new OogaDataReader();




  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    stage = primaryStage;
    showProfileMenu();
    primaryStage.setTitle(START_MENU_TITLE);
    primaryStage.show();
    primaryStage.setResizable(false);
  }

  private void showProfileMenu() {
    ProfileMenu profileMenu = new ProfileMenu(gameLanguage);
    Scene profileMenuScene = new Scene(profileMenu,profileMenu.getWidth(),profileMenu.getHeight());
    profileMenu.profileSelected().addListener((p, poldVal, pnewVal) ->{
      profileNameSelected = pnewVal.getProfileName();
      showStartMenu(pnewVal, profileMenuScene);
    });
    stage.setScene(profileMenuScene);
  }

  private void showStartMenu(ViewProfile profile, Scene returnScene){
    Button backToProfileMenu = makeBackButton(returnScene);
    GameMenu gameMenu = new GameMenu(gameLanguage,profile,backToProfileMenu);
    Scene gameMenuScene = new Scene(gameMenu, gameMenu.getWidth(), gameMenu.getHeight());
    gameMenu.selectedProperty().addListener((o, oldVal, newVal) -> showLoadMenu(newVal, gameMenuScene));
    stage.setScene(gameMenuScene);
  }

  private void showLoadMenu(String gameName, Scene returnScene){
    Button backToStartMenu = makeBackButton(returnScene);
    LoadMenu loadMenu = new LoadMenu(gameLanguage,gameName, profileNameSelected,dataReader, backToStartMenu);
    Scene loadScene = new Scene(loadMenu, loadMenu.getWidth(),loadMenu.getHeight());
    loadMenu.getDateSelected().addListener((d,dold,dnew)-> startGame(gameName,profileNameSelected,dnew));
    stage.setScene(loadScene);
  }

  private void startGame(String gameName, String profileName, String date) {
    if (gameName != null) {
      try {
        new ViewerGame(gameName,profileName,date, gameLanguage);
      } catch (OogaDataException e) {
        //Sam added this, because he made it possible for the OogaGame constructor to throw
        // an exception, so that the view can decide what to do when no game is found.
        showError(e.getMessage());
      }
    }
  }
  private Button makeBackButton(Scene backScene){
    Button button = new Button();
    button.setText(BACK_BUTTON_TEXT);
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
