package ooga.view;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ooga.OogaDataException;
import ooga.data.OogaProfile;
import ooga.data.gamedatareaders.GameDataReaderExternal;
import ooga.data.gamedatareaders.XMLGameDataReader;
import ooga.data.gamerecorders.XMLGameRecorder;
import ooga.data.profiledatareaders.XMLProfileReader;
import ooga.view.menus.GameMenu;
import ooga.view.menus.LoadMenu;
import ooga.view.menus.ProfileMenu;
import ooga.view.menus.ScrollMenu;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import static org.junit.Assert.assertNotEquals;

public class ViewingNewProfileTest {

    private XMLProfileReader myProfileReader = new XMLProfileReader();
    private List<ViewProfile> myProfiles = new ArrayList<>();
    public static final String DEFAULT_IMAGE_PATH = "ooga/view/Resources/profilephotos/defaultphoto.jpg";
    private ViewProfile newViewProfile = new ViewProfile(ResourceBundle.getBundle("ooga/view/Resources/languages.English"), "Test", "/Users/dohertyguirand/Spring2020/CS308/Finalimages/download-2.jpg");


    /**
     * Shows profile options for user to choose, user can also add a new profile
     */
    private void addNewProfile(ViewProfile profile){
        try {
            File photoFile = profile.getFileChosen();
            if(photoFile != null){
                myProfileReader.addNewProfile(profile.getProfileName(),profile.getFileChosen());
                myProfiles.add(profile);
                ImageView photo = new ImageView(profile.getProfilePath());
//                this.setBottom(horizontalScroller());
//                myProfiles = new ArrayList<>();
//                makeViewProfiles();
//                addProfileImages();
            }
        } catch (IllegalArgumentException | OogaDataException d){
            profile.setProfilePhotoPath(DEFAULT_IMAGE_PATH);
            //BUG TO BE FIXED LATER
        }
    }


    @Test
    public void newProfileDoesNotHaveDefaultPhoto(){
        addNewProfile(newViewProfile);
        for(ViewProfile profile: myProfiles){
            for(Node item: )
        }
    }
}
