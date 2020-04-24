package ooga.data;

import ooga.OogaDataException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * this is part of internal API. it houses the methods that are common to any type of XML data reader (e.g. XMLGameDataReader)
 */
public interface XMLDataReader {

  String ENGLISH_PROPERTIES_LOCATION = "ooga/data/resources/english";
  ResourceBundle myDataResources = ResourceBundle.getBundle(ENGLISH_PROPERTIES_LOCATION);

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
        if(fileExtension.equals("xml")) fileList.add(gameFile);
      }
    }
    return fileList;
  }

  default String getFirstElementByTag(Element element, String tagKey, String errorMessage) throws OogaDataException {
    checkKeyExists(element, tagKey, errorMessage);
    return element.getElementsByTagName(myDataResources.getString(tagKey)).item(0).getTextContent();
  }

  default String getFirstElementByTag(Document document, String tagKey, String errorMessage) throws OogaDataException {
    checkKeyExists(document, tagKey, errorMessage);
    return document.getElementsByTagName(myDataResources.getString(tagKey)).item(0).getTextContent();
  }

  default void checkKeyExists(Element element, String tagKey, String errorMessage) throws OogaDataException {
    if(element.getElementsByTagName(myDataResources.getString(tagKey)).getLength() == 0)
      throw new OogaDataException(errorMessage);
  }

  default void checkKeyExists(Document document, String key, String errorMessage) throws OogaDataException {
    if(document.getElementsByTagName(myDataResources.getString(key)).getLength() == 0)
      throw new OogaDataException(errorMessage);
  }

  default String getFirstElementOrDefault(Element element, String tagKey, String defaultValue){
    if(element.getElementsByTagName(myDataResources.getString(tagKey)).getLength() == 0) return defaultValue;
    return element.getElementsByTagName(myDataResources.getString(tagKey)).item(0).getTextContent();
  }

  default Document getDocument(File fXmlFile, String errorMessageKey) throws OogaDataException {
    Document doc;
    try {
      doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fXmlFile);
    } catch (SAXException | ParserConfigurationException | IOException e) {
      throw new OogaDataException(myDataResources.getString(errorMessageKey));
    }
    return doc;
  }
}
