package ooga.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import ooga.data.OogaDataReader;

public class StartMenu {

  private StringProperty gameSelected = new SimpleStringProperty();
  private Scene myScene;

  public StartMenu(OogaDataReader myDataReader) {
  }

  public String getGameSelected() {
    return gameSelected.get();
  }

  public StringProperty gameSelectedProperty() {
    return gameSelected;
  }

  public Scene getScene() {
    return myScene;
  }
}
