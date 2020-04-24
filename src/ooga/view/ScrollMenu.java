package ooga.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import ooga.data.DataReader;
import ooga.data.OogaDataReader;

import java.util.ResourceBundle;

public abstract class ScrollMenu extends Pane{

    private final ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
    private final double WINDOW_WIDTH = Double.parseDouble(myResources.getString("windowWidth"));
    private final double IMAGE_HEIGHT = Double.parseDouble(myResources.getString("gameImageHeight"));
    private final double IMAGE_WIDTH = Double.parseDouble(myResources.getString("gameImageWidth"));
    private final double IMAGE_RESIZE_FACTOR = Double.parseDouble(myResources.getString("gameImageResizeFactor"));
    private final String SCROLL_PANE_STYLE = myResources.getString("scrollpanecss");
    private final String HBOX_STYLE = myResources.getString("hboxcss");
    private final double SCROLLBAR_Y = Double.parseDouble(myResources.getString("scrollbarY"));
    private final double HBOX_SPACING = Double.parseDouble(myResources.getString("hboxspacing"));
    private final double HBOX_Y_LAYOUT = Double.parseDouble(myResources.getString("hboxy"));
    protected final DataReader myDataReader;
    protected HBox myHBox;


    protected ScrollMenu(){
        myDataReader = new OogaDataReader();
        double windowHeight = Double.parseDouble(myResources.getString("windowHeight"));
        this.setWidth(WINDOW_WIDTH);
        this.setHeight(windowHeight);
//        ImageView imgView = new ImageView(myResources.getString("menuBackgroundLocation"));
//        imgView.setFitWidth(WINDOW_WIDTH);
//        imgView.setFitHeight(windowHeight);
       // this.getChildren().addAll(imgView);
        this.getChildren().addAll( horizontalScroller());
        String SCROLLBAR_CSS_LOCATION = myResources.getString("scrollBarCSSLocation");
        this.getStylesheets().add(SCROLLBAR_CSS_LOCATION);
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
