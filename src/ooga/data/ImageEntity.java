package ooga.data;

import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ImageEntity extends OogaEntity {

  private StringProperty imageLocation = null;

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

  @Override
  public void reactToVariables(Map<String, Double> variables) {
    //does nothing for now?
  }
}
