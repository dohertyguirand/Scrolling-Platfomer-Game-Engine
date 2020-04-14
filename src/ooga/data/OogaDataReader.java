package ooga.data;
import ooga.*;
import ooga.game.Game;


import java.io.IOException;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import ooga.game.Level;
import ooga.game.OogaLevel;
import ooga.game.asyncbehavior.DestroySelfBehavior;
import ooga.game.asyncbehavior.StopDownwardVelocity;
import ooga.game.framebehavior.GravityBehavior;
import ooga.game.framebehavior.MoveForwardBehavior;
import ooga.game.inputbehavior.JumpBehavior;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;

/**
 * info @ https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 * TODO: create a better header
 */

public class OogaDataReader implements DataReader{

    private String myLibraryFilePath;   //the path to the folder in which is held every folder for every game that will be displayed and run
    private static String DEFAULT_LIBRARY_FILE = "data/games-library";
    private static String DEFAULT_USERS_FILE = "data/users";

    public OogaDataReader(String givenFilePath){
        myLibraryFilePath = givenFilePath;
    }
    public OogaDataReader(){
        this(DEFAULT_LIBRARY_FILE);
    }

    @Override
    public List<Thumbnail> getThumbnails() {
        // TODO: when OogaDataReader is constructed, check that libraryFile is a directory and isn't empty and that the gameDirectories aren't empty
        ArrayList<Thumbnail> thumbnailList = new ArrayList<>();
        for (File gameFile : getAllXMLFiles(myLibraryFilePath)){
            try {
                // create a new document to parse
                File fXmlFile = new File(String.valueOf(gameFile));
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fXmlFile);

                // find the required information in the document
                String gameTitle = doc.getElementsByTagName("Name").item(0).getTextContent();
                String gameDescription = doc.getElementsByTagName("Description").item(0).getTextContent();
                String gameThumbnailImageName = doc.getElementsByTagName("Thumbnail").item(0).getTextContent();

                String fullImagePath = "file:" + gameFile.getParentFile() + "/" + gameThumbnailImageName;
                Thumbnail newThumbnail = new Thumbnail(fullImagePath, gameTitle, gameDescription);
                thumbnailList.add(newThumbnail);
            } catch (SAXException | ParserConfigurationException | IOException e) {
                // TODO: This ^v is gross get rid of it :) (written by Braeden to Braeden)
                e.printStackTrace();
            }
        }
        return thumbnailList;
    }

    @Override
    public List<String> getBasicGameInfo(String givenGameName) throws OogaDataException {
        ArrayList<String> IDList = new ArrayList<>();
        File gameFile = findGame(givenGameName);
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(gameFile);
            // in the xml create a list of all 'Level' nodes
            NodeList levelList = doc.getElementsByTagName("Level");
            // add all IDs to the list
            for (int i = 0; i < levelList.getLength(); i++) {
                Node currentLevel = levelList.item(i);
                Element levelAsElement = (Element) currentLevel;
                String newID = levelAsElement.getElementsByTagName("ID").item(0).getTextContent();
                IDList.add(newID);
            }
        } catch (SAXException | ParserConfigurationException | IOException e) {
            // this error will never happen because it would have happened in findGame()
            throw new OogaDataException("This error should not ever occur");
        }
        return IDList;
    }

    @Override
    public List<String> getGameFilePaths() {
        ArrayList<String> FilePaths = new ArrayList<>();
        for(File f : getAllXMLFiles(myLibraryFilePath)){
            FilePaths.add(f.getPath());
        }
        return FilePaths;
    }

    /**
     * For Users and for Library, there is one directory holding several smaller directories, each
     * of which represents a game or user with its stored images and .xml files. This method returns a list
     * of those .xml files.
     * @return a list of xml files stored in subdirectories of the given file.
     * @param rootDirectory
     */
    private List<File> getAllXMLFiles(String rootDirectory){
        ArrayList<File> fileList = new ArrayList<>();
        File libraryFile = new File(rootDirectory);
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

    private File findGame(String givenGameName) throws OogaDataException {
        List<File> gameFiles = getAllXMLFiles(myLibraryFilePath);
        for(File f : gameFiles) {
            // check if this game file is the correct game file
            try {
                // create a new document to parse
                File fXmlFile = new File(String.valueOf(f));
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fXmlFile);
                String gameTitle = doc.getElementsByTagName("Name").item(0).getTextContent();
                if (gameTitle.equals(givenGameName)) return f;
            } catch (SAXException | ParserConfigurationException | IOException e) {
                // TODO: This ^v is gross get rid of it :) (written by Braeden to Braeden)
                e.printStackTrace();
                break;
            }
        }
        throw new OogaDataException("Requested game name not found in Library");
    }


    @Override
    public Level loadLevel(String givenGameName, String givenLevelID) throws OogaDataException {
        ArrayList<Entity> initialEntities = new ArrayList<>();
        File gameFile = findGame(givenGameName);
        Map<String, ImageEntityDefinition> entityMap = getEntityMap(givenGameName);
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(gameFile);
            // in the xml create a list of all 'Level' nodes
            NodeList levelNodes = doc.getElementsByTagName("Level");
            // for each check the ID
            for (int i = 0; i < levelNodes.getLength(); i++) {
                Element level = (Element) levelNodes.item(i);
                String levelID = level.getElementsByTagName("ID").item(0).getTextContent();
                if(levelID.equals(givenLevelID)){
                    // in the xml create a list of all 'Instance' nodes
                    NodeList entityNodes = doc.getElementsByTagName("Instance");
                    // for each, save a copy of the specified instance at the specified place
                    for (int j = 0; j < entityNodes.getLength(); j++) {
                        Node currentEntity = entityNodes.item(j);
                        Element entityElement = (Element) currentEntity;
                        String entityName = entityElement.getElementsByTagName("Type").item(0).getTextContent();
                        double xPosition = Double.parseDouble(entityElement.getElementsByTagName("XPos").item(0).getTextContent());
                        double yPosition = Double.parseDouble(entityElement.getElementsByTagName("YPos").item(0).getTextContent());
                        System.out.println(String.format("%s @ %f,%f", entityName, xPosition, yPosition));
                        initialEntities.add(entityMap.get(entityName).makeInstanceAt(xPosition,yPosition));
                    }
                }
            }
        } catch (SAXException | ParserConfigurationException | IOException e) {
            // this error will never happen because it would have happened in findGame()
            throw new OogaDataException("This error should not ever occur");
        }

        OogaLevel newLevel = new OogaLevel(initialEntities);

        return newLevel;
    }

    @Override
    public Map<String, ImageEntityDefinition> getEntityMap(String gameName) throws OogaDataException {
        Map<String, ImageEntityDefinition> retMap = new HashMap<>();
        File gameFile = findGame(gameName);
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(gameFile);
            // in the xml create a list of all 'Level' nodes
            NodeList entityNodes = doc.getElementsByTagName("Entity");
            // add all entities to the map
            for (int i = 0; i < entityNodes.getLength(); i++) {
                // create a new entity
                Node currentEntity = entityNodes.item(i);
                // if the entity has an image, it is an imageEntity
                Element entityElement = (Element) currentEntity;
                if(entityElement.getElementsByTagName("Image").getLength() > 0) {
                    // add the ImageEntity to the map
                    String newName = entityElement.getElementsByTagName("Name").item(0).getTextContent();
                    ImageEntityDefinition newIED = createImageEntityDefinition(entityElement);
                    retMap.put(newName, newIED);
                }
                else{
                    //TODO: add case for text Entity (figure out how you can add both to the same map and still distinguish them on the game side)
                }
            }
        } catch (SAXException | ParserConfigurationException | IOException e) {
            // this error will never happen because it would have happened in findGame()
            throw new OogaDataException("This error should not ever occur");
        }
        return retMap;
    }

    /**
     * Read the .xml file and create a new EntityDefinition as it describes
     * @param entityElement the Node describing the requested Entity
     * @return
     */
    private ImageEntityDefinition createImageEntityDefinition(Element entityElement) throws OogaDataException {
        String name = entityElement.getElementsByTagName("Name").item(0).getTextContent();
        Double height = Double.parseDouble(entityElement.getElementsByTagName("Height").item(0).getTextContent());
        Double width = Double.parseDouble(entityElement.getElementsByTagName("Width").item(0).getTextContent());
        String imagePath = myLibraryFilePath + "/" + entityElement.getElementsByTagName("Image").item(0).getTextContent();
        System.out.print(String.format("Name: %s ", name));

        List<MovementBehavior> movementBehaviors = new ArrayList<>();
        for (int i=0; i<entityElement.getElementsByTagName("MovementBehavior").getLength(); i++){
            // create an array of the behavior name and its space-separated parameters
            String[] behavior = entityElement.getElementsByTagName("MovementBehavior").item(i).getTextContent().split(" ");
            // TODO: improve the way this determines the type of Behavior
            // determine what type of behavior it is
            if(behavior[0].equals("Gravity")){
                double xGrav = 0.0, yGrav = 0.1;
                if (behavior.length == 2) {
                    yGrav = Double.parseDouble(behavior[1]);
                }
                if (behavior.length >= 3) {
                    xGrav = Double.parseDouble(behavior[1]);
                    yGrav = Double.parseDouble(behavior[2]);
                }
                System.out.print(String.format("Movement Behavior: Gravity %f, %f ", xGrav,yGrav));
                movementBehaviors.add(new GravityBehavior(xGrav, yGrav));
            }else if(behavior[0].equals("MoveForward")){
                double xVel = 0.0, yVel = 0.0;
                if (behavior.length == 2) {
                    xVel = Double.parseDouble(behavior[1]);
                }
                if (behavior.length >= 3) {
                    xVel = Double.parseDouble(behavior[1]);
                    yVel = Double.parseDouble(behavior[2]);
                }
                System.out.print(String.format("Movement Behavior: MoveForward %f, %f ", xVel,yVel));
                movementBehaviors.add(new MoveForwardBehavior(xVel, yVel));
            }else{
                throw new OogaDataException("Movement Behavior listed in game file is not recognized");
            }
        }

        Map<String,List<CollisionBehavior>> collisionBehaviors = new HashMap<>();
        for (int i=0; i<entityElement.getElementsByTagName("EntityCollision").getLength(); i++){
            Element behaviorElement = (Element) entityElement.getElementsByTagName("EntityCollision").item(i);
            String collisionObject = behaviorElement.getElementsByTagName("With").item(0).getTextContent();
            ArrayList<CollisionBehavior> reactions = new ArrayList<>();
            //loop through all reactions and add them to the list
            for (int j=0; j<behaviorElement.getElementsByTagName("Reaction").getLength(); j++) {
                String reaction = behaviorElement.getElementsByTagName("Reaction").item(j).getTextContent();
                // TODO: improve the way this determines the type of Behavior
                // determine what type of behavior it is
                if (reaction.equals("RemoveSelf")) {
                    System.out.print("Collision Behavior: " + collisionObject + " RemoveSelf ");
                    reactions.add(new DestroySelfBehavior());
                } else if (reaction.equals("StopDownwardVelocity")) {
                    System.out.print("Collision Behavior: " + collisionObject + " StopDownwardVel ");
                    reactions.add(new StopDownwardVelocity());
                } else if(reaction.equals("MoveUp")){
                    //TODO: verify this useage, I can't find it used anywhere so I'm not sure what this does
                } else {
                    throw new OogaDataException("Collision Behavior listed in game file is not recognized");
                }
            }
            collisionBehaviors.put(collisionObject, reactions);
        }


        Map<String, List<ControlsBehavior>> controlBehaviors = new HashMap<>();
        for (int i=0; i<entityElement.getElementsByTagName("ControlInput").getLength(); i++){
            Element behaviorElement = (Element) entityElement.getElementsByTagName("ControlInput").item(i);
            String keyPressed = behaviorElement.getElementsByTagName("Key").item(0).getTextContent();
            ArrayList<ControlsBehavior> reactions = new ArrayList<>();
            //loop through all reactions and add them to the list
            for (int j=0; j<behaviorElement.getElementsByTagName("ControlBehavior").getLength(); j++) {
                // create an array of the behavior name and its space-separated parameters
                String[] reaction = behaviorElement.getElementsByTagName("ControlBehavior").item(j).getTextContent().split(" ");
                // TODO: improve the way this determines the type of Behavior
                // determine what type of behavior it is
                if (reaction[0].equals("JumpBehavior")) {
                    double yVel = -1.2;
                    if(reaction.length>=2) yVel = Double.parseDouble(reaction[1]);
                    System.out.print("Control Behavior: " + keyPressed + " Jump ");
                    reactions.add(new JumpBehavior(yVel));
                } else {
                    throw new OogaDataException("Control Behavior listed in game file is not recognized");
                }
            }
            controlBehaviors.put(keyPressed, reactions);
        }

        ImageEntityDefinition newIED = new ImageEntityDefinition(name, height, width, imagePath, movementBehaviors,
                 collisionBehaviors, controlBehaviors);

        System.out.println();

        return newIED;
    }

    @Override
    public List<Profile_Temporary> getProfiles() {
        // TODO: when OogaDataReader is constructed, check that libraryFile is a directory and isn't empty and that the gameDirectories aren't empty
        ArrayList<Profile_Temporary> profileList = new ArrayList<>();
        for (File userFile : getAllXMLFiles(DEFAULT_USERS_FILE)){
            try {
                // create a new document to parse
                File fXmlFile = new File(String.valueOf(userFile));
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fXmlFile);

                // find the required information in the document
                String userName = doc.getElementsByTagName("Name").item(0).getTextContent();
                String userImage = doc.getElementsByTagName("Image").item(0).getTextContent();

                String fullImagePath = "file:" + userFile.getParentFile() + "/" + userImage;
                Profile_Temporary newProfile = new Profile_Temporary(userName, fullImagePath);
                profileList.add(newProfile);
            } catch (SAXException | ParserConfigurationException | IOException e) {
                // TODO: This ^v is gross get rid of it :) (written by Braeden to Braeden)
                e.printStackTrace();
            }
        }
        return profileList;
    }

    @Override
    public void saveGameState(String filePath) throws OogaDataException {

    }

    @Override
    public Game loadGameState(String filePath) throws OogaDataException {
        return null;
    }

    @Override
    public String getEntityImage(String entityName, String gameFile) throws OogaDataException {

        return null;
    }


}
