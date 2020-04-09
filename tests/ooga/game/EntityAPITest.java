package ooga.game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import ooga.CollisionBehavior;
import ooga.EntityAPI;
import ooga.MovementBehavior;
import ooga.data.ImageEntity;
import ooga.game.asyncbehavior.DestroySelfBehavior;
import ooga.game.asyncbehavior.MoveUpCollision;
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

  @Test
  public void testDinosaur() {
    EntityAPI dino = new ImageEntity();
    EntityAPI cactus = new ImageEntity();
    Map<String, List<CollisionBehavior>> collisionMap = new HashMap<>();
    collisionMap.put("Cactus", List.of(new MoveUpCollision(dino, 20.01), new DestroySelfBehavior()));
    dino.setCollisionBehaviors(collisionMap);
    dino.setMovementBehaviors(List.of(new MoveForwardBehavior(10.0,0.0)));

    dino.updateSelf(1.0);
    dino.handleCollision("Cactus");
    assertTrue(dino.isDestroyed());
  }
}