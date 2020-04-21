package ooga.game.behaviors.asyncbehavior;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.CollisionEffect;

public class ChangeVelocity implements CollisionEffect {

  private double bounceVelocity;

  public ChangeVelocity(List<String> args) {
    bounceVelocity = Double.parseDouble(args.get(0));
  }

  @Override
  public void doVerticalCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    subject.changeVelocity(0.0,bounceVelocity * 5);
  }
}
