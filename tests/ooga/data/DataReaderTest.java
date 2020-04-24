
package ooga.data;

import ooga.Entity;
import ooga.OogaDataException;
import ooga.game.Level;
import ooga.view.OogaProfile;
import org.junit.jupiter.api.Test;
import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a class that just tests DataReader and makes sure it is working correctly.
 * @author braedenward
 */
public class DataReaderTest {
    private OogaDataReader testDataReader = new OogaDataReader();
    private List<String> GAME_NAMES = new ArrayList<>(List.of(
            "Doodle Jump", "Chrome Dino", "Super Mario Bros", "Fireboy and Watergirl", "Flappy Bird", "VVVVVV"));

    @Test
    public void testGetThumbnails() throws OogaDataException {
        List<Thumbnail> thumbnailList = testDataReader.getThumbnails();
        for (Thumbnail t : thumbnailList) {
            System.out.println(String.format("Title: %s \nDescription: %s \nImage: %s\n", t.getTitle(), t.getDescription(), t.getImageFile()));
        }
    }
    @Test
    public void testGetBasicGameInfo(){
        for (String gameName : GAME_NAMES){
            List<List<String>> stringList = null;
            try {
                stringList = testDataReader.getBasicGameInfo(gameName);
            } catch (OogaDataException e) {
                // TODO: Fix this, Braeden
                e.printStackTrace();
                fail();
            }
            System.out.println("List of Level IDs recieved for " + gameName + ": " + stringList + "\n");
        }
    }

    @Test
    public void testLoadNewLevel() throws OogaDataException {
        // for every game load and display every level
        for (String gameName : GAME_NAMES){
            System.out.println("Game: " + gameName);
            List<String> idList = testDataReader.getLevelIDs(gameName);
            // for every level, load and print every entity
            for(String id : idList){
                System.out.println("Level: " + id);
                Level createdLevel = testDataReader.loadNewLevel(gameName, id);
                // for every entity, neatly display its information (in a very clue-esque way)
                for (Entity e : createdLevel.getEntities()){
                    System.out.println(String.format("Entity named %s with ID %s at position %s with variables %s",
                            e.getName(), e.getEntityID(), e.getPosition().toString(), e.getVariables().toString()));
                }
            }
        }
    }

    @Test
    public void testGetGameFilePaths(){
        List<String> pathList = testDataReader.getGameFilePaths();
        System.out.println(pathList);
    }

    @Test
    public void testGetEntityMap(){
        for (String gameName : GAME_NAMES){
            Map<String, ImageEntityDefinition> retMap = null;
            try {
                //TODO: Add getEntityMap
                retMap = testDataReader.getImageEntityMap(gameName);
            } catch (OogaDataException e) {
                e.printStackTrace();
                fail();
            }
            for(String key : retMap.keySet()){
                Entity e = retMap.get(key).makeInstanceAt(0.0,0.0);
                System.out.print("Name: "+ key + "   ");
                System.out.print("Height: " + e.getHeight()+"   ");
                System.out.println("Width: " + e.getWidth());
            }
            System.out.println(retMap);
        }
    }

    @Test
    public void testGetProfiles(){
        List<OogaProfile>  profiles = null;
        try {
            profiles = testDataReader.getProfiles();
        } catch (OogaDataException e) {
            e.printStackTrace();
        }
        System.out.println(profiles.size() + " Profiles:");
        for (OogaProfile profile : profiles){
            assertNotNull(profile.getProfileName(), "Profile name is null");
            assertNotNull(profile.getProfilePhotoPath(), "Profile photo is null");
            System.out.println(String.format("Name %s  Image: %s", profile.getProfileName(), profile.getProfilePhotoPath()));
        }
    }

    @Test
    public void testAddNewProfile(){
        OogaProfile testProfile = new OogaProfile();
        testProfile.setProfileName("Doug Rattman");
        testProfile.setProfilePhoto("data/users/Rattman.jpeg");
        try {
            testDataReader.addNewProfile(testProfile);
        } catch (OogaDataException e) {
            e.printStackTrace();
            fail();
        }
    }

}

