package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

/**
 * @author sam thompson
 */
@SuppressWarnings("unused")
public class DestroySelfEffect extends TimeDelayedEffect {


    public DestroySelfEffect(List<String> args) {
        super(args);
    }

    @Override
    public void processArgs(List<String> args) {
        //has no arguments.
    }

    /**
     * Performs the effect
     * @param subject     The entity that owns this. This is the entity that should be modified.
     * @param otherEntity
     * @param elapsedTime
     * @param variables
     * @param game
     */
    @Override
    protected void doTimeDelayedEffect(EntityInternal subject, EntityInternal otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game) {
        subject.destroySelf();
    }
}
