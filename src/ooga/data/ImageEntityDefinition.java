package ooga.data;

import javafx.beans.property.*;
import ooga.CollisionBehavior;
import ooga.ControlsBehavior;
import ooga.Entity;
import ooga.MovementBehavior;

import java.util.List;
import java.util.Map;

public class ImageEntityDefinition {
    private DoubleProperty myHeight = new SimpleDoubleProperty();
    private DoubleProperty myWidth = new SimpleDoubleProperty();
    private List<MovementBehavior> myMovementBehaviors;
    private Map<String,List<CollisionBehavior>> myCollisionBehaviors;
    private Map<String,List<ControlsBehavior>> myControls;
    private String myName;

    public ImageEntityDefinition(String name, Double){

    }
    public ImageEntity createEntity(Double xpos, Double ypos){
        return null;
    }

}
