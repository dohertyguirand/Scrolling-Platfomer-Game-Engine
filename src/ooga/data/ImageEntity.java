package ooga.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;

public class ImageEntity extends OogaEntity {

  private StringProperty imageLocation = null;
  private DoubleProperty height = new SimpleDoubleProperty();
  private DoubleProperty width = new SimpleDoubleProperty();

  public ImageEntity(String name) {
    super(name);
    width.setValue(10.0);
    height.setValue(10.0);
  }

  public ImageEntity() {
    super("Unnamed");
    width.setValue(10.0);
    height.setValue(10.0);
  }

  public String getImageLocation() {
    return imageLocation.get();
  }

  public StringProperty imageLocationProperty() {
    return imageLocation;
  }

  public DoubleProperty heightProperty() {
    return height;
  }

  @Override
  public double getWidth() {
    return width.get();
  }


  @Override
  public double getHeight() {
    return height.get();
  }

  public DoubleProperty widthProperty() {
    return width;
  }
}
