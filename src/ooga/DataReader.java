package ooga;

import java.util.List;

public interface DataReader {
  List<String> getThumbnails(String folderPath) throws OogaDataException;

  Game loadGame(String filePath) throws OogaDataException;

  void saveGameState(String filePath) throws OogaDataException;

  void loadGameState(String filePath) throws OogaDataException;

  String getEntityImage(String entityName, String gameFile) throws OogaDataException;
}