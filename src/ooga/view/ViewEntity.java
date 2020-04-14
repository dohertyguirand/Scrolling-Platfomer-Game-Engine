package ooga.view;

import javafx.scene.Node;
import ooga.data.OogaEntity;

public interface ViewEntity {

  void bindGenericProperties(OogaEntity entity);

  Node getNode();
}
