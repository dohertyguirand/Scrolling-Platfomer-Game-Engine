package ooga.game;

import javafx.beans.property.DoubleProperty;
import ooga.Entity;
import ooga.data.OogaEntity;

import javafx.collections.ObservableList;
import ooga.UserInputListener;

import java.util.List;

/**
 * An instance of a loaded game that holds all levels and global game data. Must be populated
 * with levels and any relevant persistent data like the starting number of lives. Handles
 * game logic that is applied to every entity within the game every frame.
 */
public interface Game {

  /**
   * @return All entities that ought to be drawn onscreen in the current frame.
   * This includes the player, enemies, terrain, powerups, etc., but not background or in-game
   * UI.
   */
  ObservableList<Entity> getEntities();

  /**
   * Runs anything that needs to be run by the game, levels, or any entities in a level when
   * the game is first started.
   */
  @Deprecated
  void doGameStart();

  /**
   * Updates things in the gaem according to how much time has passed
   * @param elapsedTime time passed in milliseconds
   */
  void doGameStep(double elapsedTime);

  /**
   * @return The String ID of the game's current level.
   */
  String getCurrentLevelId();

  void setCameraShiftProperties(List<DoubleProperty> property);
}
