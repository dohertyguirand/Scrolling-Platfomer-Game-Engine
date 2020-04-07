package ooga.game.framebehavior;

import java.util.List;
import ooga.Entity;
import ooga.MovementBehavior;
import ooga.game.PhysicsEntity;

/**
 * Uses constant downward motion to simulate basic gravity.
 * Brings attention to the challenge of having acceleration.
 */
public class GravityBehavior implements MovementBehavior {

    public static final double X_MOVE_PER_SECOND = 0;
    public static final double Y_MOVE_PER_SECOND = -10;
    private Entity myEntity;
    private PhysicsEntity myPhysics;

    public void setPhysicsTarget(PhysicsEntity e) {
        myPhysics = e;
    }

    @Override
    public void setTarget(Entity e) {
        myEntity = e;
    }

    @Override
    public void doMovementUpdate(double elapsedTime) {
        myPhysics.setAcceleration(List.of(X_MOVE_PER_SECOND,Y_MOVE_PER_SECOND));
        myEntity.move(elapsedTime * X_MOVE_PER_SECOND, elapsedTime * Y_MOVE_PER_SECOND);
        myEntity.
    }
}
