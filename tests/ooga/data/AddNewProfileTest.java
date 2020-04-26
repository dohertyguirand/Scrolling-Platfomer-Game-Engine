package ooga.data;


import ooga.OogaDataException;
import ooga.data.profiledatareaders.XMLProfileReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class AddNewProfileTest {

    XMLProfileReader profileReader = new XMLProfileReader();
    String exampleName = "Test" + System.currentTimeMillis();
    File photoFile = new File("/Users/dohertyguirand/Spring2020/CS308/Projects/final_team17/src/ooga/view/Resources/alien.jpg");

    @Test
    public void testAddingUniqueProfile() {
        try {
            profileReader.addNewProfile(exampleName, photoFile);
        } catch (OogaDataException ex) {
            fail("Should not have thrown an exception");
        }
    }
}
