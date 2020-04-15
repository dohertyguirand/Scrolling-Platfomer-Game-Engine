package ooga.data;

import javafx.beans.property.*;
import ooga.CollisionBehavior;
import ooga.ControlsBehavior;
import ooga.Entity;
import ooga.MovementBehavior;

import java.util.List;
import java.util.Map;

public class ImageEntityDefinition {
    private double myHeight;
    private double myWidth;
    private String myImageLocation;
    private List<MovementBehavior> myMovementBehaviors;
    private Map<String,List<CollisionBehavior>> myCollisionBehaviors;
    private Map<String,List<ControlsBehavior>> myControls;
    private String myName;

    public ImageEntityDefinition(String name, double height, double width, String imageLocation, List<MovementBehavior> movementBehaviors,
                                 Map<String,List<CollisionBehavior>> collisionBehaviors, Map<String,List<ControlsBehavior>> controls){
        myName = name;
        myHeight = height;
        myWidth = width;
        myImageLocation = imageLocation;
        myMovementBehaviors = movementBehaviors;
        myCollisionBehaviors = collisionBehaviors;
        myControls = controls;
    }

    public ImageEntity makeInstanceAt(Double xpos, Double ypos){
        ImageEntity newEntity = new ImageEntity(myName, myImageLocation, xpos, ypos, myWidth, myHeight);
        newEntity.setCollisionBehaviors(myCollisionBehaviors);
        newEntity.setMovementBehaviors(myMovementBehaviors);
        newEntity.setControlsBehaviors(myControls);
        return newEntity;
    }

}
