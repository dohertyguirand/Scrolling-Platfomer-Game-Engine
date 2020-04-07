package ooga.view;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ooga.data.DataReader;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StartMenu extends Application {

  private StringProperty gameSelected = new SimpleStringProperty();
  private Scene myScene;

//  public StartMenu(DataReader myDataReader) {
//  }
//
//  public String getGameSelected() {
//    return gameSelected.get();
//  }
//
//  public StringProperty gameSelectedProperty() {
//    return gameSelected;
//  }
//
//  public Scene getScene() {
//    return myScene;
//  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    Pane root = new Pane();
    root.setPrefSize(800,800);

    InputStream is = Files.newInputStream(Paths.get("resources/data/menu_images/menubackground.jpg"));

    Image img = new Image(is);
    is.close();

    ImageView imgView = new ImageView(img);
    root.getChildren().addAll(imgView);
    imgView.setFitWidth(800);
    imgView.setFitHeight(800);

    myScene = new Scene(root);
    primaryStage.setScene(myScene);
    primaryStage.show();

  }
  public static void main(String[] args) {
    launch(args);
  }
}
