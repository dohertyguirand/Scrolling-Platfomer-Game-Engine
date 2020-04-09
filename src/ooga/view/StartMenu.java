package ooga.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
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
  private final double HBOX_SPACING = Double.parseDouble(myResources.getString("hboxspacing"));
  private final double HBOX_Y_LAYOUT = Double.parseDouble(myResources.getString("hboxy"));
  private final double GAME_IMAGE_RESIZE_FACTOR = Double.parseDouble(myResources.getString("gameImageResizeFactor"));

  private Scene myScene;
  private ScrollPane myScrollPane;
  private HBox myHBox;

  public StartMenu() {
    myDataReader = new OogaDataReader();

    Group root = new Group();
    Pane pane = new Pane();
    pane.setPrefSize(WINDOW_WIDTH,WINDOW_HEIGHT);

    ImageView imgView = new ImageView(myResources.getString("menuBackgroundLocation"));
    imgView.setFitWidth(WINDOW_WIDTH);
    imgView.setFitHeight(WINDOW_HEIGHT);

    horizontalScroller();
    addImages();

    pane.getChildren().addAll(imgView);
    root.getChildren().addAll(pane, myScrollPane);

    myScene = new Scene(root);
    String SCROLLBAR_CSS_LOCATION = myResources.getString("scrollBarCSSLocation");
    myScene.getStylesheets().add(SCROLLBAR_CSS_LOCATION);
  }

  public Scene getScene() {
    return myScene;
  }

  public StringProperty gameSelectedProperty() {
    return gameSelected;
  }

  public void setGameSelected(String gameSelected) {
    this.gameSelected.set(null);
    this.gameSelected.set(gameSelected);
  }

  private void horizontalScroller() {
      myHBox = new HBox();
      myHBox.setLayoutY(HBOX_Y_LAYOUT);
      myHBox.setSpacing(HBOX_SPACING);
      myScrollPane = new ScrollPane();
      myScrollPane.setLayoutY(SCROLLBAR_Y);
      myScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
      myScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
      myScrollPane.setPrefWidth(WINDOW_WIDTH);
      myScrollPane.setContent(myHBox);
  }

  private void addImages() {
    List<Thumbnail> thumbnails = myDataReader.getThumbnails();
    for (Thumbnail thumbnail : thumbnails) {
      ImageView gameImage = new ImageView(thumbnail.getImageFile());
      resizeImage(gameImage, 1);
      Button gameButton = new Button(null, gameImage);
      gameButton.setOnAction(e -> setGameSelected(thumbnail.getTitle()));
      gameButton.setOnMouseEntered(e -> resizeImage(gameImage, GAME_IMAGE_RESIZE_FACTOR));
      gameButton.setOnMouseExited(e -> resizeImage(gameImage, 1));
      gameButton.setTooltip(new Tooltip(thumbnail.getDescription()));
      myHBox.getChildren().add(gameButton);
    }
  }

  private void resizeImage(ImageView gameImage, double resizeFactor) {
    gameImage.setFitWidth(GAME_IMAGE_WIDTH*resizeFactor);
    gameImage.setFitHeight(GAME_IMAGE_HEIGHT*resizeFactor);
  }
}
