package ooga.tests;

import ooga.OogaDataException;
import ooga.data.OogaDataReader;

/**
 * This is a class that just tests DataReader and makes sure it is working correctly. It should be moved/changed so that it is
 * closer to the other tests and more similar to them in functionality, but I don't currently know how to do that
 * TODO: ask sam how to do that^
 * @author braedenward
 */
public class DataReaderTest {
    public void runTest(String filePath){
        OogaDataReader testDataReader = new OogaDataReader();
        String testFilePath = filePath;
        try {
            testDataReader.loadGame(testFilePath);
        } catch (OogaDataException e) {
            // TODO: Fix this, Braeden
            System.out.println("Test Failed");
            e.printStackTrace();
        }
    }
}
