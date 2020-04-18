package ooga.data;

import ooga.game.behaviors.CollisionEffect;
import ooga.game.behaviors.ConditionalBehavior;
import ooga.game.behaviors.NonCollisionEffect;

import java.util.List;
import java.util.Map;

public class ImageEntityDefinition {
    private double myHeight;
    private double myWidth;
    private String myImageLocation;
    private List<NonCollisionEffect> myFrameBehaviors;
    private Map<String,List<CollisionEffect>> myCollisionBehaviors;
    private Map<String,List<NonCollisionEffect>> myControls;
    private List<ConditionalBehavior> myConditionalBehaviors;
    private String myName;

    public ImageEntityDefinition(String name, double height, double width, String imageLocation, List<NonCollisionEffect> frameBehaviors,
                                 Map<String,List<CollisionEffect>> collisionBehaviors,
                                 Map<String,List<NonCollisionEffect>> controls, List<ConditionalBehavior> conditionalBehaviors){
        myName = name;
        myHeight = height;
        myWidth = width;
        myImageLocation = imageLocation;
        myFrameBehaviors = frameBehaviors;
        myCollisionBehaviors = collisionBehaviors;
        myControls = controls;
        myConditionalBehaviors = conditionalBehaviors;
    }

    public ImageEntity makeInstanceAt(Double xpos, Double ypos){
        ImageEntity newEntity = new ImageEntity(myName, myImageLocation, xpos, ypos, myWidth, myHeight);
        newEntity.setCollisionBehaviors(myCollisionBehaviors);
        newEntity.setMovementBehaviors(myFrameBehaviors);
        newEntity.setControlsBehaviors(myControls);
        newEntity.setConditionalBehaviors(myConditionalBehaviors);
        return newEntity;
    }

    @Override
    public String toString() {
        return myName + ": (" + myHeight + " x " + myWidth + ")";
    }
}
