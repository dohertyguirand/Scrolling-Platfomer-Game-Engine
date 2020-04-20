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
    private ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
    private final double WINDOW_HEIGHT = Double.parseDouble(myResources.getString("windowHeight"));
    private final double WINDOW_WIDTH = Double.parseDouble(myResources.getString("windowWidth"));
    private final String STYLESHEET = "ooga/view/Resources/PlayerProfile.css";
    private ImageView myProfilePhoto;
    private ImageView myViewPhoto;

    public ViewProfile(String name, String imagePath){
        myName = name;
        myDefaultImagePath = "ooga/view/Resources/profilephotos/defaultphoto.jpg";
        setImageView(imagePath);
        myHighestScores = new HashMap<>();
    }
    public ViewProfile(OggaProfile profile){
        setImageView(profile.getProfilePhotoPath());
        myName = profile.getProfileName();
        myHighestScores = profile.getStats();
    }
    public ViewProfile(){
      this("Testing","ooga/view/Resources/profilephotos/defaultphoto.jpg");
    }

    public void setImageView(String photoPath){
        try{
            myProfilePhoto = new ImageView(photoPath);
            myViewPhoto = new ImageView(photoPath);
        }
        catch (IllegalArgumentException | NullPointerException e){
            myProfilePhoto = new ImageView(myDefaultImagePath);
            myViewPhoto = new ImageView(myDefaultImagePath);
        }
    }

    public Pane getPane(){
        BorderPane pane = new BorderPane();
        pane.setPrefSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        pane.setTop(setNameAndPhoto());
        pane.setCenter(setStatsBox());
        pane.getStylesheets().add(STYLESHEET);
        return pane;
    }

    private VBox setNameAndPhoto(){
        VBox nameAndPhoto = new VBox();
        nameAndPhoto.getChildren().add(myViewPhoto);
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
            //myPane.setTop(setNameAndPhoto());
            } catch (IOException | NullPointerException | IllegalArgumentException ex) {
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
        //myPane.setOnMouseClicked(m->handleMouseClicked(m, textArea,hBox));
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
        if(myHighestScores == null) {
           setStats(new HashMap<>(){{
                put("SuperMario", 100);
                put("Dino", 3500);
                put("FireBoy and Water Girl", 3000);
            }});
        }
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


    public ImageView getProfilePhoto(){return myProfilePhoto;}

}
