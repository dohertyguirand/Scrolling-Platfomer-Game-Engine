package ooga.data;

import javafx.beans.property.StringProperty;

public class TextEntity extends OogaEntity {

  private StringProperty text;

  public String getText() {
    return text.get();
  }

  public StringProperty textProperty() {
    return text;
  }
}
