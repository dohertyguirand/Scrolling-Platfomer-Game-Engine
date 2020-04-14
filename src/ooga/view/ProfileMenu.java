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
    private List<ViewProfile> myProfiles;
    private String addNewProfilePhoto = "ooga/view/Resources/profilephotos/defaultphoto.jpg";



    protected ProfileMenu(){
        super();
        makeViewProfiles();
        addProfileImages();
    }


    private void makeViewProfiles(){
        List<OggaProfile> oggaProfiles = new ArrayList<>();
        //List<OggaProfile> oggaProfiles = myDataReader.getProfiles();
        for(OggaProfile oggaProfile : oggaProfiles){
            ViewProfile viewProfile = new ViewProfile(oggaProfile);
            myProfiles.add(viewProfile);
        }
    }

     private void addProfileImages(){
        if(myProfiles != null) {
            for (ViewProfile profile : myProfiles) {
                Button button = makeButton(profile.getProfilePhoto(),profile.getProfileName());
                button.setOnAction(e -> {
                    setOptionSelected(profile.getProfileName());
                    setProfileSelected(profile);
                });
                myHBox.getChildren().add(button);
            }
        }
//         ImageView defaultImage = new ImageView(addNewProfilePhoto);
//         Button button = makeButton(defaultImage,"Add a New Profile");
//         button.setOnAction(e->);
//         myHBox.getChildren().add(button);
}

     private Button makeButton(ImageView image, String name){
        resizeImage(image,1);
        Button button = new Button(null,image);
        button.setOnMouseEntered(e -> resizeImage(image, IMAGE_RESIZE_FACTOR));
        button.setOnMouseExited(e -> resizeImage(image, 1));
        button.setTooltip(new Tooltip(name));
        return button;
     }

     private void setProfileSelected(ViewProfile profile){
        this.profileSelected.set(null);
        this.profileSelected.set(profile);
     }

     public ObjectProperty profileSelected(){ return profileSelected; }

     public void setMyProfiles(List<ViewProfile> viewProfiles){
        myProfiles = viewProfiles;
        addProfileImages();
     }

}
