package ooga.data.gamerecorders;

import ooga.OogaDataException;
import ooga.data.XMLDataReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public interface XMLGameRecorder extends GameRecorderExternal, XMLDataReader, GameRecorderInternal {

  String DEFAULT_USERS_FILE = "data/users";

  @Override
  default List<List<String>> getGameSaves(String userName, String gameName) throws OogaDataException {
    Document doc = getDocForUserName(userName);
    List<List<String>> gameSaveInfo = new ArrayList<>();
    // loop through all of the games saves in the user file and find the right games
    for (int i = 0; i < doc.getElementsByTagName(myDataResources.getString("UserFileGameTag")).getLength(); i++) {
      Element gameElement = (Element) doc.getElementsByTagName(myDataResources.getString("UserFileGameTag")).item(i);
      try {
        checkKeyExists(gameElement, myDataResources.getString("UserFileGameNameTag"), "");
      } catch (OogaDataException e) {
        // meant to be empty
        // skip games that don't have names
        continue;
      }
      String foundGameName = gameElement.getElementsByTagName(myDataResources.getString("UserFileGameNameTag")).item(0).getTextContent();
      if (foundGameName.equals(gameName)) {
        // add the Level ID and date to the list
        checkKeyExists(gameElement, myDataResources.getString("UserFileSaveDateTag"), myDataResources.getString("UserFileSaveMissingDates"));
        checkKeyExists(gameElement, myDataResources.getString("UserFileSaveFilePathTag"), myDataResources.getString("UserFileSaveMissingFilePaths"));

        String date = gameElement.getElementsByTagName(myDataResources.getString("UserFileSaveDateTag")).item(0).getTextContent();

        // get the level ID from the level file
        String loadFilePath = gameElement.getElementsByTagName(myDataResources.getString(myDataResources.getString("UserFileSaveFilePathTag"))).item(0).getTextContent();
        File levelFile = new File(loadFilePath);
        Document levelDoc = getDocument(levelFile, myDataResources.getString("DocumentParseException"));

        checkKeyExists(levelDoc, myDataResources.getString("SaveFileLevelTag"), myDataResources.getString("SaveFileMissingLevel"));
        Element savedLevelElement = (Element) levelDoc.getElementsByTagName(myDataResources.getString("SaveFileLevelTag")).item(0);

        checkKeyExists(savedLevelElement, myDataResources.getString("LevelIDTag"), myDataResources.getString("SaveFileLevelMissingID"));
        String id = savedLevelElement.getElementsByTagName(myDataResources.getString("LevelIDTag")).item(0).getTextContent();

        ArrayList<String> entry = new ArrayList<>(List.of(id, date));
        gameSaveInfo.add(entry);
      }
    }
    return gameSaveInfo;
  }

  /**
   * Saves the current state of the game so it can easily be loaded from where the player
   * left off.
   *
   * @param filePath The filepath at which to save the game.
   */
  @Override
  default void saveGameState(String filePath) {}

  /**
   * Looks through the user files and finds the path of the save file by the requested user at the requested date
   *
   * @param UserName : Name of the user who made the save file
   *                 if the requested username isn't listed in the file or doesn't have a save at the given time
   * @param Date
   */
  @Override
  default String getLevelFilePath(String UserName, String Date) {return null;}
}
