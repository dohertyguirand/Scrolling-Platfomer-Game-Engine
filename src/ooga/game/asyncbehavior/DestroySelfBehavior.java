package ooga.game.asyncbehavior;

import ooga.CollisionBehavior;
import ooga.Entity;
import ooga.game.DirectionlessCollision;

public class DestroySelfBehavior extends DirectionlessCollision {

    @Override
    public void doCollision(Entity subject, Entity collidingEntity) {
        subject.destroySelf();
    }
}
