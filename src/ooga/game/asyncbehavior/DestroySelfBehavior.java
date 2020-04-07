package ooga.game.asyncbehavior;

import ooga.CollisionBehavior;
import ooga.Entity;

public class DestroySelfBehavior implements CollisionBehavior {

    private Entity myEntity;

    public DestroySelfBehavior(Entity e) {
        myEntity = e;
    }

    @Override
    public void doCollision(String collidingEntity) {
        myEntity.destroySelf();
    }
}
