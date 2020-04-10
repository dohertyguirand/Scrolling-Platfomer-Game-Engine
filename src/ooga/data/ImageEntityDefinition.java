package ooga.data;

import javafx.beans.property.*;
import ooga.CollisionBehavior;
import ooga.ControlsBehavior;
import ooga.Entity;
import ooga.MovementBehavior;

import java.util.List;
import java.util.Map;

public class ImageEntityDefinition {
    private StringProperty imageLocation = null;
    private DoubleProperty height = new SimpleDoubleProperty();
    private DoubleProperty width = new SimpleDoubleProperty();
    private BooleanProperty activeInView = new SimpleBooleanProperty(true);
    private DoubleProperty myWidth;
    private DoubleProperty myHeight;
    private List<MovementBehavior> myMovementBehaviors;
    private Map<String,List<CollisionBehavior>> myCollisionBehaviors;
    private Map<String,List<ControlsBehavior>> myControls;
    private String myName;

    public ImageEntityDefinition(){

    }
    public ImageEntity createEntity(Double xpos, Double ypos){
        return null;
    }

}
