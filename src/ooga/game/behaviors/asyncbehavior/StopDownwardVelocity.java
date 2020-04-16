package ooga.game.behaviors.asyncbehavior;

import java.util.Map;
import ooga.Entity;

import java.util.List;
import ooga.game.CollisionDetector;
import ooga.game.DirectionlessCollision;
import ooga.game.collisiondetection.OogaCollisionDetector;

public class StopDownwardVelocity extends DirectionlessCollision {

    public StopDownwardVelocity(List<String> args) {
        //arguments have no effect on this behavior
    }

    @Override
    public void doCollision(Entity subject, Entity collidingEntity, double elapsedTime,
        Map<String, Double> variables) {
        List<Double> currentVelocity = subject.getVelocity();
        double newXPos = subject.getPosition().get(0);
        double newYPos = collidingEntity.getPosition().get(1) - subject.getHeight();
        //TODO: Remove reliance here on collision detector implementation
        CollisionDetector detector = new OogaCollisionDetector();
        subject.setPosition(List.of(newXPos,newYPos));
        while (detector.isColliding(subject,collidingEntity, 1.0)) {
            newYPos -= 1;
            subject.setPosition(List.of(newXPos,newYPos));
        }
        subject.setVelocity(currentVelocity.get(0), 0.0);
    }
}
