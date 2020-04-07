package ooga.data;

import javafx.beans.property.*;

public abstract class Entity {

  private BooleanProperty activeInView = new SimpleBooleanProperty(true);
  private DoubleProperty x = new SimpleDoubleProperty();
  private DoubleProperty y = new SimpleDoubleProperty();

  public double getX() {
    return x.get();
  }

  public DoubleProperty xProperty() {
    return x;
  }

  public double getY() {
    return y.get();
  }

  public DoubleProperty yProperty() {
    return y;
  }

  public boolean isActiveInView() {
    return activeInView.get();
  }

  public BooleanProperty activeInViewProperty() {
    return activeInView;
  }

  public void setActiveInView(boolean activeInView) {
    this.activeInView.set(activeInView);
  }
}
