package ooga.game.framebehavior;

import ooga.Entity;
import ooga.MovementBehavior;

/**
 * Uses constant downward motion to simulate basic gravity.
 * Brings attention to the challenge of having acceleration.
 */
public class GravityBehavior implements MovementBehavior {

    public static final double X_MOVE_PER_SECOND = 0;
    public static final double Y_MOVE_PER_SECOND = -10;
    private Entity myEntity;

    @Override
    public void setTarget(Entity e) {
        myEntity = e;
    }

    @Override
    public void doMovementUpdate(double elapsedTime) {
        myEntity.move(elapsedTime * X_MOVE_PER_SECOND, elapsedTime * Y_MOVE_PER_SECOND);
    }
}
