package ooga.view;

import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.Entity;
import ooga.data.OogaEntity;
import ooga.data.ImageEntity;

import java.util.ResourceBundle;

public class ViewImageEntity implements ViewEntity {

  private ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
  private final double Y_OFFSET = Double.parseDouble(myResources.getString("pauseButtonSize"));
  private ImageView imageView = new ImageView();

  public ViewImageEntity(ImageEntity entity, ObjectProperty<Effect> colorEffect){
    bindImageProperty(entity.imageLocationProperty(), colorEffect);
    bindGenericProperties(entity);
    bindSizeProperties(entity);
  }

  public void bindGenericProperties(Entity entity) {
    imageView.xProperty().bind(entity.xProperty());
    imageView.yProperty().bind(entity.yProperty().add(new SimpleDoubleProperty(Y_OFFSET)));
    // add more properties here if needed
  }

  public Node getNode() {
    return imageView;
  }
  public DoubleProperty getXProperty(){return imageView.xProperty();}
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
