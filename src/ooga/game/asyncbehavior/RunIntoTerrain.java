package ooga.game.asyncbehavior;

import java.util.List;
import ooga.CollisionBehavior;
import ooga.Entity;
import ooga.game.CollisionDetector;
import ooga.game.OogaCollisionDetector;
import ooga.game.VelocityCollisionDetector;

public class RunIntoTerrain implements CollisionBehavior {

  @Override
  public void doCollision(Entity subject, Entity collidingEntity) {
//    System.out.println("DINO X: " + subject.getPosition().get(0));
//    System.out.println("Cactus X: " + collidingEntity.getPosition().get(0));
//    List<Double> currentVelocity = subject.getVelocity();
//    double newXPos = subject.getPosition().get(0);
//    double newYPos = collidingEntity.getPosition().get(1);
//    //if colliding vertically, stop vertical momentum
//    //else if colliding horizontally, stop horizontal momentum
//    //else if colliding diagonally, stop horizontal momentum
//    //TODO: Remove reliance here on collision detector implementation
//    CollisionDetector detector = new VelocityCollisionDetector();
////    subject.setPosition(List.of(newXPos,newYPos));
//    if (detector.isCollidingVertically(subject,collidingEntity)) {
////        collidingVertically(subject,collidingEntity)) {
//      System.out.println("Vertical collision detected");
//      while (detector.isColliding(subject,collidingEntity)) {
//        newYPos -= 1;
//        subject.setPosition(List.of(newXPos,newYPos));
//      }
//      subject.setVelocity(currentVelocity.get(0), 0.0);
//    }
//    else {
//      while (detector.isColliding(subject,collidingEntity)) {
//        newXPos -= Math.signum(subject.getVelocity().get(0));
//        subject.setPosition(List.of(newXPos,newYPos));
//      }
//      subject.setVelocity(0.0, currentVelocity.get(1));
//    }

  }

  //TODO: Set up a way to tell whether a collision is VERTICAL or HORIZONTAL
  private boolean collidingVertically(Entity subject, Entity collidingEntity) {
    //TODO: Remove reliance here on collision detector implementation
    CollisionDetector detector = new OogaCollisionDetector();
    double prevX = subject.getPosition().get(0) - subject.getVelocity().get(0);
    double prevY = subject.getPosition().get(1) - subject.getVelocity().get(1);

    List<Double> currentPos = subject.getPosition();

    subject.setPosition(List.of(prevX,prevY));
    boolean ret = detector.isColliding(subject,collidingEntity);
    subject.setPosition(currentPos);
    return ret;
  }

}
