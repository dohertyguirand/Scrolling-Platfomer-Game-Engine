package ooga.game.behaviors.asyncbehavior;

import java.util.Map;
import ooga.Entity;
import ooga.data.ImageEntity;
import ooga.game.GameInternal;

import java.util.List;


public class CollisionImage extends DirectionlessCollision {
    String newImageFileName = "";

    public CollisionImage(List<String> args){
        if(args.size() >= 1){
            newImageFileName = args.get(0);
        }
    }

    /**
     * Handles reaction to controls. Requires the ControlsBehavior to have a reference to the
     * instance that uses it in order to have an effect on that instance.
     * @param elapsedTime
     * @param subject The entity that owns this controls behavior. This is the entity that should
     *                be modified.
     * @param variables
     * @param game
     */
    @Override
    public void doCollision(Entity subject, Entity collidingEntity, double elapsedTime,
                            Map<String, Double> variables, GameInternal game) {
        if(subject instanceof ImageEntity){
            ImageEntity imageEntity = (ImageEntity)subject;
            imageEntity.setImageLocation("file:data/games-library/" + newImageFileName);
        }
    }
}
