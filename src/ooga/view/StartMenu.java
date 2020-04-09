package ooga.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import ooga.data.OogaDataReader;
import ooga.data.Thumbnail;


import java.util.List;
import java.util.ResourceBundle;

public class StartMenu {
  private OogaDataReader myDataReader;
  private StringProperty gameSelected = new SimpleStringProperty();
  private ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
  private final double WINDOW_HEIGHT = Double.parseDouble(myResources.getString("windowHeight"));
  private final double WINDOW_WIDTH = Double.parseDouble(myResources.getString("windowWidth"));
  private final double GAME_IMAGE_HEIGHT = Double.parseDouble(myResources.getString("gameImageHeight"));
  private final double GAME_IMAGE_WIDTH = Double.parseDouble(myResources.getString("gameImageWidth"));
  private final double SCROLLBAR_Y = Double.parseDouble(myResources.getString("scrollbarY"));
  private final double MAX_SCROLLBAR = Double.parseDouble(myResources.getString("maxScrollbar"));
  private final double HBOX_SPACING = Double.parseDouble(myResources.getString("hboxspacing"));
  private final double HBOX_Y_LAYOUT = Double.parseDouble(myResources.getString("hboxy"));

  private Scene myScene;
  private ScrollBar myScrollbar;
  private HBox myHbox;
  public Scene getScene() {
    return myScene;
  }

  public StartMenu() {
    myDataReader = new OogaDataReader();

    Group root = new Group();
    Pane pane = new Pane();
    pane.setPrefSize(WINDOW_WIDTH,WINDOW_HEIGHT);

    ImageView imgView = new ImageView(myResources.getString("menuBackgroundLocation"));
    imgView.setFitWidth(WINDOW_WIDTH);
    imgView.setFitHeight(WINDOW_HEIGHT);

    horizontalScroller();
    hbarsettings();
    addImages();

    pane.getChildren().addAll(imgView);
    root.getChildren().addAll(pane, myHbox, myScrollbar);


    myScene = new Scene(root);
    myScene.getStylesheets().add("ooga/view/Resources/Scrollbar.css");
  }

  public StringProperty gameSelectedProperty() {
    return gameSelected;
  }

  public void setGameSelected(String gameSelected) {
    this.gameSelected.set(null);
    this.gameSelected.set(gameSelected);
  }

  private void horizontalScroller() {
      myScrollbar = new ScrollBar();
      myScrollbar.setLayoutY(SCROLLBAR_Y);
      myScrollbar.setMin(0);
      myScrollbar.setOrientation(Orientation.HORIZONTAL);
      myScrollbar.setPrefWidth(WINDOW_WIDTH);
      myScrollbar.setMax(MAX_SCROLLBAR);

    myScrollbar.valueProperty().addListener((ov, old_val, new_val) -> myHbox.setLayoutX(-new_val.doubleValue()));
  }

  private void hbarsettings() {
    myHbox = new HBox();
    myHbox.setLayoutY(HBOX_Y_LAYOUT);
    myHbox.setSpacing(HBOX_SPACING);
  }

  private void addImages() {
    List<Thumbnail> thumbnails = myDataReader.getThumbnails();
    for (Thumbnail thumbnail : thumbnails) {
      ImageView gameImage = new ImageView(thumbnail.getImageFile());
      resizeImage(gameImage, 1);
      Button gameButton = new Button(thumbnail.getDescription(), gameImage);
      gameButton.setOnAction(e -> setGameSelected(thumbnail.getTitle()));
      gameButton.setOnMouseEntered(e -> resizeImage(gameImage, 1.25));
      gameButton.setOnMouseExited(e -> resizeImage(gameImage, 1));
      myHbox.getChildren().add(gameButton);
    }
  }

  private void resizeImage(ImageView gameImage, double resizeFactor) {
    gameImage.setFitWidth(GAME_IMAGE_WIDTH*resizeFactor);
    gameImage.setFitHeight(GAME_IMAGE_HEIGHT*resizeFactor);
  }

}
