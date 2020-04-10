package ooga.data;
import ooga.Entity;
import ooga.OogaDataException;
import ooga.game.Game;


import java.io.IOException;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import ooga.game.Level;
import ooga.game.OogaLevel;
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

    //TODO: we as a team need to make an EntityDefinition interface
    // That is what should be in this map, not a full interface
    //TODO: If DataReader stores no game specific information, then Braeden needs to decide how this EntityMap will be
    // stored and distributed; shouldn't be too hard
    // create
    // private Map<String, EntityDefinition> myEntityMap;

    public OogaDataReader(String givenFilePath){
        myLibraryFilePath = givenFilePath;
    }
    public OogaDataReader(){
        this(DEFAULT_LIBRARY_FILE);
    }

    @Override
    public List<Thumbnail> getThumbnails() {
        System.out.println("Reading thumbnails");
        // TODO: when OogaDataReader is constructed, check that libraryFile is a directory and isn't empty and that the gameDirectories aren't empty
        ArrayList<Thumbnail> thumbnailList = new ArrayList<>();
        for (File gameFile : getAllGameFiles()){
            try {
                // create a new document to parse
                File fXmlFile = new File(String.valueOf(gameFile));
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fXmlFile);

                // find the required information in the document
                String gameTitle = doc.getElementsByTagName("Name").item(0).getTextContent();
                String gameDescription = doc.getElementsByTagName("Description").item(0).getTextContent();
                String gameThumbnailImageName = doc.getElementsByTagName("Thumbnail").item(0).getTextContent();

                System.out.println("file:" + gameFile.getParentFile() + "/" + gameThumbnailImageName);
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
        for(File f : getAllGameFiles()){
            FilePaths.add(f.getPath());
        }
        return FilePaths;
    }

    private List<File> getAllGameFiles(){
        ArrayList<File> fileList = new ArrayList<>();
        File libraryFile = new File(myLibraryFilePath);
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
        List<File> gameFiles = getAllGameFiles();
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
    public Level loadLevel(String gameName, String levelID) throws OogaDataException {
        ArrayList<Entity> initialEntities = new ArrayList<>();
        File gameFile = findGame(gameName);
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(gameFile);
            // in the xml create a list of all 'Level' nodes
            NodeList entityNodes = doc.getElementsByTagName("Entity");
            System.out.println("There are "+entityNodes.getLength()+" entities in this file.");
            System.out.print("They are ");
            // add all IDs to the list
            for (int i = 0; i < entityNodes.getLength(); i++) {
                Node currentEntity = entityNodes.item(i);
                Element entityElement = (Element) currentEntity;
                String entityName = entityElement.getElementsByTagName("Name").item(0).getTextContent();
                System.out.print(entityName+" ");
            }
            System.out.println(".");
        } catch (SAXException | ParserConfigurationException | IOException e) {
            // this error will never happen because it would have happened in findGame()
            throw new OogaDataException("This error should not ever occur");
        }

        OogaLevel newLevel = new OogaLevel(initialEntities);

//        try {
//            // create a new document to parse
//            // String filePath = myLibraryFilePath;
//            String filePath = "/Users/braedenward/Desktop/CS308/final_team17/data/games-library/example-mario/example_mario.xml";
//            File fXmlFile = new File(filePath);
//            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fXmlFile);
//
//            //optional, but recommended
//            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
//            //doc.getDocumentElement().normalize();
//
//            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
//            //print information about the game
//            System.out.println("Game title: " + doc.getElementsByTagName("Name").item(0).getTextContent());
//            System.out.println("Description: " + doc.getElementsByTagName("Description").item(0).getTextContent());
//            System.out.println("Thumbnail: " + doc.getElementsByTagName("Thumbnail").item(0).getTextContent());
//
//            // in the xml create a list of all 'Level' nodes
//            NodeList levelList = doc.getElementsByTagName("Level");
//            System.out.println("Number of levels: " + levelList.getLength());
//
//            System.out.println("----------------------------");
//
//            //loop through all levels and display their information
//            for (int i=0; i<levelList.getLength(); i++) {
//                Node currentLevel = levelList.item(i);
//                Element levelAsElement = (Element) currentLevel;
//                // print ID and end conditions
//                System.out.println("Level " + levelAsElement.getElementsByTagName("ID").item(0).getTextContent());
//                System.out.println("End Condition: " + levelAsElement.getElementsByTagName("EndCondition").item(0).getTextContent());
//
//                //loop through and print instances
//                NodeList currentLevelInstances =
//                    ((Element) levelAsElement.getElementsByTagName("Instances").item(0)).getElementsByTagName("Instance");
//                System.out.println(currentLevelInstances.getLength() + " Instances:");
//
//                for (int j=0; j<currentLevelInstances.getLength(); j++) {
//                    Element currentChild = (Element) currentLevelInstances.item(j);
//                    if(currentChild.getNodeType() == Node.ELEMENT_NODE){
//                        // for each instance, print its type and location
//                        String type = currentChild.getElementsByTagName("Type").item(0).getTextContent();
//                        String xpos = currentChild.getElementsByTagName("XPos").item(0).getTextContent();
//                        String ypos = currentChild.getElementsByTagName("YPos").item(0).getTextContent();
//                        System.out.println(String.format("\t%s at x:%s y:%s", type, xpos, ypos));
//                    }
//                }
//
//                // a space afterwards for ~asthetics~
//                System.out.println();
//            }
//        } catch (Exception e) {
//            // TODO: This ^v is gross get rid of it :) (written by Braeden to Braeden)
//            e.printStackTrace();
//        }

        return null;
    }

    @Override
    public Map<String, ImageEntityDefinition> getEntityMap(String gameName) throws OogaDataException {
        File gameFile = findGame(gameName);
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(gameFile);
            // in the xml create a list of all 'Level' nodes
            NodeList entityNodes = doc.getElementsByTagName("Entity");
            System.out.println("There are "+entityNodes.getLength()+" entities in this file.");
            System.out.print("They are ");
            // add all entities to the map
            for (int i = 0; i < entityNodes.getLength(); i++) {
                // create a new entity
                Node currentEntity = entityNodes.item(i);
                Element entityElement = (Element) currentEntity;
                String entityName = entityElement.getElementsByTagName("Name").item(0).getTextContent();
                System.out.print(entityName+" ");
            }
            System.out.println(".");
        } catch (SAXException | ParserConfigurationException | IOException e) {
            // this error will never happen because it would have happened in findGame()
            throw new OogaDataException("This error should not ever occur");
        }
        return null;
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
