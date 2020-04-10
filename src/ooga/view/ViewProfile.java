package ooga.view;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ooga.Profile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class ViewProfile extends BorderPane implements Profile {
    private String myProfilePhoto;
    private String myName;
    private Map<String, Integer> myHighestScores;
    private static final String DEFAULT_PROFILE_PHOTO = "ooga/view/Resources/profilephotos/defaultphoto.jpg";
    private ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
    private final double WINDOW_HEIGHT = Double.parseDouble(myResources.getString("windowHeight"));
    private final double WINDOW_WIDTH = Double.parseDouble(myResources.getString("windowWidth"));


    public ViewProfile(){
    }

    public void showProfile(){
        this.setHeight(WINDOW_HEIGHT);
        this.setWidth(WINDOW_WIDTH);
        this.setTop(setNameAndPhoto());
        this.setCenter(setStatsBox());
    }
    private VBox setNameAndPhoto(){
        VBox nameAndPhoto = new VBox();
        if(myProfilePhoto == null|| myProfilePhoto == "") myProfilePhoto = DEFAULT_PROFILE_PHOTO;
        ImageView photoImage = new ImageView(myProfilePhoto);
        nameAndPhoto.getChildren().add(photoImage);
        nameAndPhoto.getChildren().add(setNameText());
        nameAndPhoto.setOnDragEntered(e-> handleDroppedPhoto(e));
        nameAndPhoto.setOnDragDropped(e-> handleDroppedPhoto(e));
        return nameAndPhoto;
    }

    private void handleDroppedPhoto(DragEvent e){
        Dragboard db = e.getDragboard();
        List<File> files = db.getFiles();
        for(File file: files){
            BufferedImage bufferedImage;
            try {
                bufferedImage = ImageIO.read(file);
                if(bufferedImage == null) return;
                String profilePhotoPath = "src/ooga/view/Resources/profilephotos/" + myName+ "profilephoto.jpg";
                File newFile = new File(profilePhotoPath);
                ImageIO.write(bufferedImage,"png",newFile);
                myProfilePhoto = "ooga/view/Resources/profilephotos/" + myName+ "profilephoto.jpg" ;
                setTop(setNameAndPhoto());
            } catch (IOException ex) {
            } }
    }
    private HBox setNameText(){
        Text name = new Text(myName);
        HBox hBox = new HBox();
        hBox.getChildren().add(name);
        return hBox;
    }
    private GridPane setStatsBox(){
        GridPane gridPane = new GridPane();
        gridPane.setHgap(50);
        int row = 0;
        for(String game: myHighestScores.keySet()){
            Integer stat = myHighestScores.get(game);
            Text gameName = new Text(game);
            Text statText = new Text(stat.toString());
            gridPane.add(gameName,0,row);
            gridPane.add(statText,1,row);
            row++;
        }
        return gridPane;
    }

    public void setProfilePhoto(String photoPath){
        myProfilePhoto = photoPath;
    }

    @Override
    public void setProfileName(String name) {
        myName = name;
    }

    @Override
    public void setStats(Map<String,Integer> stats) {
        myHighestScores = stats;
    }

    @Override
    public Map<String,Integer> getStats() {
        return myHighestScores;
    }

    @Override
    public void updateStats(String gameName, Integer highScore) {
        myHighestScores.putIfAbsent(gameName,0);
        myHighestScores.put(gameName,highScore);
    }
    @Override
    public int getGameStats(String gameName) {
        return myHighestScores.get(gameName);
    }

    @Override
    public String getProfileName() {
        return myName;
    }
}
