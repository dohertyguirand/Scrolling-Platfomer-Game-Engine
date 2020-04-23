package ooga.view;

import javafx.scene.Node;
import ooga.Entity;

public interface ViewEntity {

  void bindGenericProperties(Entity entity);

  Node getNode();
}
