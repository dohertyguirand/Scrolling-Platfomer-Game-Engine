package ooga.game;

import java.util.List;

import java.util.Map;

/**
 * @author sam thompson, doherty guirand, caryshindell
 */
public interface GameInternal {

  void createEntity(String type, List<Double> position);

  void createEntity(String type, List<Double> position, double width, double height);

  EntityInternal getEntityWithId(String id);

  List<EntityInternal> getEntitiesWithName(String name);

  void goToLevel(String levelID);

  void goToNextLevel();

  void restartLevel();

  void setCameraShiftValues(double xShift, double yShift);

  List<Double> getCameraShiftValues();

  /**
   * @return A copy of the map of game variables for viewing.
   */
  Map<String,String> getVariables();

  /**
   * Sets the provided variable to the desired value. Creates the variable if it doesn't exist.
   * @param var The name of the variable to set.
   * @param value The String to set the variable equal to.
   */
  void setVariable(String var, String value);

  /**
   * @return A list of the entities that are currently in play.
   */
  List<EntityInternal> getInternalEntities();
}
