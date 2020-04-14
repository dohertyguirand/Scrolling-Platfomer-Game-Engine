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
    private String myImageLocation;
    private List<MovementBehavior> myMovementBehaviors;
    private Map<String,List<CollisionBehavior>> myCollisionBehaviors;
    private Map<String,List<ControlsBehavior>> myControls;
    private String myName;

    public ImageEntityDefinition(String name, Double height, Double width, String imageLocation, List<MovementBehavior> movementBehaviors,
                                 Map<String,List<CollisionBehavior>> collisionBehaviors, Map<String,List<ControlsBehavior>> controls){
        myName = name;
        myHeight.setValue(height);
        myWidth.setValue(width);
        myImageLocation = imageLocation;
        myMovementBehaviors = movementBehaviors;
        myCollisionBehaviors = collisionBehaviors;
        myControls = controls;
    }

    public ImageEntity makeInstanceAt(Double xpos, Double ypos){
        ImageEntity newEntity = new ImageEntity(myName, myImageLocation);
        //TODO: uncomment the below methods calls when the methods are added to ImageEntity in this branch
        //newEntity.setHeight(myHeight);
        //newEntity.setWidth(myWidth);
        newEntity.setCollisionBehaviors(myCollisionBehaviors);
        newEntity.setMovementBehaviors(myMovementBehaviors);
        newEntity.setControlsBehaviors(myControls);
        newEntity.setPosition(List.of(xpos, ypos));
        return newEntity;
    }

}
