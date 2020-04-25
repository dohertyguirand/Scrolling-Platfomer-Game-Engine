package ooga.view.menus;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ooga.OogaDataException;
import ooga.data.OogaProfile;
import ooga.view.ViewProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileMenu extends ScrollMenu {
    private final ObjectProperty<ViewProfile> profileSelected = new SimpleObjectProperty<>();
    private List<ViewProfile> myProfiles = new ArrayList<>();
    private String addNewProfilePhoto = "ooga/view/Resources/profilephotos/Makenewprofile.png";
    private static final String ADD_PROFILE = "Add a New Profile";
    private static final String SUBMIT = "Submit";
    private static final String ERROR_MESSAGE = "Could Not Create New Profile";
    private static final String PROFILE_MENU_TITLE_KEY = "ProfileMenuTitle";

    /**
     * Shows profile options for user to choose, user can also add a new profile
     */
    public ProfileMenu(ResourceBundle languageResources){
        super(languageResources,PROFILE_MENU_TITLE_KEY );
        makeViewProfiles();
        addProfileImages();
        makeProfileButton();
    }

    /**
     * User has to choose a profile to play games, Visualizer listens to this property to know which profile has been selected
     * @return a ViewProfile that correlates to the profile selected by the user
     */
    public ObjectProperty<ViewProfile> profileSelected(){ return profileSelected; }



    private void makeViewProfiles() {
        List<OogaProfile> oogaProfiles;
        try {
          oogaProfiles = myProfileReader.getProfiles();
        }catch (OogaDataException e){
          return;
        }
        for (OogaProfile oogaProfile : oogaProfiles) {
            ViewProfile viewProfile = new ViewProfile(languageResources,oogaProfile.getProfileName(),oogaProfile.getProfilePhotoPath(), oogaProfile.getStats());
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
         button.setOnAction(e-> showNewProfileScreen());
         myHBox.getChildren().add(button);
    }

     private void showNewProfileScreen(){
         Button submit = new Button(SUBMIT);
         ViewProfile profile = new ViewProfile(languageResources,submit);
         Stage stage = new Stage();
         submit.setOnAction(e-> {
             stage.hide();
             addNewProfile(profile);
         });
         stage.setScene(new Scene(profile.getPane(),this.getWidth(),this.getHeight()));
         stage.show();
     }

     private void addNewProfile(ViewProfile profile){
        try {
            myProfileReader.addNewProfile(new OogaProfile(profile.getProfileName(),profile.getProfilePath()));
            myProfiles.add(profile);
            myHBox = new HBox();
            addProfileImages();
        } catch (OogaDataException e) {
             showError(ERROR_MESSAGE);
        }
     }

     private void setProfileSelected(ViewProfile profile){
        this.profileSelected.set(null);
        this.profileSelected.set(profile);
     }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR_MESSAGE);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Sets profiles to spefified lists of viewProfiles. Used for testing
     * @param viewProfiles list of viewProfiles
     */
    @Deprecated
    public void setMyProfiles(List<ViewProfile> viewProfiles){
        myProfiles = viewProfiles;
        addProfileImages();
    }

    private void makeProfileButton() {
        Button newProfileButton = new Button("Make a new Profile");
//        newProfileButton.setOnAction(e -> setProfileSelected(profile));
        myHBox.getChildren().add(newProfileButton);

    }
}
