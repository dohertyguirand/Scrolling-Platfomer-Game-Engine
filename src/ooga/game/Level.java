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
}
