package ooga.view;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import ooga.Entity;

import java.util.List;

public interface ViewEntity {

  /**
   * Binds the x and y position properties to be incremented by the camera shift
   * @param entity the entity
   */
  void bindGenericProperties(Entity entity, List<DoubleProperty> cameraShift);

  Node getNode();
}
