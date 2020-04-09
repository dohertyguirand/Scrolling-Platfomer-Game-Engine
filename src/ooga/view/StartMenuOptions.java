/*package ooga.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import java.util.HashMap;
import java.util.Map;

public class StartMenuOptions {

    private static final Paint COLORBUTTON = Color.BLACK;
    private static final double Button_Space = 30;
    private static final double MARGIN = 50;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private BooleanProperty Display = new SimpleBooleanProperty(false);
    private BooleanProperty Audio = new SimpleBooleanProperty(false);
    private BooleanProperty Reset = new SimpleBooleanProperty(false);
    private BooleanProperty GoBack  = new SimpleBooleanProperty(false);
    Map<BooleanProperty, String> buttonPropertiesAndNames = new HashMap<>(){{
        put(Display, "Display");
        put(Audio, "Audio");
        put(Reset, "Reset");
        put(GoBack, "Go Back");
    }};
    private Slider myVolumeSlider;
    private static final int SLIDERMINNUM = 0;
    private static final int SLIDERMAXNUM = 10;
    private static final int SLIDERUNIT = 5;

    private Button myButton;


    private Button makeButton(BooleanProperty statusProperty, String text){
        myButton = new Button(text);
        myButton.setTextFill(COLORBUTTON);
        myButton.setOnAction(e -> statusProperty.setValue(!statusProperty.getValue()));
        return myButton;
    }

    private Button getButton () {
        return myButton;
    }

    public StartMenuOptions(){
        VBox buttonVBox = new VBox(Button_Space);
        for(Map.Entry<BooleanProperty, String> buttonPropertyAndName : buttonPropertiesAndNames.entrySet()){
            buttonVBox.getChildren().add(makeButton(buttonPropertyAndName.getKey(), buttonPropertyAndName.getValue()));
            myButton.setOnMouseEntered(e -> myButton.resize(myButton.getWidth()*1.25, myButton.getHeight()*1.25));
            myButton.setOnMouseExited(e -> myButton.resize(myButton.getWidth()*1.5, myButton.getHeight()*1.5));
        }
    }

    public Slider volumeControl() {
        myVolumeSlider = new Slider();
        myVolumeSlider.setMin(SLIDERMINNUM);
        myVolumeSlider.setMax(SLIDERMAXNUM);
        myVolumeSlider.setValue(1);
        myVolumeSlider.setShowTickLabels(true);
        myVolumeSlider.setShowTickMarks(true);
        myVolumeSlider.setMajorTickUnit(SLIDERUNIT);
        myVolumeSlider.setBlockIncrement(SLIDERUNIT);

        myVolumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                animation.setRate((Double) new_val);
            }
        });
        return myVolumeSlider;
    }

    public BooleanProperty BackProperty() {
        return GoBack;
    }

    public BooleanProperty DisplayProperty() {
        return Display;
    }
    public BooleanProperty AudioProperty() {
        return Audio;
    }


}*/
