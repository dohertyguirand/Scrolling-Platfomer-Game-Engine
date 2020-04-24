package ooga.data.entities;

import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ImageEntity extends OogaEntity {

  private StringProperty imageLocation = null;

  public ImageEntity(String name, String imageFilePath, double xPos, double yPos, double width, double height) {
    super(xPos, yPos, width, height);
    if (imageFilePath != null) {
      imageLocation = new SimpleStringProperty(imageFilePath);
    }
    myName = name;
    propertyUpdaters.put("imageLocation", this::setImageLocation);
  }

  @Deprecated
  public ImageEntity(String name) {
    //TODO: fix tests and remove this
    this(name, null, 0, 0, 100, 100);
  }

  @Deprecated
  public ImageEntity() {
    //TODO: fix tests and remove this
    this("Unnamed", null, 0, 0, 10.0, 10.0);
  }

  public void setImageLocation(String filePath){imageLocation.set(filePath);}

  public StringProperty imageLocationProperty() {
    return imageLocation;
  }

  @Override
  public void reactToVariables(Map<String, String> variables) {
    //does nothing for now?
  }
}
