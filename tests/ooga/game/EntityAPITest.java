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
    int numTests = 10;
    double xGrav = 0;
    double yGrav = -10;
    double testElapsedTime = 1.0;
    ImageEntity subject = new ImageEntity();
    subject.setMovementBehaviors(List.of(new GravityBehavior(xGrav,yGrav)));
    subject.setPosition(List.of(0.0,100.0));
    List<Double> expectedVelocity = List.of(0.0,0.0);
    List<Double> expectedPosition = List.of(0.0,100.0);

    for (int i = 0 ; i < numTests; i ++) {
      subject.updateSelf(testElapsedTime);
      expectedVelocity = List.of(expectedVelocity.get(0) + xGrav, expectedVelocity.get(1) + yGrav);
      System.out.println("exp = " + expectedVelocity);
      if (expectedPosition.get(1) <= 0) {
        expectedVelocity = List.of(0.0,0.0);
      }
      expectedPosition = List.of(expectedPosition.get(0)+ expectedVelocity.get(0),expectedPosition.get(1)+expectedVelocity.get(1));
      System.out.println("expectedPosition = " + expectedPosition);

      assertEquals(expectedPosition,subject.getPosition());
    }
  }
}