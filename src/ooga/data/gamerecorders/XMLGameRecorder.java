package ooga.data.gamerecorders;

import ooga.OogaDataException;
import ooga.data.XMLDataReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLGameRecorder implements XMLDataReader, GameRecorderInternal {

  @Override
  public List<List<String>> getGameSaves(String userName, String gameName) throws OogaDataException {
    Document doc = getDocForUserName(userName);
    List<List<String>> gameSaveInfo = new ArrayList<>();
    // loop through all of the games saves in the user file and find the right games
    for (int i = 0; i < doc.getElementsByTagName(myDataResources.getString("UserFileGameTag")).getLength(); i++) {
      Element gameElement = (Element) doc.getElementsByTagName(myDataResources.getString("UserFileGameTag")).item(i);
      String foundGameName = gameElement.getElementsByTagName(myDataResources.getString("UserFileGameNameTag")).item(0).getTextContent();
      if (foundGameName.equals(gameName)) {
        String date = getFirstElementByTag(gameElement, "UserFileSaveDateTag", myDataResources.getString("UserFileSaveMissingDates"));
        String loadFilePath = getFirstElementByTag(gameElement, "UserFileSaveFilePathTag", myDataResources.getString("UserFileSaveMissingFilePaths"));
        File levelFile = new File(loadFilePath);
        Document levelDoc = getDocument(levelFile);
        checkKeyExists(levelDoc, myDataResources.getString("SaveFileLevelTag"), myDataResources.getString("SaveFileMissingLevel"));
        Element savedLevelElement = (Element) levelDoc.getElementsByTagName(myDataResources.getString("SaveFileLevelTag")).item(0);
        String id = getFirstElementByTag(savedLevelElement, "LevelIDTag", myDataResources.getString("SaveFileLevelMissingID"));
        gameSaveInfo.add(List.of(id, date));
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
  public void saveGameState(String filePath) {}

  /**
   * Looks through the user files and finds the path of the save file by the requested user at the requested date
   *
   * @param UserName : Name of the user who made the save file
   *                 if the requested username isn't listed in the file or doesn't have a save at the given time
   * @param Date
   */
  @Override
  public String getLevelFilePath(String UserName, String Date) {return null;}
}
