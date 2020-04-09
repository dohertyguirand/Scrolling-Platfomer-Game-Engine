package ooga.view;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.data.OogaEntity;
import ooga.data.ImageEntity;

public class ViewImageEntity extends ViewEntity {

  private ImageView imageView = new ImageView();

  public ViewImageEntity(ImageEntity entity){
    bindImageProperty(entity.imageLocationProperty());
    bindGenericProperties(entity);
    bindSizeProperties(entity);
  }

  public void bindGenericProperties(OogaEntity entity) {
    imageView.xProperty().bind(entity.xProperty());
    imageView.yProperty().bind(entity.yProperty());
    // add more properties here if needed
  }

  public Node getNode() {
    return imageView;
  }

  private void bindImageProperty(StringProperty location){
    imageView.setImage(new Image(location.getValue()));
    location.addListener((o, oldVal, newVal) -> imageView.setImage(new Image(newVal)));
    // might need to rebind other ImageView properties here
  }

  private void bindSizeProperties(ImageEntity entity){
    imageView.fitHeightProperty().bind(entity.heightProperty());
    imageView.fitWidthProperty().bind(entity.widthProperty());
  }


}
