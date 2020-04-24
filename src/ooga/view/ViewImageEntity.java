package ooga.view;

import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.Entity;
import ooga.data.ImageEntity;

import java.util.List;
import java.util.ResourceBundle;

public class ViewImageEntity implements ViewEntity {

  private final ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
  private final double Y_OFFSET = Double.parseDouble(myResources.getString("pauseButtonSize"));
  private final ImageView imageView = new ImageView();

  public ViewImageEntity(ImageEntity entity, ObjectProperty<Effect> colorEffect, List<DoubleProperty> cameraShift){
    bindImageProperty(entity.imageLocationProperty(), colorEffect);
    bindGenericProperties(entity, cameraShift);
    bindSizeProperties(entity);
  }

  /**
   * Binds the x and y position properties to be incremented by the camera shift
   * Multiplies the camera shift by the entity's stationary property, so entities can be marked to move/not move with camera
   * @param entity
   */
  public void bindGenericProperties(Entity entity, List<DoubleProperty> cameraShift) {
    imageView.xProperty().bind(entity.xProperty().add(entity.stationaryProperty().multiply(cameraShift.get(0))));
    imageView.yProperty().bind(entity.yProperty().add(new SimpleDoubleProperty(Y_OFFSET).add(entity.stationaryProperty().multiply(cameraShift.get(1)))));
    // add more properties here if needed
  }

  public Node getNode() {
    return imageView;
  }

  public DoubleProperty getXProperty(){return imageView.xProperty();}
  @SuppressWarnings("unused")
  public DoubleProperty getYProperty(){return imageView.yProperty();}

  private void bindImageProperty(StringProperty location, ObjectProperty<Effect> colorEffect){
    imageView.setImage(new Image(location.getValue()));
    location.addListener((o, oldVal, newVal) -> {
      imageView.setImage(new Image(newVal));
      imageView.setEffect(colorEffect.getValue());
    });
    colorEffect.addListener((o, oldVal, newVal) -> imageView.setEffect(newVal));
  }

  private void bindSizeProperties(ImageEntity entity){
    imageView.fitHeightProperty().bind(entity.heightProperty());
    imageView.fitWidthProperty().bind(entity.widthProperty());
  }
}
