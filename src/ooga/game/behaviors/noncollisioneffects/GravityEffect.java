package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;

import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;

/**
 * Uses constant downward motion to simulate basic gravity.
 * Brings attention to the challenge of having acceleration.
 */
public class GravityEffect implements Effect {

    private static final double DEFAULT_Y_GRAVITY = 0.1;
    private List<String> myGravityVectorData;

    public GravityEffect(List<String> args) throws IndexOutOfBoundsException, NumberFormatException {
        String xGrav = args.get(0);
        String yGrav = args.get(1);
        myGravityVectorData = List.of(xGrav,yGrav);
    }

    public GravityEffect(double xGrav, double yGrav) {
        myGravityVectorData = List.of(String.valueOf(xGrav),String.valueOf(yGrav));
    }

    @Override
    public void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
        //subject.changeVelocity(myGravityVector.get(0)*elapsedTime/EXPECTED_DT,myGravityVector.get(1)*elapsedTime/EXPECTED_DT);
        //System.out.println("GRAVITY APPLYING TO " + subject.getName());
        subject.changeVelocity(parseData(myGravityVectorData.get(0), subject, variables, 0.0),
                parseData(myGravityVectorData.get(1), subject, variables, DEFAULT_Y_GRAVITY));
    }
}
