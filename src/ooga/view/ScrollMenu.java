package ooga.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import ooga.data.GameDataReaderExternal;
import ooga.data.ProfileReaderExternal;
import ooga.data.xmlReaders.XMLGameDataReader;
import ooga.data.xmlReaders.XMLProfileReader;

import java.util.ResourceBundle;

public abstract class ScrollMenu {

    protected final ProfileReaderExternal myProfileReader;
    protected final GameDataReaderExternal myGameDataReader;
    protected HBox myHBox;
    protected final Pane myPane;

    private final Scene myScene;
    private final ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
    @SuppressWarnings("FieldCanBeLocal")
    private final double WINDOW_HEIGHT = Double.parseDouble(myResources.getString("windowHeight"));
    private final double WINDOW_WIDTH = Double.parseDouble(myResources.getString("windowWidth"));
    private final double IMAGE_HEIGHT = Double.parseDouble(myResources.getString("gameImageHeight"));
    private final double IMAGE_WIDTH = Double.parseDouble(myResources.getString("gameImageWidth"));
    private final double IMAGE_RESIZE_FACTOR = Double.parseDouble(myResources.getString("gameImageResizeFactor"));
    private final String SCROLL_PANE_STYLE = myResources.getString("scrollpanecss");
    private final String HBOX_STYLE = myResources.getString("hboxcss");
    private final double SCROLLBAR_Y = Double.parseDouble(myResources.getString("scrollbarY"));
    private final double HBOX_SPACING = Double.parseDouble(myResources.getString("hboxspacing"));
    private final double HBOX_Y_LAYOUT = Double.parseDouble(myResources.getString("hboxy"));


    protected ScrollMenu(){
        myProfileReader = new XMLProfileReader() {};
        myGameDataReader = new XMLGameDataReader() {};
        Group myRoot = new Group();
        myPane = new Pane();
        myPane.setPrefSize(WINDOW_WIDTH,WINDOW_HEIGHT);

        ImageView imgView = new ImageView(myResources.getString("menuBackgroundLocation"));
        imgView.setFitWidth(WINDOW_WIDTH);
        imgView.setFitHeight(WINDOW_HEIGHT);

       // horizontalScroller();


        myPane.getChildren().addAll(imgView);
        myRoot.getChildren().addAll(myPane, horizontalScroller());

        myScene = new Scene(myRoot);
        String SCROLLBAR_CSS_LOCATION = myResources.getString("scrollBarCSSLocation");
        myScene.getStylesheets().add(SCROLLBAR_CSS_LOCATION);
    }




    public Scene getScene() {
        return myScene;
    }


    protected void resizeImage(ImageView image, double resizeFactor) {
        image.setFitWidth(IMAGE_WIDTH *resizeFactor);
        image.setFitHeight(IMAGE_HEIGHT *resizeFactor);
    }

    protected ScrollPane horizontalScroller() {
        myHBox = new HBox();
        myHBox.setStyle(HBOX_STYLE);
        myHBox.setLayoutY(HBOX_Y_LAYOUT);
        myHBox.setSpacing(HBOX_SPACING);
       ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle(SCROLL_PANE_STYLE);
        scrollPane.setLayoutY(SCROLLBAR_Y);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefWidth(WINDOW_WIDTH);
        scrollPane.setContent(myHBox);
        return scrollPane;
    }

    protected Button makeButton(ImageView image, String description){
        resizeImage(image,1);
        Button button = new Button(null,image);
        button.setOnMouseEntered(e -> resizeImage(image, IMAGE_RESIZE_FACTOR));
        button.setOnMouseExited(e -> resizeImage(image, 1));
        button.setTooltip(new Tooltip(description));
        return button;
    }
}
