package ooga.game;

import ooga.CollisionBehavior;
import ooga.Entity;

public abstract class DirectionlessCollision implements CollisionBehavior {

  @Override
  public void doVerticalCollision(Entity subject, Entity collidingEntity, double elapsedTime) {
    doCollision(subject,collidingEntity);
  }

  @Override
  public void doHorizontalCollision(Entity subject, Entity collidingEntity, double elapsedTime) {
    doCollision(subject,collidingEntity);
  }

  public abstract void doCollision(Entity subject, Entity collidingEntity);
}
