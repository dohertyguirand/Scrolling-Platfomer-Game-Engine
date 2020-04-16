package ooga.game;

import java.util.HashMap;
import java.util.Map;
import ooga.game.behaviors.CollisionBehavior;
import ooga.Entity;

public abstract class DirectionlessCollision implements CollisionBehavior {

  @Override
  public void doVerticalCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables) {
    doCollision(subject,collidingEntity, elapsedTime, variables);
  }

  @Override
  public void doHorizontalCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables) {
    doCollision(subject,collidingEntity, elapsedTime, variables);
  }

  public abstract void doCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables);
}
