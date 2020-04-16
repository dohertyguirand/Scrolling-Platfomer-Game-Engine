package ooga.data;

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
    propertyUpdaters.put("imageLocation", null); //TODO: figure out how image location should react to variable change
  }

  public ImageEntity(String name) {
    //TODO: fix tests and remove this
    this(name, null, 0, 0, 100, 100);
  }

  public ImageEntity() {
    //TODO: fix tests and remove this
    this("Unnamed", null, 0, 0, 10.0, 10.0);
  }

  public String getImageLocation() {
    return imageLocation.get();
  }

  public StringProperty imageLocationProperty() {
    return imageLocation;
  }

  @Override
  public void reactToVariables(Map<String, Double> variables) {
    //does nothing for now?
  }
}
