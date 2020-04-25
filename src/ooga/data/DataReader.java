package ooga.data;

import ooga.OogaDataException;
import org.w3c.dom.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface DataReader {

  /**
   * part of the data reader internal api
   * For Users and for Library, there is one directory holding several smaller directories, each
   * of which represents a game or user with its stored images and .xml files. This method returns a list
   * of those .xml files.
   * @return a list of xml files stored in subdirectories of the given file.
   */
  default List<File> getAllFiles(String filePath){
    ArrayList<File> fileList = new ArrayList<>();
    File libraryFile = new File(filePath);
    // loop through the library and find each game
    for (File gameDirectory : Objects.requireNonNull(libraryFile.listFiles())){
      if(!gameDirectory.isDirectory()) continue;
      // go through the game folder and find the game file
      for (File gameFile : Objects.requireNonNull(gameDirectory.listFiles())){
        // check if the file is a .xml
        String[] splitFile = gameFile.getName().split("\\.");
        String fileExtension = splitFile[splitFile.length-1];
        if(fileExtension.equals(getFileType())) fileList.add(gameFile);
      }
    }
    return fileList;
  }

  /**
   *
   * @return the file type of the data reader sub-interface (e.g. xml)
   */
  String getFileType();

  /**
   * gets the document (I'm assuming document is not xml specific)
   * @param file file
   * @param errorMessageKey properties file key for error message
   * @return desired document
   * @throws OogaDataException if the document could not be parsed
   */
  Document getDocument(File file, String errorMessageKey) throws OogaDataException;
}
