package ooga.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import ooga.data.Thumbnail;

import java.util.List;
import java.util.ResourceBundle;

public abstract class ScrollMenu {

    protected DataReader myDataReader;
    protected HBox myHBox;
    protected ScrollPane myScrollPane;
    protected Scene myScene;
    protected Pane myPane;
    protected Group myRoot;
    protected ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/Resources.config");
    protected final double WINDOW_HEIGHT = Double.parseDouble(myResources.getString("windowHeight"));
    protected final double WINDOW_WIDTH = Double.parseDouble(myResources.getString("windowWidth"));
    protected final double IMAGE_HEIGHT = Double.parseDouble(myResources.getString("gameImageHeight"));
    protected final double IMAGE_WIDTH = Double.parseDouble(myResources.getString("gameImageWidth"));
    protected final double IMAGE_RESIZE_FACTOR = Double.parseDouble(myResources.getString("gameImageResizeFactor"));
    protected String SCROLL_PANE_STYLE = myResources.getString("scrollpanecss");
    protected String HBOX_STYLE = myResources.getString("hboxcss");
    private final double SCROLLBAR_Y = Double.parseDouble(myResources.getString("scrollbarY"));
    private final double HBOX_SPACING = Double.parseDouble(myResources.getString("hboxspacing"));
    private final double HBOX_Y_LAYOUT = Double.parseDouble(myResources.getString("hboxy"));
    protected StringProperty optionSelected = new SimpleStringProperty();

    protected ScrollMenu(){
        myDataReader = new OogaDataReader();

        myRoot = new Group();
        myPane = new Pane();
        myPane.setPrefSize(WINDOW_WIDTH,WINDOW_HEIGHT);

        ImageView imgView = new ImageView(myResources.getString("menuBackgroundLocation"));
        imgView.setFitWidth(WINDOW_WIDTH);
        imgView.setFitHeight(WINDOW_HEIGHT);

        horizontalScroller();


        myPane.getChildren().addAll(imgView);
        myRoot.getChildren().addAll(myPane, myScrollPane);

        myScene = new Scene(myRoot);
        String SCROLLBAR_CSS_LOCATION = myResources.getString("scrollBarCSSLocation");
        myScene.getStylesheets().add(SCROLLBAR_CSS_LOCATION);
    }

    protected void addImages(List<Thumbnail> thumbnails){
        for (Thumbnail thumbnail : thumbnails) {
            ImageView optionImage = new ImageView(thumbnail.getImageFile());
            resizeImage(optionImage, 1);
            Button gameButton = new Button(null, optionImage);
            gameButton.setOnAction(e -> setOptionSelected(thumbnail.getTitle()));
            gameButton.setOnMouseEntered(e -> resizeImage(optionImage, IMAGE_RESIZE_FACTOR));
            gameButton.setOnMouseExited(e -> resizeImage(optionImage, 1));
            gameButton.setTooltip(new Tooltip(thumbnail.getDescription()));
            myHBox.getChildren().add(gameButton);
        }
    }


    protected void addImages() {
        List<Thumbnail> thumbnails = myDataReader.getThumbnails();
        addImages(thumbnails);
    }

    public void setOptionSelected(String optionSelected) {
        this.optionSelected.set(null);
        this.optionSelected.set(optionSelected);
    }

    public Scene getScene() {
        return myScene;
    }

    public StringProperty selectedProperty() {
        return optionSelected;
    }

    protected void resizeImage(ImageView image, double resizeFactor) {
        image.setFitWidth(IMAGE_WIDTH *resizeFactor);
        image.setFitHeight(IMAGE_HEIGHT *resizeFactor);
    }

    protected void horizontalScroller() {
        myHBox = new HBox();
        myHBox.setStyle(HBOX_STYLE);
        myHBox.setLayoutY(HBOX_Y_LAYOUT);
        myHBox.setSpacing(HBOX_SPACING);
        myScrollPane = new ScrollPane();
        myScrollPane.setStyle(SCROLL_PANE_STYLE);
        myScrollPane.setLayoutY(SCROLLBAR_Y);
        myScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        myScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        myScrollPane.setPrefWidth(WINDOW_WIDTH);
        myScrollPane.setContent(myHBox);
    }
}
