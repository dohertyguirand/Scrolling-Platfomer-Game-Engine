package ooga.data.entities;

import ooga.game.behaviors.ConditionalBehavior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author caryshindell, sam thompson, braeden ward
 * This is the outline of an entity that can be defined in the data files, to make it faster to instantiate specific
 * instances. Note that it contains behaviors and does not contain position.
 * Assumptions: we are essentially assuming that all image entities with the same name/type have the same behaviors
 * Note: We do not have text entity definitions, only image entity definitions.
 * Dependencies: ConditionalBehavior interface
 * Example: mario has a set image, width, height, behaviors, and camera stationary status.
 */
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

    public double getHeight() {
        return myHeight;
    }

    public double getWidth() {
        return myWidth;
    }

    public void setVariables(Map<String, String> entityVariables) { myVariables = entityVariables; }

    public void setStationary(boolean isStationary){stationary = isStationary;}

    public boolean getStationary() {
        return stationary;
    }
}
