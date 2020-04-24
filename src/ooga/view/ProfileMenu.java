package ooga.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import ooga.OogaDataException;

import java.util.ArrayList;
import java.util.List;

public class ProfileMenu extends ScrollMenu {
    private final ObjectProperty<ViewProfile> profileSelected = new SimpleObjectProperty<>();
    private List<ViewProfile> myProfiles = new ArrayList<>();
    private String addNewProfilePhoto = "ooga/view/Resources/profilephotos/Makenewprofile.png";
    private static final String ADD_PROFILE = "Add a New Profile";



    protected ProfileMenu(){
        super();
        makeViewProfiles();
        addProfileImages();
    }

    private void makeViewProfiles() {
        List<OogaProfile> oogaProfiles;
        try {
          oogaProfiles = myDataReader.getProfiles();
        }catch (OogaDataException e){
          return;
        }
        for (OogaProfile oogaProfile : oogaProfiles) {
            ViewProfile viewProfile = new ViewProfile(oogaProfile.getProfileName(),oogaProfile.myProfilePhotoPath);
            myProfiles.add(viewProfile);
        }
    }
     private void addProfileImages() {
            if (myProfiles != null) {
                for (ViewProfile profile : myProfiles) {
                    Button button = makeButton(new ImageView(profile.getProfilePath()), profile.getProfileName());
                    button.setOnAction(e -> setProfileSelected(profile));
                    myHBox.getChildren().add(button);
                }
            }
         ImageView defaultImage = new ImageView(addNewProfilePhoto);
         Button button = makeButton(defaultImage,ADD_PROFILE);
         //button.setOnAction(e-> );
         myHBox.getChildren().add(button);
        }

     private void setProfileSelected(ViewProfile profile){
        this.profileSelected.set(null);
        this.profileSelected.set(profile);
     }

     public ObjectProperty<ViewProfile> profileSelected(){ return profileSelected; }

     @Deprecated
     public void setMyProfiles(List<ViewProfile> viewProfiles){
        myProfiles = viewProfiles;
        addProfileImages();
     }

}
