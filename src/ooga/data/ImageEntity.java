package ooga.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ImageEntity extends OogaEntity {

  private StringProperty imageLocation = null;

  public ImageEntity(String name, String imageFilePath) {
    super(name);
    myWidth.setValue(100.0);
    myHeight.setValue(100.0);
    if (imageFilePath != null) {
      imageLocation = new SimpleStringProperty(imageFilePath);
    }
  }

  public ImageEntity(String name) {
    this(name,null);
  }

  public ImageEntity() {
    super("Unnamed");
    myWidth.setValue(10.0);
    myHeight.setValue(10.0);
  }

  public String getImageLocation() {
    return imageLocation.get();
  }

  public StringProperty imageLocationProperty() {
    return imageLocation;
  }
}
