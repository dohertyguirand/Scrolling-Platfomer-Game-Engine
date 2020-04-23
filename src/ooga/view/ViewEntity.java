package ooga.view;

import javafx.scene.Node;
import ooga.Entity;
import ooga.data.OogaEntity;

public interface ViewEntity {

  void bindGenericProperties(Entity entity);

  Node getNode();
}
