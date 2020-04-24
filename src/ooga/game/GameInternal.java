package ooga.game;

import java.util.List;

import javafx.beans.property.DoubleProperty;
import ooga.Entity;

public interface GameInternal {

  void createEntity(String type, List<Double> position);

  void createEntity(String type, List<Double> position, double width, double height);

  Entity getEntityWithId(String id);

  List<Entity> getEntitiesWithName(String name);

  void goToLevel(String levelID);

  void goToNextLevel();

  void restartLevel();

  void setCameraShiftProperties(List<DoubleProperty> property);

  void setCameraShiftValue(double xShift, double yShift);

  List<Double> getCameraShiftValues();
}
