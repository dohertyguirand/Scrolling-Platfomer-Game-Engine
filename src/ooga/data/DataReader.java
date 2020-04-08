package ooga.data;

import java.util.List;
import ooga.OogaDataException;
import ooga.game.Game;

/**
 * Handles the interaction with game data files, including interpretation and writing.
 * Forms the external side of data interaction so that the rest of the program doesn't
 * need to know how we're storing games.
 */
public interface DataReader {

  /**
   * Returns a list of thumbnails for all the available games.
   * Returns an empty list if there are no files containing thumbnails.
   * @return The list of thumbnails of games.
   */
  List<Thumbnail> getThumbnails();

  /**
   * Returns the filepaths to every game file detected in the folder. Doesn't guarantee
   * that it will scan for full validity of every file, but makes basic checks.
   * @param folderPath The folder to check for game data files.
   * @return A list of filepaths of game data files in the given folder.
   * @throws OogaDataException if the given filepath isn't a directory.
   */
  List<String> getGameFiles(String folderPath) throws OogaDataException;

  /**
   * @param filePath The relative filepath of the game, including the filename.
   * @return A fully loaded Game that is runnable by the engine and represents the game in the
   * data file.
   * @throws OogaDataException If the file given is not a valid game.
   */
  Game loadGame(String filePath) throws OogaDataException;

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

  /**
   * @param entityName The name of the entity to find the image resource for.
   * @param gameFile The name of the game file to look for the entity inside of.
   * @return The image file location associated with the identified entity.
   * @throws OogaDataException If the entity doesn't exist, or there's no valid game file given,
   * or the given entity has no associated image.
   */
  String getEntityImage(String entityName, String gameFile) throws OogaDataException;
}