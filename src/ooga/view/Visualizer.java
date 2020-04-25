package ooga.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ooga.OogaDataException;
import ooga.data.gamedatareaders.GameDataReaderExternal;
import ooga.data.gamedatareaders.XMLGameDataReader;
import ooga.view.menus.GameMenu;
import ooga.view.menus.LoadMenu;
import ooga.view.menus.ProfileMenu;

public class Visualizer extends Application {

  private static final String ERROR_MESSAGE = "Error: Internal Data Error";
  private static final String START_MENU_TITLE = "Choose a Game";
  private static final String BACK_BUTTON_TEXT = "Back";
  private String profileNameSelected;
  private Stage stage;
  private final GameDataReaderExternal myDataReader = new XMLGameDataReader() {};
  private String dateSelected;

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
    ProfileMenu profileMenu = new ProfileMenu();
    Scene profileMenuScene = new Scene(profileMenu,profileMenu.getWidth(),profileMenu.getHeight());
    profileMenu.profileSelected().addListener((p, poldVal, pnewVal) ->{
      profileNameSelected = pnewVal.getProfileName();
      showStartMenu(pnewVal, profileMenuScene);
    });
    stage.setScene(profileMenuScene);
  }

  private void showStartMenu(ViewProfile profile, Scene returnScene){
    Button backToProfileMenu = makeBackButton(returnScene);
    GameMenu gameMenu = new GameMenu(profile,backToProfileMenu);
    Scene gameMenuScene = new Scene(gameMenu, gameMenu.getWidth(), gameMenu.getHeight());
    gameMenu.selectedProperty().addListener((o, oldVal, newVal) -> showLoadMenu(newVal, gameMenuScene));
    stage.setScene(gameMenuScene);
  }

  private void showLoadMenu(String gameName, Scene returnScene){
    Button backToStartMenu = makeBackButton(returnScene);
    LoadMenu loadMenu = new LoadMenu(gameName, profileNameSelected,myDataReader, backToStartMenu);
    Scene loadScene = new Scene(loadMenu, loadMenu.getWidth(),loadMenu.getHeight());
    loadMenu.getDateSelected().addListener((d,dold,dnew)-> startGame(gameName,profileNameSelected,dnew));
    stage.setScene(loadScene);
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
