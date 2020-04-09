package ooga.view;

import javafx.scene.Node;
import ooga.data.OogaEntity;

public abstract class ViewEntity {

  public abstract void bindGenericProperties(OogaEntity entity);

  public abstract Node getNode();
}
