package ooga.game.asyncbehavior;

import ooga.CollisionBehavior;
import ooga.Entity;

import java.util.List;

public class StopDownwardVelocity implements CollisionBehavior {
    @Override

    public void doCollision(Entity thisEntity, String collidingEntity) {
        List<Double> currentVelocity = thisEntity.getVelocity();
        thisEntity.setVelocity(currentVelocity.get(0), 0.0);
    }
}
