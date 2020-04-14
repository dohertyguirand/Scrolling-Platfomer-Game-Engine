package ooga.game.asyncbehavior;

import ooga.CollisionBehavior;
import ooga.Entity;

import java.util.List;
import ooga.game.CollisionDetector;
import ooga.game.DirectionlessCollision;
import ooga.game.OogaCollisionDetector;

public class StopDownwardVelocity extends DirectionlessCollision {

    @Override
    public void doCollision(Entity subject, Entity collidingEntity) {
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
