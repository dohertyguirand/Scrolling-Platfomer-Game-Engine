package ooga.data;

import ooga.game.behaviors.ConditionalBehavior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageEntityDefinition {
    private final double myHeight;
    private final double myWidth;
    private final String myImageLocation;
    private final List<ConditionalBehavior> myBehaviors;
    private final String myName;
    private Map<String, String> myVariables = new HashMap<>();
    private boolean stationary = false;

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
        newEntity.setVariables(myVariables);
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

    public void setVariables(Map<String, String> entityVariables) { myVariables = entityVariables; }

    public void setStationary(boolean isStationary){stationary = isStationary;}
}
