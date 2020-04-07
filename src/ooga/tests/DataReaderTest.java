package ooga.tests;

import ooga.OogaDataException;
import ooga.data.OogaDataReader;

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
