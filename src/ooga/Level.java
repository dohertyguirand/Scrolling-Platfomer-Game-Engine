package ooga;

import java.util.List;

/**
 * Represents one in-game level, including its end conditions and list of entities.
 */
public interface Level {

  /**
   * @return A List of all Entities in the level.
   */
  List<Entity> getEntities();

  /**
   * Usually relies on an owned instance of LevelEndCondition to handle the unique logic of
   * a specific level's end condition.
   * @return True if the level should end, which should cause the game to progress.
   */
  boolean checkEndCondition();
}
