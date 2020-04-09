package ooga.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ImageEntity extends OogaEntity {

  private StringProperty imageLocation = null;
  private DoubleProperty height = new SimpleDoubleProperty();
  private DoubleProperty width = new SimpleDoubleProperty();

  public ImageEntity(String name, String imageFilePath) {
    super(name);
    width.setValue(100.0);
    height.setValue(100.0);
    if (imageFilePath != null) {
      imageLocation = new SimpleStringProperty(imageFilePath);
    }
  }

  public ImageEntity(String name) {
    this(name,null);
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

  public DoubleProperty widthProperty() {
    return width;
  }
}
