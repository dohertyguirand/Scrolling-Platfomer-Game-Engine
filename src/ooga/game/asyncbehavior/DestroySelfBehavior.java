package ooga.game.asyncbehavior;

import ooga.CollisionBehavior;
import ooga.Entity;

public class DestroySelfBehavior implements CollisionBehavior {

    @Override
    public void doCollision(Entity thisEntity, String collidingEntity) {
        thisEntity.destroySelf();
    }
}