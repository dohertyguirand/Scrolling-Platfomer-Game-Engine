package ooga.view;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;


public class ViewProfile extends OggaProfile {
    private static final String DEFAULT_PROFILE_PHOTO = "ooga/view/Resources/profilephotos/defaultphoto.jpg";
    private ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
    private final double WINDOW_HEIGHT = Double.parseDouble(myResources.getString("windowHeight"));
    private final double WINDOW_WIDTH = Double.parseDouble(myResources.getString("windowWidth"));
    private final String STYLESHEET = "ooga/view/Resources/PlayerProfile.css";
    private BorderPane myPane = new BorderPane();


    public ViewProfile(){
        myHighestScores = new HashMap<>();
        myName = "testing";
    }

    public void showProfile(){
        myPane.setPrefSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        myPane.setTop(setNameAndPhoto());
        myPane.setCenter(setStatsBox());
        myPane.getStylesheets().add(STYLESHEET);
    }
    private VBox setNameAndPhoto(){
        VBox nameAndPhoto = new VBox();
        if(myProfilePhotoPath == null) {
            myProfilePhotoPath = DEFAULT_PROFILE_PHOTO;}
        ImageView photoImage;
        try{
            photoImage = new ImageView(myProfilePhotoPath);
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
            myProfilePhotoPath = "ooga/view/Resources/profilephotos/" + myName+ "profilephoto.jpg" ;
            myPane.setTop(setNameAndPhoto());
            } catch (IOException ex) {
            }
    }
    private void handleExitPressed(TextArea textArea, HBox hBox, KeyEvent e){
        if(e.getCode() ==  KeyCode.ENTER){
            hideTextArea(textArea,hBox);
        }
    }

    private void hideTextArea(TextArea textArea,HBox hBox){
        if(!textArea.getText().equals("\n")){
            myName = textArea.getText();
        }
        Text text = new Text(myName);
        hBox.getChildren().clear();
        hBox.getChildren().add(text);
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
        textArea.setOnKeyPressed(e->handleExitPressed(textArea,hBox,e));
        myPane.setOnMouseClicked(m->handleMouseClicked(m, textArea,hBox));
        hBox.getChildren().add(name);
        gridPane.setStyle(".grid-pane");
        gridPane.add(nameHeader,0,0);
        gridPane.add(hBox,1,0);
        return gridPane;
    }

    private void handleMouseClicked(MouseEvent m,TextArea textArea,HBox hBox) {
//        if(hBox.getChildren().contains(textArea) && !textArea.getBoundsInParent().contains(m.getX(),m.getY())){
//            hideTextArea(textArea,hBox);
//        }
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



    public Pane getPane(){
        return myPane;
    }
}
