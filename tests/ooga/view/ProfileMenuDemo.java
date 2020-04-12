package ooga.view;

import javafx.application.Application;
import javafx.stage.Stage;
import ooga.data.Thumbnail;

import java.util.List;

public class ProfileMenuDemo extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ProfileMenu profileMenu = new ProfileMenu();
        Thumbnail newThumbnail = new Thumbnail("ooga/view/Resources/profilephotos/tree.jpg", "Tree", "Tree's Profile");
        profileMenu.addProfileThumbnails(List.of(newThumbnail));
        profileMenu.addImages();
        stage.setScene(profileMenu.getScene());
        stage.show();
    }

}
