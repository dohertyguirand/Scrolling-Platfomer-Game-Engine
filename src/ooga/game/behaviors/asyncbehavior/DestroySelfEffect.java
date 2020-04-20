package ooga.game.behaviors.asyncbehavior;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;

public class DestroySelfEffect extends DirectionlessCollision {


    public DestroySelfEffect(List<String> args) {
        //arguments have no effect on this behavior
    }

    @Override
    public void doCollision(Entity subject, Entity collidingEntity, double elapsedTime,
        Map<String, Double> variables, GameInternal game) {
        subject.destroySelf();
    }
}
