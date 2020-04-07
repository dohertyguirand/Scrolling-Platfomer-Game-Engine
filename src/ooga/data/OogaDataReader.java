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

    @Override
    public List<String> getThumbnails(String folderPath) throws OogaDataException {
        return null;
    }

    @Override
    public List<String> getGameFiles(String folderPath) throws OogaDataException {
        return null;
    }

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

            // in the xml create a list of all 'Level' nodes
            NodeList levelList = doc.getElementsByTagName("Level");

            System.out.println("----------------------------");
            System.out.println("Level List length: " + levelList.getLength());

            for (int i=0; i<levelList.getLength(); i++) {
                Node currentLevel = levelList.item(i);
                System.out.println("\nCurrent Element: " + currentLevel.getNodeName());
                NodeList currentLevelChildren = currentLevel.getChildNodes();
                for (int j=0; j<currentLevelChildren.getLength(); j++) {
                    Node currentChild = currentLevelChildren.item(j);
                    if(currentChild.getNodeType() == Node.ELEMENT_NODE){
                        System.out.print("\t Current Element: " + currentChild.getNodeName());
                        if(currentChild.getChildNodes().getLength() > 1){
                            System.out.println("\t CurrentChildren: ");
                        }
                        else System.out.println("\t Current Text: " + currentChild.getTextContent());
                    }
                }
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
