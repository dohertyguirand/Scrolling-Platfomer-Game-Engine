package ooga.data;

import java.awt.*;

/**
 * This class encapsulates the initial representation of a Game on the home screen. It is everything the DataReader needs
 * to give initially including but not limited to the thumbnail image, the game's title, and the game's description.
 */
public class Thumbnail {
    private String myImageFile;
    private String myTitle;
    private String myDescription;

    public Thumbnail(String imageFileName, String title, String description){
        myImageFile = imageFileName;
        myTitle = title;
        myDescription = description;
    }
}
