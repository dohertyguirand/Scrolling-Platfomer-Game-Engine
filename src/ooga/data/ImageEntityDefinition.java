package ooga.data;

import ooga.game.behaviors.CollisionBehavior;
import ooga.game.behaviors.ControlsBehavior;
import ooga.game.behaviors.FrameBehavior;

import java.util.List;
import java.util.Map;

public class ImageEntityDefinition {
    private double myHeight;
    private double myWidth;
    private String myImageLocation;
    private List<FrameBehavior> myFrameBehaviors;
    private Map<String,List<CollisionBehavior>> myCollisionBehaviors;
    private Map<String,List<ControlsBehavior>> myControls;
    private String myName;

    public ImageEntityDefinition(String name, double height, double width, String imageLocation, List<FrameBehavior> frameBehaviors,
                                 Map<String,List<CollisionBehavior>> collisionBehaviors, Map<String,List<ControlsBehavior>> controls){
        myName = name;
        myHeight = height;
        myWidth = width;
        myImageLocation = imageLocation;
        myFrameBehaviors = frameBehaviors;
        myCollisionBehaviors = collisionBehaviors;
        myControls = controls;
    }

    public ImageEntity makeInstanceAt(Double xpos, Double ypos){
        ImageEntity newEntity = new ImageEntity(myName, myImageLocation, xpos, ypos, myWidth, myHeight);
        newEntity.setCollisionBehaviors(myCollisionBehaviors);
        newEntity.setMovementBehaviors(myFrameBehaviors);
        newEntity.setControlsBehaviors(myControls);
        return newEntity;
    }

    @Override
    public String toString() {
        return myName + ": (" + myHeight + " x " + myWidth + ")";
    }
}
