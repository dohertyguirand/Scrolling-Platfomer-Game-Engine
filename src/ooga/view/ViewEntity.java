package ooga.view;

import javafx.scene.Node;
import ooga.data.Entity;

public abstract class ViewEntity {

  public abstract void bindGenericProperties(Entity entity);

  public abstract Node getNode();
}
