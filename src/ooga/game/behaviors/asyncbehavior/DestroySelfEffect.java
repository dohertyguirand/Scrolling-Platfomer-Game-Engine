package ooga.game.behaviors.asyncbehavior;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;

public class DestroySelfEffect implements Effect {


    public DestroySelfEffect(List<String> args) {
        //arguments have no effect on this behavior
    }

    @Override
    public void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
        subject.destroySelf();
    }
}
