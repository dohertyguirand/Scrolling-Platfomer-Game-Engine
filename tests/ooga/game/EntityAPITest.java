package ooga.game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import ooga.EntityAPI;
import ooga.MovementBehavior;
import ooga.data.ImageEntity;
import ooga.game.framebehavior.GravityBehavior;
import ooga.game.framebehavior.MoveForwardBehavior;
import org.junit.jupiter.api.Test;


class EntityAPITest {

  @Test
  void testGravity(){
    ImageEntity subject = new ImageEntity();
    subject.setMovementBehaviors(List.of(new GravityBehavior(0,-10)));
    subject.setPosition(List.of(0.0,100.0));
    subject.updateSelf(1.0);
    double expectedVelocity 
    assertEquals();
  }
}