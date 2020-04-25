package ooga.data.profiledatareaders;

import ooga.OogaDataException;
import ooga.data.XMLDataReader;
import ooga.view.OogaProfile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public interface XMLProfileReader extends XMLDataReader, ProfileReaderExternal, ProfileReaderInternal {

  String DEFAULT_USERS_FILE = "data/users";

  /**
   * Adds a given profile to the profile folder
   * @param newProfile the profile to add to the saved profile folder
   */
  default void addNewProfile(OogaProfile newProfile) {
    //TODO: make sure profile doesn't already exist
    String newProfileName = newProfile.getProfileName();
    try {
      Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
      String directory = DEFAULT_USERS_FILE+"/"+newProfileName;
      String filepath = directory+"/"+newProfileName+".xml";
      File file = new File(directory);
      boolean bool = file.mkdir();
      if(bool){
        System.out.println("Directory created successfully");
      }else{
        System.out.println("Sorry could not create specified directory");
      }
      Element root = document.createElement("User");
      document.appendChild(root);
      Element nameElement = document.createElement(myDataResources.getString("ProfileNameTag"));
      nameElement.appendChild(document.createTextNode(newProfileName));
      root.appendChild(nameElement);
      String newProfileImage = newProfile.getProfilePhotoPath();
      //copy image into the new user's folder
      Path src = Paths.get(newProfileImage);
      String imageName = newProfileImage.split("/")[newProfileImage.split("/").length-1];
      String copiedProfileImage = directory+"/"+imageName; //
      Path dest = Paths.get(copiedProfileImage);
      Files.copy(src, dest);
      //change the directory stored in the given Profile to point to this new copy of the image
      newProfile.setProfilePhoto(imageName);
      Element imageElement = document.createElement(myDataResources.getString("ProfileImageTag"));
      imageElement.appendChild(document.createTextNode(imageName));
      root.appendChild(imageElement);
      Element saveStateElement = document.createElement("SavedGameStates");
      root.appendChild(saveStateElement);
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource domSource = new DOMSource(document);
      StreamResult streamResult = new StreamResult(new File(filepath));
      transformer.transform(domSource, streamResult);
    } catch (ParserConfigurationException | TransformerException | IOException pce) {
      pce.printStackTrace();
      //TODO: remove print stack trace
    }
  }

  /**
   * @return A list of Profiles according to the data stored in the Users folder. Returns an empty list if there are no
   * existing profiles
   */
  default List<OogaProfile> getProfiles() throws OogaDataException{
    // TODO: when OogaDataReader is constructed, check that libraryFile is a directory and isn't empty and that the gameDirectories aren't empty
    ArrayList<OogaProfile> profileList = new ArrayList<>();
    for (File userFile : getAllFiles(DEFAULT_USERS_FILE)){
      File fXmlFile = new File(String.valueOf(userFile));
      Document doc = getDocument(fXmlFile, myDataResources.getString("DocumentParseException"));
      String userName = getFirstElementByTag(doc, "ProfileNameTag", myDataResources.getString("MissingUserNameException"));
      String userImage = getFirstElementByTag(doc, "ProfileImageTag", myDataResources.getString("MissingUserImageException"));
      String fullImagePath = myDataResources.getString("PathPrefix") + userFile.getParentFile() +
              myDataResources.getString("Slash") + userImage;
      OogaProfile newProfile = new OogaProfile();
      newProfile.setProfileName(userName);
      newProfile.setProfilePhoto(fullImagePath);
      profileList.add(newProfile);
    }
    return profileList;
  }
}
