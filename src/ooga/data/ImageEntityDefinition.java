package ooga.data;

import ooga.game.behaviors.ConditionalBehavior;

import java.util.List;

public class ImageEntityDefinition {
    private double myHeight;
    private double myWidth;
    private String myImageLocation;
    private List<ConditionalBehavior> myBehaviors;
    private String myName;

    public ImageEntityDefinition(String name, double height, double width, String imageLocation, List<ConditionalBehavior> behaviors){
        myName = name;
        myHeight = height;
        myWidth = width;
        myImageLocation = imageLocation;
        myBehaviors = behaviors;
    }

    public ImageEntity makeInstanceAt(Double xpos, Double ypos){
        ImageEntity newEntity = new ImageEntity(myName, myImageLocation, xpos, ypos, myWidth, myHeight);
        newEntity.setConditionalBehaviors(myBehaviors);
        return newEntity;
    }

    @Override
    public String toString() {
        return myName + ": (" + myHeight + " x " + myWidth + ")";
    }

    public double getMyHeight() {
        return myHeight;
    }

    public double getMyWidth() {
        return myWidth;
    }
}
