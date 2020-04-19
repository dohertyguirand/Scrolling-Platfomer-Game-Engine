package ooga.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.data.Thumbnail;


import java.util.List;

public class StartMenu extends ScrollMenu{

  private StringProperty optionSelected = new SimpleStringProperty();

  public StartMenu() {
        super();
        addImages(myDataReader.getThumbnails());
  }

  public StartMenu(ViewProfile profile){
    super();
    addImages(myDataReader.getThumbnails());
    myPane.getChildren().add(setProfileData(profile));
  }

  private void addImages(List<Thumbnail> thumbnails){
    for (Thumbnail thumbnail : thumbnails) {
      ImageView optionImage = new ImageView(thumbnail.getImageFile());
      resizeImage(optionImage, 1);
      Button gameButton = makeButton(optionImage,thumbnail.getDescription());
      gameButton.setOnAction(e -> setOptionSelected(thumbnail.getTitle()));
      myHBox.getChildren().add(gameButton);
    }
  }

  private void addImages() {
    List<Thumbnail> thumbnails = myDataReader.getThumbnails();
    addImages(thumbnails);
  }


  private Node setProfileData(ViewProfile profile){
    VBox vBox = new VBox();
    vBox.setOnMouseClicked(e->{
      showProfile(profile);
    });
    Text text = new Text(profile.getProfileName());
    vBox.getChildren().add(profile.getProfilePhoto());
    vBox.getChildren().add(text);
    return vBox;
  }
  public StringProperty selectedProperty() {
    return optionSelected;
  }

  private void showProfile(ViewProfile profile){
    Stage stage  = new Stage();
    Scene scene = new Scene(profile.getPane());
    stage.setTitle(profile.getProfileName() + "'s Profile");
    stage.setScene(scene);
    stage.show();
  }

  public void setOptionSelected(String optionSelected) {
    this.optionSelected.set(null);
    this.optionSelected.set(optionSelected);
  }
}
