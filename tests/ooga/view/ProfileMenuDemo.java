package ooga.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ooga.OogaDataException;

import java.io.IOException;
import java.util.List;

public class ProfileMenuDemo extends Application {

    private static final String GAME_NOT_FOUND_MESSAGE = "Error: Game not found";
    private static final String START_MENU_TITLE = "Choose a Game";
    private Stage stage;
    private String profileNameSelected;

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
        List<ViewProfile> profiles = List.of(new ViewProfile("Tree", "ooga/view/Resources/profilephotos/tree.jpg"));
        profileMenu.setMyProfiles(profiles);
        profileMenu.profileSelected().addListener((p, poldVal, pnewVal) -> {
            ViewProfile profile = (ViewProfile) pnewVal;
            ScrollMenu startMenu = new StartMenu(profile);
            profileNameSelected = profile.getProfileName();
            startMenu.selectedProperty().addListener((o, oldVal, newVal) -> startGame(newVal, profileNameSelected));
            stage.setScene(startMenu.getScene());
        });
        return profileMenu.getScene();
    }

    private void startGame(String gameName, String profileName) {
        if (gameName != null) {
            try {
                new ViewerGame(gameName, profileName);
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
