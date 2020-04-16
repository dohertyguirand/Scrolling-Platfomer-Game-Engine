package ooga.game.behaviors.asyncbehavior;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.behaviors.CollisionBehavior;

/**
 * Acts like RemoveSelf, but only on a vertical collision where collidingEntity is above subject.
 */
public class StompableBehavior implements CollisionBehavior {

  public StompableBehavior(List<String> args) {
    //This has no arguments.
  }

  @Override
  public void doVerticalCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables) {
      if (collidingEntity.getPosition().get(1) + collidingEntity.getHeight() < subject.getPosition().get(1)) {
        subject.destroySelf();
      }
  }
}
