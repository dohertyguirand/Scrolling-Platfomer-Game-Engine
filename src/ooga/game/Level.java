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
   * Similar to addEntity, but adds multiple easily.
   * @param e The entities to add.
   */
  void addEntities(Collection<Entity> e);

  /**
   * Usually relies on an owned instance of LevelEndCondition to handle the unique logic of
   * a specific level's end condition.
   * @return True if the level should end, which should cause the game to progress.
   */
  boolean checkEndCondition();

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
   * Notifies the level of a collision taking place, so that the level can tell whether it has ended
   * @param firstEntity The Name of one entity in a collision.
   * @param secondEntity The Name of another entity in a collision.
   */
  void registerCollision(String firstEntity, String secondEntity);

  /**
   * @return The ID of this level as assigned in the file.
   */
  String getLevelId();
}
