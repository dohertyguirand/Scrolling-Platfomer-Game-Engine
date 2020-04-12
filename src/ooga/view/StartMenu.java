package ooga.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ooga.data.Thumbnail;


import java.util.List;

public class StartMenu extends ScrollMenu{

  public StartMenu() {
        super();
        addImages(myDataReader.getThumbnails());
  }

  public StartMenu(ViewProfile profile){
    super();
    addImages(myDataReader.getThumbnails());
    myPane.getChildren().add(setProfileData(profile));
  }


  private Node setProfileData(ViewProfile profile){
    VBox vBox = new VBox();
    Text text = new Text(profile.getProfileName());
    vBox.getChildren().add(profile.getProfilePhoto());
    vBox.getChildren().add(text);
    return vBox;
  }
}
