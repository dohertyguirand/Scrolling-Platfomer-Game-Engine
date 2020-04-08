package ooga.game;

import java.util.List;
import ooga.data.Entity;

import javafx.collections.ObservableList;
import ooga.UserInputListener;
import ooga.data.Entity;

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
  void doGameStart();

  /**
   * Detects all collisions between entities in the current level and has them resolve their
   * collisions. Can cause entities to be created or removed. The rate at which this is called
   * depends on how often this and doUpdateLoop are called.
   */
  void doCollisionLoop();

  /**
   * Handles any functionality that happens per frame regardless of collisions or input.
   * Examples include: Entities moving forward by themselves, gravity.
   * @param elapsedTime The real time elapsed between this frame and the last.
   */
  void doUpdateLoop(double elapsedTime);

  /**
   * Handles anything that happens inside the game, globally or by an entity, based on the
   * action (method or class) that is mapped to the given String (probably in a resource file).
   * Happens asynchronously to operations that happen every frame.
   * @param input A String identifying the type of input, like "Forward" or "ActionButton".
   */
  void handleUserInput(String input);

  UserInputListener makeUserInputListener();
}
