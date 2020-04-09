package ooga.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import ooga.CollisionBehavior;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class PauseMenu extends Pane {
  private ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
  private final double WINDOW_HEIGHT = Double.parseDouble(myResources.getString("windowHeight"));
  private final double WINDOW_WIDTH = Double.parseDouble(myResources.getString("windowWidth"));
  private static final Color BACKGROUND_COLOR = Color.NAVY;
  private static final Paint BUTTON_COLOR = Color.RED;
  private static final double SPACING = 30;
  private static final double MARGIN = 50;
  private BooleanProperty resumed = new SimpleBooleanProperty(true);
  private BooleanProperty quit = new SimpleBooleanProperty(false);
  private BooleanProperty save = new SimpleBooleanProperty(false);
  Map<BooleanProperty, String> buttonPropertiesAndNames = new HashMap<>(){{
    put(resumed, "Resume");
    put(quit, "Quit");
    put(save, "Save");
  }};
  private ScrollPane myScrollPane;

  public PauseMenu(){
    this.getStylesheets().add("ooga/view/Resources/PauseMenu.css");
    /**
     * Hey this is braeden, just noting here that I commented out those three lines below because they were causing errors
     * [thumbs up emoji]
     */
    //Rectangle background = new Rectangle(ViewerGame.WINDOW_WIDTH, ViewerGame.WINDOW_HEIGHT, BACKGROUND_COLOR);
    //this.getChildren().add(background);
    VBox buttonVBox = new VBox(SPACING);
    for(Map.Entry<BooleanProperty, String> buttonPropertyAndName : buttonPropertiesAndNames.entrySet()){
      buttonVBox.getChildren().add(makeButton(buttonPropertyAndName.getKey(), buttonPropertyAndName.getValue()));
    }
    //VBox.setMargin(buttonVBox, new Insets(MARGIN, 0, 0, ViewerGame.WINDOW_WIDTH/2));
    this.getChildren().add(buttonVBox);
    this.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR,null,null)));
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

  private Button makeButton(BooleanProperty statusProperty, String text){
    Button button = new Button(text);
    button.setOnAction(e -> statusProperty.setValue(!statusProperty.getValue()));
    button.setTextFill(BUTTON_COLOR);
    return button;
  }
}
