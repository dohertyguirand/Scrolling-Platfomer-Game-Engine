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

public class StartMenu extends ScrollMenu{


  public StartMenu() {
        super();
        addImages();
  }
    @Override
  protected void addImages() {
    List<Thumbnail> thumbnails = myDataReader.getThumbnails();
    for (Thumbnail thumbnail : thumbnails) {
      ImageView gameImage = new ImageView(thumbnail.getImageFile());
      resizeImage(gameImage, 1);
      Button gameButton = new Button(null, gameImage);
      gameButton.setOnAction(e -> setOptionSelected(thumbnail.getTitle()));
      gameButton.setOnMouseEntered(e -> resizeImage(gameImage, IMAGE_RESIZE_FACTOR));
      gameButton.setOnMouseExited(e -> resizeImage(gameImage, 1));
      gameButton.setTooltip(new Tooltip(thumbnail.getDescription()));
      myHBox.getChildren().add(gameButton);
    }
  }
}
