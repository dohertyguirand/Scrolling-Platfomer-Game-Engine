package ooga.game.framebehavior;

import java.util.List;
import ooga.EntityAPI;
import ooga.MovementBehavior;
import ooga.data.Entity;
import ooga.game.PhysicsEntity;

/**
 * Uses constant downward motion to simulate basic gravity.
 * Brings attention to the challenge of having acceleration.
 */
public class GravityBehavior implements MovementBehavior {

    private List<Double> myGravityVector;
    private EntityAPI myEntity;

    public GravityBehavior(double xGrav, double yGrav) {
        myGravityVector = List.of(xGrav,yGrav);
    }

    @Override
    public void doMovementUpdate(double elapsedTime, Entity subject) {
        subject.changeVelocity(myGravityVector.get(0),myGravityVector.get(1));
        if (subject.getPosition().get(1) <= 0) {
            subject.setVelocity(0,0);
        }
        subject.moveByVelocity();
    }
}
