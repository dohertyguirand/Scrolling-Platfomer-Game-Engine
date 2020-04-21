package ooga.view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.data.OogaEntity;
import ooga.data.ImageEntity;

import java.util.ResourceBundle;

public class ViewImageEntity implements ViewEntity {

  private ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
  private final double Y_OFFSET = Double.parseDouble(myResources.getString("pauseButtonSize"));
  private ImageView imageView = new ImageView();

  public ViewImageEntity(ImageEntity entity){
    bindImageProperty(entity.imageLocationProperty());
    bindGenericProperties(entity);
    bindSizeProperties(entity);
  }

  public void bindGenericProperties(OogaEntity entity) {
    imageView.xProperty().bind(entity.xProperty());
    imageView.yProperty().bind(entity.yProperty().add(new SimpleDoubleProperty(Y_OFFSET)));
    // add more properties here if needed
  }

  public Node getNode() {
    return imageView;
  }

  private void bindImageProperty(StringProperty location){
    System.out.println(location.getValue());
    imageView.setImage(new Image(location.getValue()));
    location.addListener((o, oldVal, newVal) -> imageView.setImage(new Image(newVal)));
    //TODO: add error checking to make sure image file path is correct
  }

  private void bindSizeProperties(ImageEntity entity){
    imageView.fitHeightProperty().bind(entity.heightProperty());
    imageView.fitWidthProperty().bind(entity.widthProperty());
  }


}
