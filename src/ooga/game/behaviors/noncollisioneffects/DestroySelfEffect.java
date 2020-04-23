package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

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
     *
     * @param subject     The entity that owns this. This is the entity that should be modified.
     * @param otherEntity
     * @param elapsedTime
     * @param variables
     * @param game
     */
    @Override
    protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
        subject.destroySelf();
    }
}
