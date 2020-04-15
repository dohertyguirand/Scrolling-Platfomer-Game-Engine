package ooga.data;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TextEntity extends OogaEntity {

  private StringProperty text = new SimpleStringProperty();
  private StringProperty fontName = new SimpleStringProperty();
  private String baseText; // without any variables inserted

  public TextEntity(String contents, String fontName, double xPos, double yPos, double width, double height){
    super(xPos, yPos, width, height);
    text.set(contents);
    this.fontName.set(fontName);
    baseText = contents;
    propertyUpdaters.put("text", this::updateTextProperty);
  }

  private void updateTextProperty(Double variableValue) {
    text.set(String.format(baseText, variableValue));
  }

  public String getText() {
    return text.get();
  }

  public StringProperty textProperty() {
    return text;
  }

  public String getFontName() { return fontName.get(); }

  public StringProperty fontNameProperty() { return fontName; }

  public void updateTextProperty(double value) {
  }
}
