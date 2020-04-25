package ooga.game.behaviors.framebehaviors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ooga.data.ImageEntity;
import ooga.data.OogaEntity;
import org.junit.jupiter.api.Test;

public class TestMoveForward {
  @Test
  void test() {
    final double FRICTION = OogaEntity.DEFAULT_FRICTION;
    final double SMALL_NUMBER = 0.000000001;
    final double ELAPSED_TIME = 1.0;
    final int NUM_ASSERTS = 10;
//    MovementBehavior behavior = new MoveForwardBehavior(List.of("1.0","2.0","6.0"));
    ImageEntity entity = new ImageEntity();
//    entity.setMovementBehaviors(List.of(behavior));
    double expectedXVel = 0.0;
    for (int i = 0; i < NUM_ASSERTS; i ++) {
//      entity.updateSelf(ELAPSED_TIME);
      expectedXVel += 1.0 - FRICTION;
      if (expectedXVel > 6.0) {
        expectedXVel = 6.0;
      }
      System.out.println("expectedXVel = " + expectedXVel);
      System.out.println("entity.getVelocity().get(0) = " + entity.getVelocity().get(0));
      assertTrue((Double)expectedXVel - SMALL_NUMBER < entity.getVelocity().get(0));
      assertTrue((Double)expectedXVel + SMALL_NUMBER > entity.getVelocity().get(0));
    }
  }
}
