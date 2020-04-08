package ooga.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import ooga.data.OogaDataReader;
import ooga.data.Thumbnail;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;


import java.util.List;
import java.util.ResourceBundle;

public class StartMenu {
  private OogaDataReader myDataReader;
  private StringProperty gameSelected = new SimpleStringProperty();
  private ResourceBundle myResources = ResourceBundle.getBundle("./Resources.config");
  private final double WINDOW_HEIGHT = Double.parseDouble(myResources.getString("windowHeight"));
  private final double WINDOW_WIDTH = Double.parseDouble(myResources.getString("windowWidth"));
  private final double GAME_IMAGE_HEIGHT = Double.parseDouble(myResources.getString("gameImageHeight"));
  private final double GAME_IMAGE_WIDTH = Double.parseDouble(myResources.getString("gameImageWidth"));
  private Scene myScene;
  private ScrollBar myScrollbar;
  private HBox myHbox;
  final Image[] myImages = new Image[4];
  final ImageView[] myPics = new ImageView[4];
  private ImageView imagev1;
  private ImageView imagev2;
  private ImageView imagev3;


  public Scene getScene() {
    return myScene;
  }

  public StartMenu() throws IOException {
    myScene.getStylesheets().add("resources/data/Scrollbar.css");

    myDataReader = new OogaDataReader();

    Group root = new Group();
    Pane pane = new Pane();
    pane.setPrefSize(WINDOW_WIDTH,WINDOW_HEIGHT);

    ImageView imgView = new ImageView(myResources.getString("menuBackgroundLocation"));
    imgView.setFitWidth(WINDOW_WIDTH);
    imgView.setFitHeight(WINDOW_HEIGHT);

    horizontalScroller();
    hbarsettings();
    addimages();
    initialize();


    pane.getChildren().addAll(imgView);
    root.getChildren().addAll(pane, myHbox, myScrollbar);


    myScene = new Scene(root);
  }

  public String getGameSelected() {
    return gameSelected.get();
  }

  public StringProperty gameSelectedProperty() {
    return gameSelected;
  }

  public void setGameSelected(String gameSelected) {
    this.gameSelected.set(gameSelected);
  }

  private void horizontalScroller() {
      myScrollbar = new ScrollBar();
      myScrollbar.setLayoutY(450);
      myScrollbar.setMin(0);
      myScrollbar.setOrientation(Orientation.HORIZONTAL);
      myScrollbar.setPrefWidth(WINDOW_WIDTH);
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

  private void addimages() {
    List<Thumbnail> thumbnails = myDataReader.getThumbnails();
    for (Thumbnail thumbnail : thumbnails) {
      Button gameButton = new Button(thumbnail.getDescription(), new ImageView(thumbnail.getImageFile()));
      gameButton.setOnAction(e -> setGameSelected(thumbnail.getTitle()));
      gameButton.setOnMouseEntered(e -> gameButton.resize(GAME_IMAGE_WIDTH*1.25, GAME_IMAGE_HEIGHT*1.25));
      gameButton.setOnMouseExited(e -> gameButton.resize(GAME_IMAGE_WIDTH, GAME_IMAGE_HEIGHT));
      myHbox.getChildren().add(gameButton);
    }
  }

  public void initialize() {
    imagev1 = new ImageView();
    imagev2 = new ImageView();
    imagev3 = new ImageView();
    Image i = new Image(new File("resources/data/menu_images/pick.gif").toURI().toString());
    imagev1.setImage(i);
    Image j = new Image(new File("resources/data/menu_images/your.gif").toURI().toString());
    imagev2.setImage(j);
    Image k = new Image(new File("resources/data/menu_images/your.gif").toURI().toString());
    imagev3.setImage(k);

    imagev1.setX(300); imagev1.setY(200);
    imagev2.setX(400); imagev2.setY(200);
    imagev3.setX(500); imagev3.setY(200);
  }
}
