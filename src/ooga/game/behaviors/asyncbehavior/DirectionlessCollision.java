package ooga.game.behaviors.asyncbehavior;

import java.util.Map;
import ooga.game.GameInternal;
import ooga.game.behaviors.CollisionBehavior;
import ooga.Entity;

public abstract class DirectionlessCollision implements CollisionBehavior {

  @Override
  public void doVerticalCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    doCollision(subject,collidingEntity, elapsedTime, variables, game);
  }

  @Override
  public void doHorizontalCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    doCollision(subject,collidingEntity, elapsedTime, variables, game);
  }

  public abstract void doCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game);
}
