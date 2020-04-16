package ooga.game.behaviors.asyncbehavior;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.behaviors.CollisionBehavior;

public class VerticalBounce implements CollisionBehavior {

  private double bounceVelocity;

  public VerticalBounce(List<String> args) {
    bounceVelocity = Double.parseDouble(args.get(0));
  }

  @Override
  public void doVerticalCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables) {
    System.out.println("BOUNCING?");
    if (subject.getPosition().get(1) + subject.getHeight() < collidingEntity.getPosition().get(1)) {
      System.out.println("bounceVelocity = " + bounceVelocity);
      System.out.println("elapsedTime = " + elapsedTime);
      subject.changeVelocity(0.0,bounceVelocity * 5);
    }
  }
}
