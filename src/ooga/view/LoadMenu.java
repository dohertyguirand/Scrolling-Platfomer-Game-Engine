package ooga.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ooga.data.GameDataReaderExternal;

import java.util.ResourceBundle;

public class LoadMenu {
    private final ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
    @SuppressWarnings("FieldCanBeLocal")
    private final double WINDOW_HEIGHT = Double.parseDouble(myResources.getString("windowHeight"));
    private final double WINDOW_WIDTH = Double.parseDouble(myResources.getString("windowWidth"));
    private static final String STYLESHEET = "ooga/view/Resources/PauseMenu.css";
    private final String SCROLL_PANE_STYLE = myResources.getString("scrollpanecss");
    private final String TITLE_STYLE = myResources.getString("titlecss");
    private  String ICON_STYLE = myResources.getString("iconcss");
    private static final String PAUSE_MENU_TITLE_KEY = "pauseMenuTitle";
    private static final double SPACING = 30;
    private static final double ICON_SIZE = 50;
    private static final double TITLE_FONT_SIZE = 70;
    private final String gameName;
    private final String profileName;
    private final GameDataReaderExternal myDataReader;
    private final BorderPane root = new BorderPane();
    private final Scene myScene = new Scene(root);
    private final StringProperty dateSelected = new SimpleStringProperty();

    public LoadMenu(String gameName, String profileName, GameDataReaderExternal reader, Node backButton){
        this.gameName = gameName;
        this.profileName = profileName;
        myDataReader = reader;
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
        //TODO: refactor this
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
