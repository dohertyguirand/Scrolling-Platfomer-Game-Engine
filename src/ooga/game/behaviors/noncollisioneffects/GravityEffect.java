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

    private List<Double> myGravityVector;

    public GravityEffect(List<String> args) {
        System.out.println(args.toString());
        double xGrav = Double.parseDouble(args.get(0));
        double yGrav = Double.parseDouble(args.get(1));
        myGravityVector = List.of(xGrav,yGrav);
    }

    public GravityEffect(double xGrav, double yGrav) {
        myGravityVector = List.of(xGrav,yGrav);
    }

    @Override
    public void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
        //subject.changeVelocity(myGravityVector.get(0)*elapsedTime/EXPECTED_DT,myGravityVector.get(1)*elapsedTime/EXPECTED_DT);
        //System.out.println("GRAVITY APPLYING TO " + subject.getName());
        subject.changeVelocity(myGravityVector.get(0),myGravityVector.get(1));
    }
}
