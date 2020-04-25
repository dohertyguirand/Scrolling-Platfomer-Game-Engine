package ooga.data.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ooga.Entity;
import ooga.data.entities.OogaEntity;

public class TextEntity extends OogaEntity {

  private static final String type = Entity.textEntityType;
  private final StringProperty text = new SimpleStringProperty();
  private final StringProperty fontName = new SimpleStringProperty();
  private final String baseText; // without any variables inserted

  public TextEntity(String contents, String fontName, double xPos, double yPos, double width, double height){
    super(xPos, yPos, width, height);
    text.set(contents);
    this.fontName.set(fontName);
    baseText = contents;
    propertyUpdaters.put("Text", this::updateTextProperty);
    propertyUpdaters.put("Font", this::updateFontProperty);
  }

  private void updateTextProperty(String variableValue) {
    text.set(String.format(baseText, Double.parseDouble(variableValue)));
  }

  private void updateFontProperty(String variableValue){
    this.fontName.set(variableValue);
  }

  public StringProperty textProperty() {
    return text;
  }

  public String getFontName() { return fontName.get(); }

  public StringProperty fontNameProperty() { return fontName; }

  @SuppressWarnings({"EmptyMethod", "unused"})
  public void updateTextProperty(double value) {
  }

  /**
   * is it an image entity or text entity
   *
   * @return string "image" or "text" or something else if new entity classes are created
   */
  @Override
  public String getEntityType() {
    return type;
  }

  @Override
  public void setImageLocation(String filepath) {
    //Does nothing, since it doesn't have an image.
  }
}
