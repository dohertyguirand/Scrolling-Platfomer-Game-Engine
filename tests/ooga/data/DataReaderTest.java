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

    @Test
    public void runTest(){
        OogaDataReader testDataReader = new OogaDataReader();
        List<Thumbnail> thumbnailList = testDataReader.getThumbnails();
        for(Thumbnail t : thumbnailList){
            System.out.println(String.format("Title: %s \nDescription: %s \nImage: %s\n", t.getTitle(), t.getDescription(), t.getImageFile()));
        }
        try {
            testDataReader.loadLevel("Super Mario Bros", "1");
        } catch (OogaDataException e) {
            // TODO: Fix this, Braeden
            System.out.println("Test Failed");
            e.printStackTrace();
        }
    }
}
