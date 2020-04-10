package ooga.view;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ooga.Profile;

import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicTreeUI;
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
    private final String STYLESHEET = "ooga/view/Resources/PlayerProfile.css";


    public ViewProfile(){
    }

    public void showProfile(){
        this.setPrefSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        this.setTop(setNameAndPhoto());
        this.setCenter(setStatsBox());
        getStylesheets().add(STYLESHEET);
    }
    private VBox setNameAndPhoto(){
        VBox nameAndPhoto = new VBox();
        if(myProfilePhoto == null) {
            myProfilePhoto = DEFAULT_PROFILE_PHOTO;}
        ImageView photoImage;
        try{
            photoImage = new ImageView(myProfilePhoto);
        }
        catch (IllegalArgumentException e){
            photoImage = new ImageView(DEFAULT_PROFILE_PHOTO);
        }

        nameAndPhoto.getChildren().add(photoImage);
        nameAndPhoto.getChildren().add(setNameText());
        nameAndPhoto.setOnDragEntered(e-> handleDroppedPhoto(e));
        nameAndPhoto.setOnDragDropped(e-> handleDroppedPhoto(e));
        nameAndPhoto.setOnMouseClicked(e->{ if(e.getClickCount() == 2){ handleChosenPhoto();}});
        return nameAndPhoto;
    }

    private void handleChosenPhoto(){
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        File fileChosen = fileChooser.showOpenDialog(stage);
        setNewProfilePhoto(fileChosen);
    }
    private void handleDroppedPhoto(DragEvent e){
        Dragboard db = e.getDragboard();
        List<File> files = db.getFiles();
        for(File file: files) {
            setNewProfilePhoto(file);
        }
    }

    private void setNewProfilePhoto(File filepath){
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(filepath);
            if(bufferedImage == null) return;
            String profilePhotoPath = "src/ooga/view/Resources/profilephotos/" + myName+ "profilephoto.jpg";
            File newFile = new File(profilePhotoPath);
            ImageIO.write(bufferedImage,"png",newFile);
            myProfilePhoto = "ooga/view/Resources/profilephotos/" + myName+ "profilephoto.jpg" ;
            setTop(setNameAndPhoto());
            } catch (IOException ex) {
            }
    }
    private void handleEnterPressed(TextArea textArea, HBox hBox, KeyEvent e){
        if(e.getCode() ==  KeyCode.ENTER){
            String areaText = textArea.getText();
            if(!textArea.getText().equals("\n")){
                myName = textArea.getText();
            }
            Text text = new Text(myName);
            hBox.getChildren().clear();
            hBox.getChildren().add(text);
        }
    }
    private void replaceWithTextArea(HBox hBox,TextArea textArea){
        hBox.getChildren().clear();
        hBox.getChildren().add(textArea);
    }
    private GridPane setNameText(){
        Text nameHeader = new Text("Name:");
        GridPane gridPane = new GridPane();
        Text name = new Text(myName);
        HBox hBox = new HBox();
        TextArea textArea = new TextArea();
        textArea.setPrefHeight(25);
        textArea.setPrefWidth(200);
        hBox.setOnMouseClicked(e->replaceWithTextArea(hBox,textArea));
        textArea.setOnKeyPressed(e->handleEnterPressed(textArea,hBox,e));
        hBox.getChildren().add(name);
        gridPane.setStyle(".grid-pane");
        gridPane.add(nameHeader,0,0);
        gridPane.add(hBox,1,0);
        return gridPane;
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
        gridPane.setStyle(".grid-pane");
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
