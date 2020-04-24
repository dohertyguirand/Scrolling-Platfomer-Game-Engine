package ooga.game;

import java.util.Collection;
import javafx.collections.ObservableList;
import ooga.Entity;

/**
 * Represents one in-game level, including its end conditions and list of entities.
 */
public interface Level {

  /**
   * @return A List of all Entities in the level.
   */
  ObservableList<Entity> getEntities();

  /**
   * Removes an entity from the level, if it is in the level.
   */
  void removeEntity(Entity e);

  /**
   * Adds an entity to the level, if it is not in the level.
   * @param e The entity to add.
   */
  void addEntity(Entity e);

  /**
   * @return The ID in the game file of the level that comes after this one.
   */
  String nextLevelID();

  /**
   * @return The String ID of the level that this level will go to when it ends.
   * @param nextID
   */
  void setNextLevelID(String nextID);

  /**
   * @return The ID of this level as assigned in the file.
   */
  String getLevelId();
}
