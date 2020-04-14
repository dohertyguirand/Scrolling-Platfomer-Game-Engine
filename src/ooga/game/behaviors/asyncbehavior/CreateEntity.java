package ooga.game.behaviors.asyncbehavior;

import java.util.List;
import ooga.CollisionBehavior;
import ooga.Entity;
import ooga.data.ImageEntity;
import ooga.data.ImageEntityDefinition;
import ooga.game.DirectionlessCollision;
import ooga.game.behaviors.framebehavior.GravityBehavior;
import ooga.game.behaviors.framebehavior.MoveForwardBehavior;

public class CreateEntity extends DirectionlessCollision {

  public CreateEntity(List<String> args) {
    //has no arguments as of now
  }

  @Override
  public void doCollision(Entity subject, Entity collidingEntity) {
    Entity created = new ImageEntity("Mushroom","file:data/games-library/example-dino/googe_dino.bmp");
    double xPos = subject.getPosition().get(0);
    double yPos = subject.getPosition().get(1) - subject.getHeight();
    created.setPosition(List.of(xPos,yPos));
    created.setMovementBehaviors(List.of(new MoveForwardBehavior(25.0/1000.0,0.0),
                                          new GravityBehavior(0.0,75.0/1000.0)));
    subject.createEntity(created);
  }
}
