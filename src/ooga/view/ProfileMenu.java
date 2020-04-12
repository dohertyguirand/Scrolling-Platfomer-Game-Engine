package ooga.view;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import ooga.Profile;
import ooga.data.DataReader;
import ooga.data.OogaDataReader;
import ooga.data.Thumbnail;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileMenu extends ScrollMenu {
    List<Thumbnail> thumbnails;

    protected ProfileMenu(){
        super();
        // line 25 is the default thumbnail that allows the user to add a new profile
        thumbnails = List.of(new Thumbnail("ooga/view/Resources/profilephotos/tree.jpg", "Tree", "Tree's Profile"),new Thumbnail("ooga/view/Resources/profilephotos/defaultphoto.jpg", "addNewProfile", "Add a new Profile")
        );

       // addImages(); removed for now because line 30 does not work, addImages is called in the ProfileMenuDemo class
    }

    @Override
    protected void addImages(){
        // Method not implemented in Data will return existing profile thumbnails
        //addProfileThumbnails(myDataReader.getProfileThumbnails());
        for (Thumbnail thumbnail : thumbnails) {
            ImageView optionImage = new ImageView(thumbnail.getImageFile());
            resizeImage(optionImage, 1);
            javafx.scene.control.Button gameButton = new Button(null, optionImage);
            gameButton.setOnAction(e -> {
                setOptionSelected(thumbnail.getTitle());
                System.out.println(optionSelected);
            });
            gameButton.setOnMouseEntered(e -> resizeImage(optionImage, IMAGE_RESIZE_FACTOR));
            gameButton.setOnMouseExited(e -> resizeImage(optionImage, 1));
            gameButton.setTooltip(new Tooltip(thumbnail.getDescription()));
            myHBox.getChildren().add(gameButton);
        }
    }

    public void addProfileThumbnails(List<Thumbnail> newThumbnails){
       List<Thumbnail> list = new ArrayList<>();
        if(newThumbnails != null) list.addAll(newThumbnails);
        list.addAll(thumbnails);
        thumbnails = list;
        //did it this was just in case the lists that are passed are immutable (ie List.of instead of new ArrayList)
    }
}
