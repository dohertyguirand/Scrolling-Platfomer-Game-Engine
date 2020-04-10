package ooga.data;

import ooga.OogaDataException;
import ooga.data.OogaDataReader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a class that just tests DataReader and makes sure it is working correctly.
 * @author braedenward
 */
public class DataReaderTest {
    private OogaDataReader testDataReader = new OogaDataReader();
    private String GAME_NAME = "Super Mario Bros";
    private String ID = "1";

    @Test
    public void testGetThumbnails(){
        List<Thumbnail> thumbnailList = testDataReader.getThumbnails();
        for (Thumbnail t : thumbnailList) {
            System.out.println(String.format("Title: %s \nDescription: %s \nImage: %s\n", t.getTitle(), t.getDescription(), t.getImageFile()));
        }
    }
    @Test
    public void testGetBasicGameInfo(){
        List<String> stringList = null;
        try {
            stringList = testDataReader.getBasicGameInfo("Super Mario Bros");
        } catch (OogaDataException e) {
            // TODO: Fix this, Braeden
            System.out.println("Test Failed");
            e.printStackTrace();
        }
        System.out.println("List of Level IDs for " + GAME_NAME + ": " + stringList + "\n");
    }

    @Test
    public void testLoadLevel(){
        try {
            testDataReader.loadLevel(GAME_NAME, ID);
        } catch (OogaDataException e) {
            // TODO: Fix this, Braeden
            System.out.println("Test Failed");
            e.printStackTrace();
        }
    }
}
