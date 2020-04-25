package ooga.view.menus;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.OogaDataException;
import ooga.data.Thumbnail;
import ooga.view.ViewProfile;


import java.util.List;
import java.util.ResourceBundle;

public class GameMenu extends ScrollMenu{

  public static final String PROFILE_VIEW_TITLE = "ProfileViewTitle";
  private final StringProperty optionSelected = new SimpleStringProperty();
  private final String ERROR_MESSAGE = languageResources.getString("ThumbnailError");

  @Deprecated
  public GameMenu(ResourceBundle languageResources) {
    super(languageResources);
    try {
      List<Thumbnail> thumbnails = myGameDataReader.getThumbnails();
      addImages(thumbnails);
    } catch (OogaDataException e){
      showError(e.getMessage());
    }
  }

  /**
   * A screen that allows the user to select a game to be played
   * @param profile - ViewProfile of profile selected by user
   * @param backButton - button that allows user to go back to previous screen
   */
  public GameMenu(ResourceBundle languageResources, ViewProfile profile, Node backButton){
    super(languageResources);
    try {
      List<Thumbnail> thumbnails = myGameDataReader.getThumbnails();
      addImages(thumbnails);
    } catch (OogaDataException e){
      showError(e.getMessage());
    }
    this.getChildren().add(setProfileData(profile));
    this.getChildren().add(backButton);
  }

  /**
   * User is able to choose a game, Visualizer listens to this property to know which game as been selected
   * @return String of name of game selected
   */
  public StringProperty selectedProperty() {
    return optionSelected;
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


  private Node setProfileData(ViewProfile profile){
    VBox vBox = new VBox();
    vBox.setOnMouseClicked(e-> showProfile(profile));
    String name = profile.getProfileName();
    Text text = new Text(name);
    ImageView imageView = new ImageView(profile.getProfilePath());
    vBox.getChildren().add(imageView);
    vBox.getChildren().add(text);
    return vBox;
  }


  private void showProfile(ViewProfile profile){
    Stage stage  = new Stage();
    Scene scene = new Scene(profile.getPane());
    stage.setTitle(languageResources.getString(PROFILE_VIEW_TITLE));
    stage.setScene(scene);
    stage.show();
  }

  private void setOptionSelected(String optionSelected) {
    this.optionSelected.set(null);
    this.optionSelected.set(optionSelected);
  }

  private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(ERROR_MESSAGE);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
