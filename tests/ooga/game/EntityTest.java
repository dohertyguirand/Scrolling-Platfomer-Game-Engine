package ooga.game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import ooga.game.behaviors.CollisionEffect;
import ooga.Entity;
import ooga.data.ImageEntity;
import ooga.game.behaviors.asyncbehavior.DestroySelfEffect;
import ooga.game.behaviors.asyncbehavior.VerticalBounce;
import ooga.game.behaviors.noncollisioneffects.GravityEffect;
import ooga.game.behaviors.noncollisioneffects.JumpEffect;
import org.junit.jupiter.api.Test;


class EntityTest {

  @Test
  void testGravity(){
    int numTests = 10;
    double xGrav = 0;
    double yGrav = -10;
    double testElapsedTime = 1.0;
    ImageEntity subject = new ImageEntity();
    subject.setMovementBehaviors(List.of(new GravityEffect(xGrav,yGrav)));
    subject.setPosition(List.of(0.0,100.0));
    List<Double> expectedVelocity = List.of(0.0,0.0);
    List<Double> expectedPosition = List.of(0.0,100.0);

    for (int i = 0 ; i < numTests; i ++) {
      subject.updateSelf(testElapsedTime, new HashMap<>(), new OogaGame(new OogaLevel(new ArrayList<>())));
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
    double elapsedTime = 1.0;
    Entity dino = new ImageEntity();
    Entity cactus = new ImageEntity("cactus");
    Map<String, List<CollisionEffect>> collisionMap = new HashMap<>();
    collisionMap.put("Cactus", List.of(new VerticalBounce(List.of("-20.0")), new DestroySelfEffect(new ArrayList<>())));
    dino.setCollisionBehaviors(collisionMap);
//    dino.setMovementBehaviors(List.of(new MoveForwardBehavior(10.0,0.0)));
    dino.updateSelf(1.0, new HashMap<>(), new OogaGame(new OogaLevel(new ArrayList<>())));
//    dino.handleCollision(cactus, elapsedTime);
    assertTrue(dino.isDestroyed());
  }

  @Test
  public void testControls() {
    Entity testEntity = new ImageEntity();
    double jumpSpeedY = 10.0;
    testEntity.setControlsBehaviors(Map.of("UpKey",List.of(new JumpEffect(jumpSpeedY))));
    testEntity.setPosition(List.of(0.0,500.0));
    testEntity.reactToControls("UpKey", new OogaGame(new OogaLevel(new ArrayList<>())));
    double elapsedTime = 1.0;
    System.out.println("testEntity.getVelocity() = " + testEntity.getVelocity());
    testEntity.updateSelf(elapsedTime, new HashMap<>(), new OogaGame(new OogaLevel(new ArrayList<>())));
    double expectedY = elapsedTime * jumpSpeedY;
    assertEquals(List.of(0.0,expectedY + 500),testEntity.getPosition());
  }
}