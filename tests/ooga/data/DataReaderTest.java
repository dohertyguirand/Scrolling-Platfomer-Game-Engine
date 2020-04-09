package ooga.data;

import ooga.OogaDataException;
import ooga.data.OogaDataReader;
import org.junit.jupiter.api.Test;

/**
 * This is a class that just tests DataReader and makes sure it is working correctly.
 * @author braedenward
 */
public class DataReaderTest {

    @Test
    public void runTest(){
        OogaDataReader testDataReader = new OogaDataReader();
        try {
            // at the time of writing this, the OogaDataReader doesn't use the given Strings
            // I will change this when I have that workign properly
            // -Braeden
            testDataReader.loadLevel("GameName", "LevelID");
        } catch (OogaDataException e) {
            // TODO: Fix this, Braeden
            System.out.println("Test Failed");
            e.printStackTrace();
        }
    }
}
