package ooga.game.asyncbehavior;

import ooga.CollisionBehavior;
import ooga.Entity;

public class DestroySelfBehavior implements CollisionBehavior {

    @Override
    public void doCollision(Entity subject, Entity collidingEntity) {
        subject.destroySelf();
    }
}
