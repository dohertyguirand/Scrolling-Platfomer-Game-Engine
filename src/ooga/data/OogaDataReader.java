package ooga.data;
import ooga.OogaDataException;
import ooga.game.Game;

import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 * info @ https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 * TODO: create a better header
 */

public class OogaDataReader implements DataReader{
    private String myLibraryFilePath;
    private Map<String entityName, OogaEntity entityDefinition>

    @Override
    public List<Thumbnail> getThumbnails(String folderPath) throws OogaDataException {
        return null;
    }

    /**
     * give the Game a list of level ID's in the order that they're listed in the .xml files, its name to entity map
     * as well as anythiing else the game will need directly after it's created
     * @param
     * @return
     * @throws OogaDataException
     */
    //@Override
    public List<Integer> getBasicGameInfo(String gameName) throws OogaDataException {
        return null;
    }

//    @Override
//    public List<String> getGameFiles(String folderPath) throws OogaDataException {
//        return null;
//    }

    @Override
    public Game loadGame(String filePath) throws OogaDataException {
        try {
            // create a new document to parse
            File fXmlFile = new File(filePath);
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            //doc.getDocumentElement().normalize();

            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            //print information about the game
            System.out.println("Game title: " + doc.getElementsByTagName("Name").item(0).getTextContent());
            System.out.println("Description: " + doc.getElementsByTagName("Description").item(0).getTextContent());
            System.out.println("Thumbnail: " + doc.getElementsByTagName("Thumbnail").item(0).getTextContent());

            // in the xml create a list of all 'Level' nodes
            NodeList levelList = doc.getElementsByTagName("Level");
            System.out.println("Number of levels: " + levelList.getLength());

            System.out.println("----------------------------");

            //loop through all levels and display their information
            for (int i=0; i<levelList.getLength(); i++) {
                Node currentLevel = levelList.item(i);
                Element levelAsElement = (Element) currentLevel;
                // print ID and end conditions
                System.out.println("Level " + levelAsElement.getElementsByTagName("ID").item(0).getTextContent());
                System.out.println("End Condition: " + levelAsElement.getElementsByTagName("EndCondition").item(0).getTextContent());

                //loop through and print instances
                NodeList currentLevelInstances =
                        ((Element) levelAsElement.getElementsByTagName("Instances").item(0)).getElementsByTagName("Instance");
                System.out.println(currentLevelInstances.getLength() + " Instances:");

                for (int j=0; j<currentLevelInstances.getLength(); j++) {
                    Element currentChild = (Element) currentLevelInstances.item(j);
                    if(currentChild.getNodeType() == Node.ELEMENT_NODE){
                        // for each instance, print its type and location
                        String type = currentChild.getElementsByTagName("Type").item(0).getTextContent();
                        String xpos = currentChild.getElementsByTagName("XPos").item(0).getTextContent();
                        String ypos = currentChild.getElementsByTagName("YPos").item(0).getTextContent();
                        System.out.println(String.format("\t%s at x:%s y:%s", type, xpos, ypos));
                    }
                }

                // a space afterwards for ~asthetics~
                System.out.println();
            }
        } catch (Exception e) {
            // TODO: This ^v is gross get rid of it :) (written by Braeden to Braeden)
            e.printStackTrace();
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
