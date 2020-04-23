package ooga.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ooga.data.DataReader;

import java.util.Map;
import java.util.ResourceBundle;

public class LoadMenu {
    private ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
    private final double WINDOW_HEIGHT = Double.parseDouble(myResources.getString("windowHeight"));
    private final double WINDOW_WIDTH = Double.parseDouble(myResources.getString("windowWidth"));
    private static final String STYLESHEET = "ooga/view/Resources/PauseMenu.css";
    private  String SCROLL_PANE_STYLE = myResources.getString("scrollpanecss");
    private  String TITLE_STYLE = myResources.getString("titlecss");
    private  String ICON_STYLE = myResources.getString("iconcss");
    private static final String PAUSE_MENU_TITLE_KEY = "pauseMenuTitle";
    private static final double SPACING = 30;
    private static final double ICON_SIZE = 50;
    private static final double TITLE_FONT_SIZE = 70;
    private String gameName;
    private String profileName;
    private DataReader dataReader;
    private BorderPane root = new BorderPane();
    private Scene myScene = new Scene(root);
    private StringProperty dateSelected = new SimpleStringProperty();

    public LoadMenu(String gamename, String profilename, DataReader reader, Node backButton){
        gameName = gamename;
        profileName = profilename;
        dataReader = reader;
        root.getStylesheets().add(STYLESHEET);
        ImageView imgView = new ImageView(myResources.getString("menuBackgroundLocation"));
        imgView.setFitWidth(WINDOW_WIDTH);
        imgView.setFitHeight(WINDOW_HEIGHT);
        root.getChildren().addAll(imgView);
        root.setTop(makeMenuTitle());
        root.setLeft(setMenuItems(backButton));
        root.setCenterShape(true);
    }

    public StringProperty getDateSelected() {
        return dateSelected;
    }
    public Scene getScene() {return myScene;}
    private Button makeButton(String date, String text){
        Button button = new Button(text);
       // ImageView icon = new ImageView();
       // icon.setStyle(ICON_STYLE);
       // icon.setFitHeight(ICON_SIZE);
       // icon.setFitWidth(ICON_SIZE);
       // button.setGraphic(icon);
       // button.setOnAction(e);
        return button;
    }

    private Node setMenuItems(Node backButton) {
        VBox buttonVBox = new VBox(SPACING);
        buttonVBox.setAlignment(Pos.CENTER);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefWidth(WINDOW_WIDTH);
        scrollPane.setStyle(SCROLL_PANE_STYLE);
        scrollPane.setContent(buttonVBox);
        scrollPane.setFitToWidth(true);
        Button newGame = makeButton("", "New Game");
        newGame.setOnAction(e-> setDateSelected(newGame.getText()));
        buttonVBox.getChildren().add(newGame);
//        for(){
//            buttonVBox.getChildren().add(makeButton(buttonPropertyAndName.getKey(), buttonPropertyAndName.getValue()));
//        }
        buttonVBox.getChildren().add(backButton);
        return scrollPane;

    }

    public void setDateSelected(String optionSelected) {
        this.dateSelected.set(null);
        this.dateSelected.set(optionSelected);
    }

    private Text makeMenuTitle() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        Text text = new Text("Saved Game or New Game");
        text.setStyle(TITLE_STYLE);
        text.setFont(Font.font(TITLE_FONT_SIZE));
        text.setFill(Color.WHITE);
        hbox.getChildren().add(text);
        return text;
    }

}
