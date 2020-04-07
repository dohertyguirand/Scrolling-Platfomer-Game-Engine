package ooga.tests;

import ooga.OogaDataException;
import ooga.data.OogaDataReader;

public class DataReaderTest {
    public void runTest(){
        OogaDataReader testDataReader = new OogaDataReader();
        String testFilePath = "jetbrains://idea/navigate/reference?project=final_team17&fqn=data.example-mario";
        try {
            testDataReader.getGameFiles(testFilePath);
        } catch (OogaDataException e) {
            System.out.println("Test Failed");
            e.printStackTrace();
        }
    }
}
