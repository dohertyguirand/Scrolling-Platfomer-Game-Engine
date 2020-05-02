package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

/**
 * @author sam thompson
 * Causes the entity to remove itself from the level, meaning that
 * it won't show up onscreen and won't be considered in future frames.
 */
@SuppressWarnings("unused")
public class DestroySelfEffect extends TimeDelayedEffect {

    /**
     * @param args Has no arguments.
     */
    public DestroySelfEffect(List<String> args) {
        super(args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processArgs(List<String> args) {
        //has no arguments.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doTimeDelayedEffect(EntityInternal subject, EntityInternal otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game) {
        subject.destroySelf();
    }
}
