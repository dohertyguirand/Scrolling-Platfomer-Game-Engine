package ooga.view;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ooga.data.DataReader;
import ooga.data.OogaDataReader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StartMenu extends Application {

  private StringProperty gameSelected = new SimpleStringProperty();
  private Scene myScene;
  private ScrollBar myScrollbar;
  private HBox myHbox;
  final Image[] myImages = new Image[4];
  final ImageView[] myPics = new ImageView[4];

  public StartMenu(OogaDataReader myDataReader) {
  }

  public String getGameSelected() {
    return gameSelected.get();
  }

  public StringProperty gameSelectedProperty() {
    return gameSelected;
  }

  public Scene getScene() {
    return myScene;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    Group root = new Group();
    Pane pane = new Pane();
    pane.setPrefSize(800,800);

    InputStream is = Files.newInputStream(Paths.get("resources/data/menu_images/menubackground.jpg"));
    Image img = new Image(is);
    is.close();
    ImageView imgView = new ImageView(img);
    imgView.setFitWidth(800);
    imgView.setFitHeight(800);

    horizontalScroller();
    hbarsettings();
    addimages();


    pane.getChildren().addAll(imgView);
    root.getChildren().addAll(pane, myHbox, myScrollbar);


    myScene = new Scene(root);
    primaryStage.setScene(myScene);
    primaryStage.show();

  }
  private void horizontalScroller() {
      myScrollbar = new ScrollBar();
      myScrollbar.setLayoutY(450);
      myScrollbar.setMin(0);
      myScrollbar.setOrientation(Orientation.HORIZONTAL);
      myScrollbar.setPrefWidth(800);
      myScrollbar.setMax(360);

    myScrollbar.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> ov,
                          Number old_val, Number new_val) {
        myHbox.setLayoutX(-new_val.doubleValue());
      }
    });
  }

  private void hbarsettings() {
    myHbox = new HBox();
    myHbox.setLayoutY(350);
    myHbox.setSpacing(50);
  }

  private void addimages() throws IOException {
    for (int i = 1; i < 4; i++) {
      InputStream is = Files.newInputStream(Paths.get("resources/data/menu_images/image" +(i)+ ".png"));
      final Image image = myImages[i] =
              new Image(is);
      final ImageView pic = myPics[i] = new ImageView((myImages[i]));
      myHbox.getChildren().add(myPics[i]);
    }
  }


  public static void main(String[] args) {
    launch(args);
  }
}
