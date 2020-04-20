package ooga.game.behaviors.asyncbehavior;

import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.CollisionEffect;

import java.util.List;
import java.util.Map;

public class HorizontalBounce implements CollisionEffect {
    private double bounceVelocity;

    public HorizontalBounce(List<String> args) {
        bounceVelocity = Double.parseDouble(args.get(0));
    }

    @Override
    public void doHorizontalCollision(Entity subject, Entity collidingEntity, double elapsedTime,
                                    Map<String, Double> variables, GameInternal game) {
//    System.out.println("BOUNCING?");
//    if (subject.getPosition().get(1) + subject.getHeight() < collidingEntity.getPosition().get(1)) {
//      System.out.println("bounceVelocity = " + bounceVelocity);
//      System.out.println("elapsedTime = " + elapsedTime);
//      subject.changeVelocity(0.0,bounceVelocity * 5);
//    }
        subject.changeVelocity(0.0, bounceVelocity * 5);
    }
}
