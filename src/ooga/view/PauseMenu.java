package ooga.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ooga.CollisionBehavior;
import ooga.data.ImageEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class PauseMenu extends BorderPane {
  private ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
  private final double WINDOW_HEIGHT = Double.parseDouble(myResources.getString("windowHeight"));
  private final double WINDOW_WIDTH = Double.parseDouble(myResources.getString("windowWidth"));

  private static final String STYLESHEET = "ooga/view/Resources/PauseMenu.css";
  private static final String SCROLL_PANE_STYLE = ".scroll-pane";
  private static final String TITLE_STYLE = ".title";
  private static final String ICON_STYLE = ".icon";
  private static final String PAUSE_MENU_TITLE_KEY = "pauseMenuTitle";
  private static final Color BACKGROUND_COLOR = Color.NAVY;
  private static final double SPACING = 30;
  private static final double ICON_SIZE = 50;
  private static final double TITLE_FONT_SIZE = 70;

  private BooleanProperty resumed = new SimpleBooleanProperty(true);
  private BooleanProperty quit = new SimpleBooleanProperty(false);
  private BooleanProperty save = new SimpleBooleanProperty(false);
  Map<BooleanProperty, String> buttonPropertiesAndNames = new HashMap<>(){{
    put(resumed, "Play");
    put(quit, "Quit");
    put(save, "Save");
  }};


  public PauseMenu(){
    this.setWidth(WINDOW_WIDTH);
    this.setHeight(WINDOW_HEIGHT);
    this.getStylesheets().add(STYLESHEET);
    this.setTop(makeMenuTitle());
    this.setLeft(setMenuItems());
    this.setCenterShape(true);
   // this.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR,null,null)));
  }

  public BooleanProperty resumedProperty() {
    return resumed;
  }

  public void setResumed(boolean resumed) {
    this.resumed.set(resumed);
  }

  public BooleanProperty quitProperty() {
    return quit;
  }

  public BooleanProperty saveProperty() {
    return save;
  }

  private ScrollPane setMenuItems(){
    VBox buttonVBox = new VBox(SPACING);
    buttonVBox.setAlignment(Pos.CENTER);
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setPrefWidth(WINDOW_WIDTH);
    scrollPane.setStyle(SCROLL_PANE_STYLE);
    scrollPane.setContent(buttonVBox);
    scrollPane.setFitToWidth(true);
    for(Map.Entry<BooleanProperty, String> buttonPropertyAndName : buttonPropertiesAndNames.entrySet()){
      buttonVBox.getChildren().add(makeButton(buttonPropertyAndName.getKey(), buttonPropertyAndName.getValue()));
    }
    return scrollPane;
  }
  private HBox makeMenuTitle(){
    HBox hbox = new HBox();
    hbox.setAlignment(Pos.CENTER);
    Text text = new Text(myResources.getString(PAUSE_MENU_TITLE_KEY));
    text.setStyle(TITLE_STYLE);
    text.setFont(Font.font(TITLE_FONT_SIZE));
    text.setFill(Color.WHITE);
    hbox.getChildren().add(text);
    return hbox;
  }

  private Button makeButton(BooleanProperty statusProperty, String text){
    Button button = new Button(text);
    ImageView icon = new ImageView(new Image(myResources.getString(text)));
    icon.setStyle(ICON_STYLE);
    icon.setFitHeight(ICON_SIZE);
    icon.setFitWidth(ICON_SIZE);
    button.setGraphic(icon);
    button.setOnAction(e -> statusProperty.setValue(!statusProperty.getValue()));
    return button;
  }

}
