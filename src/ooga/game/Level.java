package ooga.game;

import java.util.List;

import javafx.collections.ObservableList;
import ooga.EntityAPI;
import ooga.data.Entity;

/**
 * Represents one in-game level, including its end conditions and list of entities.
 */
public interface Level {

  /**
   * @return A List of all Entities in the level.
   */
  ObservableList<EntityAPI> getEntities();

  /**
   * Removes an entity from the level, if it is in the level.
   */
  void removeEntity(EntityAPI e);

  /**
   * Usually relies on an owned instance of LevelEndCondition to handle the unique logic of
   * a specific level's end condition.
   * @return True if the level should end, which should cause the game to progress.
   */
  boolean checkEndCondition();
}
