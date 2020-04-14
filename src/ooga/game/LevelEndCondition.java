package ooga.game;

/**
 * Represents a behavior for a level to tell whether it is over.
 * Example: NoEntityCondition might return true when a specified entity
 * doesn't exist in the level.
 */
public interface LevelEndCondition {

  /**
   * @return True if the game should continue, ending this level.
   */
  boolean isLevelDone();

}
