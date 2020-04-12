package ooga.game.framebehavior;

import java.util.List;
import ooga.Entity;
import ooga.MovementBehavior;

/**
 * Uses constant downward motion to simulate basic gravity.
 * Brings attention to the challenge of having acceleration.
 */
public class GravityBehavior implements MovementBehavior {

    public static final int GROUND_LEVEL = 12000;

    private List<Double> myGravityVector;

    public GravityBehavior(double xGrav, double yGrav) {
        myGravityVector = List.of(xGrav,yGrav);
    }

    @Override
    public void doMovementUpdate(double elapsedTime, Entity subject) {
        subject.changeVelocity(myGravityVector.get(0),myGravityVector.get(1));
        if ((subject.getPosition().get(1) >= GROUND_LEVEL) && (subject.getVelocity().get(1) > 0)) {
            subject.setVelocity(0,0);
        }
    }
}
