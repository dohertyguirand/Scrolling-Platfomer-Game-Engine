package ooga.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.*;


public class PauseMenu extends OptionMenu {
  private final ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
  @SuppressWarnings("FieldCanBeLocal")



  private final String ICON_STYLE = myResources.getString("iconcss");
  private static final String PAUSE_MENU_TITLE = "Game Paused";
  private static final double ICON_SIZE = 50;

  private final BooleanProperty resumed = new SimpleBooleanProperty(true);
  private final BooleanProperty quit = new SimpleBooleanProperty(false);
  private final BooleanProperty save = new SimpleBooleanProperty(false);
  final Map<BooleanProperty, String> buttonPropertiesAndNames = new HashMap<>(){{
    put(resumed, "Play");
    put(quit, "Quit");
    put(save, "Save");
  }};


  public PauseMenu(){
    super(PAUSE_MENU_TITLE);
    this.setLeft(setMenuItems(createButtons()));
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
    ImageView icon = new ImageView(new Image(myResources.getString(text)));
    icon.setStyle(ICON_STYLE);
    icon.setFitHeight(ICON_SIZE);
    icon.setFitWidth(ICON_SIZE);
    button.setGraphic(icon);
    button.setOnAction(e -> statusProperty.setValue(!statusProperty.getValue()));
    return button;
  }


  private List<Node> createButtons() {
    List<Node> list = new ArrayList<>();
    for(Map.Entry<BooleanProperty, String> buttonPropertyAndName : buttonPropertiesAndNames.entrySet()){
      list.add(makeButton(buttonPropertyAndName.getKey(), buttonPropertyAndName.getValue()));
    }
    return list;
  }
}
