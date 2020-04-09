package ooga.data;

/**
 * This class encapsulates the initial representation of a Game on the home screen. It is everything the DataReader needs
 * to give initially including but not limited to the thumbnail image, the game's title, and the game's description.
 */
public class Thumbnail {

    private String imageFile;
    private String title;
    private String description;

    public Thumbnail(String imageFileName, String title, String description){
        imageFile = imageFileName;
        this.title = title;
        this.description = description;
    }

    public String getImageFile() {
        return imageFile;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
