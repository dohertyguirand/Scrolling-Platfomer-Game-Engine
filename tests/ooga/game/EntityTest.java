package ooga.game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import ooga.CollisionBehavior;
import ooga.Entity;
import ooga.data.ImageEntity;
import ooga.game.asyncbehavior.DestroySelfBehavior;
import ooga.game.asyncbehavior.MoveUpCollision;
import ooga.game.framebehavior.GravityBehavior;
import ooga.game.framebehavior.MoveForwardBehavior;
import ooga.game.inputbehavior.JumpBehavior;
import org.junit.jupiter.api.Test;


class EntityTest {

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
      if (expectedPosition.get(1) >= 400) {
        expectedVelocity = List.of(0.0,0.0);
      }
      expectedPosition = List.of(expectedPosition.get(0)+ expectedVelocity.get(0),expectedPosition.get(1)+expectedVelocity.get(1));
      System.out.println("expectedPosition = " + expectedPosition);

      assertEquals(expectedPosition,subject.getPosition());
    }
  }

  @Test
  public void testDinosaur() {
    Entity dino = new ImageEntity();
    Entity cactus = new ImageEntity();
    Map<String, List<CollisionBehavior>> collisionMap = new HashMap<>();
    collisionMap.put("Cactus", List.of(new MoveUpCollision(dino, 20.01), new DestroySelfBehavior()));
    dino.setCollisionBehaviors(collisionMap);
    dino.setMovementBehaviors(List.of(new MoveForwardBehavior(10.0,0.0)));

    dino.updateSelf(1.0);
    dino.handleCollision("Cactus");
    assertTrue(dino.isDestroyed());
  }

  @Test
  public void testControls() {
    Entity testEntity = new ImageEntity();
    double jumpSpeedY = 10.0;
    testEntity.setControlsBehaviors(Map.of("UpKey",List.of(new JumpBehavior(jumpSpeedY))));
    testEntity.setPosition(List.of(0.0,500.0));
    testEntity.reactToControls("UpKey");
    double elapsedTime = 1.0;
    System.out.println("testEntity.getVelocity() = " + testEntity.getVelocity());
    testEntity.updateSelf(elapsedTime);
    double expectedY = elapsedTime * jumpSpeedY;
    assertEquals(List.of(0.0,expectedY + 500),testEntity.getPosition());
  }
}