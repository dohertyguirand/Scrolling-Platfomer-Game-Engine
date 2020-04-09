package ooga.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.data.OogaDataReader;

import java.io.IOException;
import java.util.ResourceBundle;

public class Visualizer extends Application {

  private ResourceBundle myResources;
  private OogaDataReader myDataReader;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    myDataReader = new OogaDataReader();
    Scene display = setUpStartMenuDisplay();
    primaryStage.setScene(display);
    primaryStage.setTitle(myResources.getString("StartMenuTitle"));
    primaryStage.show();
  }

  private Scene setUpStartMenuDisplay() throws IOException {
    StartMenu startMenu = new StartMenu();
    startMenu.gameSelectedProperty().addListener((o, oldVal, newVal) -> startGame(newVal));
    return startMenu.getScene();
  }

  private void startGame(String gameName) {
    new ViewerGame(gameName, myDataReader);
  }
}
