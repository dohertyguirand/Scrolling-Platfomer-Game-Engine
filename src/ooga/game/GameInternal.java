package ooga.game;

import java.util.List;

import java.util.Map;
import ooga.Entity;

public interface GameInternal {

  void createEntity(String type, List<Double> position);

  void createEntity(String type, List<Double> position, double width, double height);

  Entity getEntityWithId(String id);

  List<Entity> getEntitiesWithName(String name);

  void goToLevel(String levelID);

  void goToNextLevel();

  void restartLevel();

  void setCameraShiftValues(double xShift, double yShift);

  List<Double> getCameraShiftValues();

  /**
   * @return A copy of the map of game variables for viewing.
   */
  Map<String,String> getVariables();
}
