package ooga.game.asyncbehavior;

import ooga.CollisionBehavior;
import ooga.Entity;

public class RunIntoTerrain implements CollisionBehavior {

  double margin = 1.0;

  @Override
  public void doVerticalCollision(Entity subject, Entity collidingEntity, double elapsedTime) {
    double direction = Math.signum(subject.getVelocity().get(1));
    double targetY = calcTargetY(subject,collidingEntity);
    double subjectY = subject.getPosition().get(1) + (subject.getVelocity().get(1) * elapsedTime);
    double deltaY = targetY - subjectY;
    subject.move(0.0,(deltaY - (margin * direction)) / elapsedTime);
  }

  @Override
  public void doHorizontalCollision(Entity subject, Entity collidingEntity, double elapsedTime) {
    double direction = Math.signum(subject.getVelocity().get(0));
    double targetX = calcTargetX(subject,collidingEntity);
    double subjectX = subject.getPosition().get(0) + (subject.getVelocity().get(0) * elapsedTime);
    double deltaX = targetX - subjectX;
    subject.move((deltaX - (margin* direction)) / elapsedTime,0);
  }

  private double calcTargetX(Entity subject, Entity collidingEntity) {
    if (subject.getPosition().get(0) < collidingEntity.getPosition().get(0)) {
      return collidingEntity.getPosition().get(0) - subject.getWidth();
    }
    else {
      return collidingEntity.getPosition().get(0) + collidingEntity.getWidth();
    }
  }

  private double calcTargetY(Entity subject, Entity collidingEntity) {
    if (subject.getPosition().get(1) < collidingEntity.getPosition().get(1)) {
      return collidingEntity.getPosition().get(1) - subject.getHeight();
    }
    else {
      return collidingEntity.getPosition().get(1) + collidingEntity.getHeight();
    }
  }
}
