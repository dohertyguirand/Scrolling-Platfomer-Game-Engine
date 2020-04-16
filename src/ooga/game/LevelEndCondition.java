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

  /**
   * Notifies the level of a collision taking place, so that the level can tell whether it has ended
   * @param firstEntity The Name of one entity in a collision.
   * @param secondEntity The Name of another entity in a collision.
   */
  void registerCollision(String firstEntity, String secondEntity);

}
