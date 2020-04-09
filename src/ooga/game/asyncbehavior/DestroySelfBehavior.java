package ooga.game.asyncbehavior;

import ooga.CollisionBehavior;
import ooga.EntityAPI;

public class DestroySelfBehavior implements CollisionBehavior {

    @Override
    public void doCollision(EntityAPI thisEntity, String collidingEntity) {
        thisEntity.destroySelf();
    }
}
