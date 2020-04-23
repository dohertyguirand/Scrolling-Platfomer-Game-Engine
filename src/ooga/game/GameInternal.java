package ooga.game;

import java.util.List;

import javafx.beans.property.DoubleProperty;
import ooga.Entity;

public interface GameInternal {

  void createEntity(String type, List<Double> position);

  Entity getEntityWithId(String id);

  List<Entity> getEntitiesWithName(String name);

  void goToLevel(String levelID);

  void goToNextLevel();

  void restartLevel();

  public void setCameraShiftProperty(List<DoubleProperty> property);

  public void setCameraShiftValue(double xShift, double yShift);

  public List<DoubleProperty> getCameraShiftProperties();

  public List<Double> getCameraShiftValues();
}
