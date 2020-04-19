package ooga.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import ooga.data.Thumbnail;

import java.util.ArrayList;
import java.util.List;

public class ProfileMenu extends ScrollMenu {
    private ObjectProperty<ViewProfile> profileSelected = new SimpleObjectProperty<>();
    private List<ViewProfile> myProfiles = new ArrayList<ViewProfile>();
    private String addNewProfilePhoto = "ooga/view/Resources/profilephotos/defaultphoto.jpg";



    protected ProfileMenu(){
        super();
        makeViewProfiles();
        addProfileImages();
    }


    private void makeViewProfiles() {
        //List<OggaProfile> oggaProfiles = new ArrayList<>();
        List<OggaProfile> oggaProfiles = myDataReader.getProfiles();
        for (OggaProfile oggaProfile : oggaProfiles) {
            ViewProfile viewProfile = new ViewProfile(oggaProfile);
            myProfiles.add(viewProfile);
        }
    }
     private void addProfileImages() {
            if (myProfiles != null) {
                for (ViewProfile profile : myProfiles) {
                    Button button = makeButton(profile.getProfilePhoto(), profile.getProfileName());
                    button.setOnAction(e -> setProfileSelected(profile));
                    myHBox.getChildren().add(button);
                }
            }
//         ImageView defaultImage = new ImageView(addNewProfilePhoto);
//         Button button = makeButton(defaultImage,"Add a New Profile");
//         button.setOnAction(e-> );
//         myHBox.getChildren().add(button);
        }

     private void setProfileSelected(ViewProfile profile){
        this.profileSelected.set(null);
        this.profileSelected.set(profile);
     }

     public ObjectProperty<ViewProfile> profileSelected(){ return profileSelected; }

     public void setMyProfiles(List<ViewProfile> viewProfiles){
        myProfiles = viewProfiles;
        addProfileImages();
     }

}
