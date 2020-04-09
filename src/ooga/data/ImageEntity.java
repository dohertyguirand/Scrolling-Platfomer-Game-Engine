package ooga.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;

public class ImageEntity extends Entity {

  private StringProperty imageLocation = null;
  private DoubleProperty height = new SimpleDoubleProperty();
  private DoubleProperty width = new SimpleDoubleProperty();

  public String getImageLocation() {
    return imageLocation.get();
  }

  public StringProperty imageLocationProperty() {
    return imageLocation;
  }

  public double getHeight() {
    return height.get();
  }

  public DoubleProperty heightProperty() {
    return height;
  }

  public double getWidth() {
    return width.get();
  }

  public DoubleProperty widthProperty() {
    return width;
  }
}
