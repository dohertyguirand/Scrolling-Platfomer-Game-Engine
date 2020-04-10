package ooga.view;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ooga.Profile;

import java.util.Map;
import java.util.ResourceBundle;


public class ViewProfile extends BorderPane implements Profile {
    private String myProfilePhoto;
    private String myName;
    private Map<String, Integer> myHighestScores;
    private static final String DEFAULT_PROFILE_PHOTO = "ooga/view/Resources/defaultphoto.jpg";
    private ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
    private final double WINDOW_HEIGHT = Double.parseDouble(myResources.getString("windowHeight"));
    private final double WINDOW_WIDTH = Double.parseDouble(myResources.getString("windowWidth"));


    public ViewProfile(){
    }

    public void showProfile(){
        this.setHeight(WINDOW_HEIGHT);
        this.setWidth(WINDOW_WIDTH);
        if(myProfilePhoto == "" || myProfilePhoto == null){ myProfilePhoto = DEFAULT_PROFILE_PHOTO; }
        VBox nameAndPhoto = new VBox();
        ImageView photoImage = new ImageView(myProfilePhoto);
        nameAndPhoto.getChildren().add(photoImage);
        nameAndPhoto.getChildren().add(setNameText());
        this.setTop(nameAndPhoto);
        this.setCenter(setStatsBox());
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
