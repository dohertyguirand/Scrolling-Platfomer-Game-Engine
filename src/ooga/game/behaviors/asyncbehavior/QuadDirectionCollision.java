package ooga.game.behaviors.asyncbehavior;

import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.CollisionEffect;

public abstract class QuadDirectionCollision implements CollisionEffect {

  @Override
  public void doVerticalCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    double topDistance = Math.abs((subject.getY()+subject.getHeight())-collidingEntity.getY());
    double bottomDistance = Math.abs(subject.getY()-(collidingEntity.getY()+collidingEntity.getHeight()));
    if (topDistance < bottomDistance) {
      doDownwardCollision(subject,collidingEntity,elapsedTime,variables,game);
    }
    else {
      doUpwardCollision(subject,collidingEntity,elapsedTime,variables,game);
    }
//    double direction = Math.signum(subject.getVelocity().get(1) - collidingEntity.getVelocity().get(1));
//    if (direction < 0) {
//      doUpwardCollision(subject,collidingEntity,elapsedTime,variables, game);
//    }
//    else {
//      doDownwardCollision(subject,collidingEntity,elapsedTime,variables, game);
//    }
  }

  protected void doDownwardCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    //does nothing by default.
  }

  protected void doUpwardCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    //does nothing by default.
  }

  @Override
  public void doHorizontalCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    double rightDistance = Math.abs((subject.getX()+subject.getWidth())-collidingEntity.getX());
    double leftDistance = Math.abs(subject.getX()-(collidingEntity.getX()+collidingEntity.getWidth()));
    if (leftDistance < rightDistance) {
      doCollisionTowardLeft(subject,collidingEntity,elapsedTime,variables, game);
    }
    else {
      doCollisionTowardRight(subject,collidingEntity,elapsedTime,variables, game);
    }
//    double direction = Math.signum(subject.getVelocity().get(0) - collidingEntity.getVelocity().get(0));
//    if (direction < 0) {
//      doCollisionTowardLeft(subject,collidingEntity,elapsedTime,variables, game);
//    }
//    else {
//      doCollisionTowardRight(subject,collidingEntity,elapsedTime,variables, game);
//    }
  }

  protected void doCollisionTowardRight(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    //does nothing by default.
  }

  protected void doCollisionTowardLeft(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    //does nothing by default.
  }

}
