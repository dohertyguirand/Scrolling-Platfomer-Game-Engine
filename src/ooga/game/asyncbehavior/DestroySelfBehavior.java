package ooga.game.asyncbehavior;

import ooga.CollisionBehavior;
import ooga.EntityAPI;

public class DestroySelfBehavior implements CollisionBehavior {

    private EntityAPI myEntity;

    public DestroySelfBehavior(EntityAPI e) {
        myEntity = e;
    }

    @Override
    public void doCollision(String collidingEntity) {
        myEntity.destroySelf();
    }
}
