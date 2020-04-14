package ooga.game.behaviors.asyncbehavior;

import java.util.List;
import ooga.Entity;
import ooga.game.DirectionlessCollision;

public class DestroySelfBehavior extends DirectionlessCollision {


    public DestroySelfBehavior(List<String> args) {
        //arguments have no effect on this behavior
    }

    @Override
    public void doCollision(Entity subject, Entity collidingEntity) {
        subject.destroySelf();
    }
}
