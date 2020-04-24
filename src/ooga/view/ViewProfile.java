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
import java.util.Map;
import java.util.ResourceBundle;


public class ViewProfile{
    private final ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
    private final double WINDOW_HEIGHT = Double.parseDouble(myResources.getString("windowHeight"));
    private final double WINDOW_WIDTH = Double.parseDouble(myResources.getString("windowWidth"));
    private static final String STYLESHEET = "ooga/view/Resources/PlayerProfile.css";
    private static final String DEFAULT_IMAGE_PATH = "ooga/view/Resources/profilephotos/defaultphoto.jpg";
    private String profilePhotoPath;
    private String profileName;
    private Map<String, Integer> myStats;



    public ViewProfile(String name, String imagePath){
        profileName = name;
        profilePhotoPath = imagePath;
        verifyPhotoPath();
        myStats = new HashMap<>();
    }
    public ViewProfile(String name, String imagePath, Map<String, Integer> stats){
        this(name,imagePath);
        myStats = stats;
    }

    @Deprecated
    public ViewProfile(){
      this("Testing","ooga/view/Resources/profilephotos/defaultphoto.jpg");
    }

    private void verifyPhotoPath(){
        try{
            ImageView example = new ImageView(profilePhotoPath);
        }
        catch (IllegalArgumentException | NullPointerException e){
            profilePhotoPath = DEFAULT_IMAGE_PATH;
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
        nameAndPhoto.getChildren().add(new ImageView(profilePhotoPath));
        nameAndPhoto.getChildren().add(setNameText());
        nameAndPhoto.setOnDragEntered(this::handleDroppedPhoto);
        nameAndPhoto.setOnDragDropped(this::handleDroppedPhoto);
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
            String profilePhotoPath = "src/ooga/view/Resources/profilephotos/" + profileName+ "profilephoto.jpg";
            File newFile = new File(profilePhotoPath);
            ImageIO.write(bufferedImage,"png",newFile);
            profilePhotoPath = "ooga/view/Resources/profilephotos/" + profileName+ "profilephoto.jpg" ;
            //myPane.setTop(setNameAndPhoto());
            } catch (IOException | NullPointerException | IllegalArgumentException ignored) {
            }
    }
    private void handleExitPressed(TextArea textArea, HBox hBox, KeyEvent e){
        if(e.getCode() ==  KeyCode.ENTER){
            hideTextArea(textArea,hBox);
        }
    }

    private void hideTextArea(TextArea textArea,HBox hBox){
        if(!textArea.getText().equals("\n")){
            profileName = textArea.getText();
        }
        Text text = new Text(profileName);
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
        Text name = new Text(profileName);
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
        if(myStats == null) {
           myStats = (new HashMap<>(){{
                put("SuperMario", 100);
                put("Dino", 3500);
                put("FireBoy and Water Girl", 3000);
            }});
        }
        for(String game: myStats.keySet()){
            Integer stat = myStats.get(game);
            Text gameName = new Text(game);
            Text statText = new Text(stat.toString());
            gridPane.add(gameName,0,row);
            gridPane.add(statText,1,row);
            row++;
        }
        gridPane.setStyle(".grid-pane");
        return gridPane;
    }

    public String getProfileName(){return profileName;}
    public String getProfilePath(){return profilePhotoPath;}

}
