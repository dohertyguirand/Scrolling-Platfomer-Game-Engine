package ooga.game.behaviors.asyncbehavior;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.CollisionBehavior;

/**
 * Acts like StompableBehavior, but essentially does the inverse.
 */
public class RunIntoStompableEnemy implements CollisionBehavior {

  public RunIntoStompableEnemy(List<String> args) {
    //This has no arguments.
  }

  @Override
  public void doVerticalCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    //if colliding entity is above the subject, kill the subject.
    if (collidingEntity.getPosition().get(1) < subject.getPosition().get(1)) {
      subject.destroySelf();
    }
  }

  @Override
  public void doHorizontalCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    subject.destroySelf();
  }
}
