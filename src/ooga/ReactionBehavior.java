package ooga;

import java.util.ResourceBundle;

public class ReactionBehavior {
    private ResourceBundle reactionMappings;

    public ReactionBehavior(ResourceBundle resourceBundle){
       reactionMappings = resourceBundle;
    }
    /**
     * Performs the specific Control and Collision behavior implementation.
     * @param ControlKey - String that references to the Specific Control
     * @param CollisionEntity - String the type of Entity that is part of the Collision
     */
    public String getReaction(String ControlKey, String CollisionEntity){
        String key = CollisionEntity+ControlKey;
        String reaction = reactionMappings.getString(key);
        return reaction;
        };

}
