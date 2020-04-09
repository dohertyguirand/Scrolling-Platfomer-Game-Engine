package ooga.data;

import ooga.OogaDataException;
import ooga.data.OogaDataReader;
import org.junit.jupiter.api.Test;

/**
 * This is a class that just tests DataReader and makes sure it is working correctly. It should be moved/changed so that it is
 * closer to the other tests and more similar to them in functionality, but I don't currently know how to do that
 * TODO: ask sam how to do that^
 * @author braedenward
 */
public class DataReaderTest {

    @Test
    public void runTest(String filePath){
        OogaDataReader testDataReader = new OogaDataReader();
        String testFilePath = filePath;
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
