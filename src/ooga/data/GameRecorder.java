package ooga.data;

import ooga.OogaDataException;
import ooga.game.Game;

/**
 * Handles saving the state of currently active games, or restoring save games.
 * Has methods that are also under the XMLGameDataReader title, since this might be a part of the
 * internal data API.
 */
@Deprecated
public interface GameRecorder {
  /**
   * Saves the current state of the game so it can easily be loaded from where the player
   * left off.
   * @param filePath The filepath at which to save the game.
   * @throws OogaDataException if the filepath is invalid, or if no game is loaded.
   */
  void saveGameState(String filePath) throws OogaDataException;

  /**
   * Loads a file that contains a saved game state, and returns a runnable game that picks up
   * where it left off.
   * @param filePath The filepath of the file to load.
   * @return A runnable Game that matches with how the game was when saved.
   * @throws OogaDataException if there is no valid file at the given filepath.
   */
  Game loadGameState(String filePath) throws OogaDataException;
}
