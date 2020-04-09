package ooga.view;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.text.Text;
import ooga.data.OogaEntity;
import ooga.data.TextEntity;

public class ViewTextEntity extends ViewEntity {

  private Text text = new Text();

  public ViewTextEntity(TextEntity entity) {
    bindGenericProperties(entity);
    bindTextProperty(entity.textProperty());
  }

  public void bindGenericProperties(OogaEntity entity) {
    text.xProperty().bind(entity.xProperty());
    text.yProperty().bind(entity.yProperty());
    // add more properties here if needed
  }

  public Node getNode() {
    return text;
  }

  private void bindTextProperty(StringProperty string){
    text.textProperty().bind(string);
  }


}
