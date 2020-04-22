package ooga.game;

import java.util.ArrayList;
import ooga.game.behaviors.Effect;
import ooga.Entity;
import ooga.data.ImageEntity;
import ooga.game.behaviors.asynceffects.DestroySelfEffect;
import ooga.game.behaviors.asynceffects.VerticalBounce;
import ooga.game.collisiondetection.OogaCollisionDetector;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollisionTest {

  public static int NUM_MOVE_UP_ASSERTS = 3;

  @Test
  void testMoveUpCollision() {
    double elapsedTime = 1.0;
    Entity e = new ImageEntity();
    Map<String, List<Effect>> collisionMap = new HashMap<>();
    collisionMap.put("TestEntity2", List.of(new VerticalBounce(List.of("20.01"))));
    e.setCollisionBehaviors(collisionMap);
    for (int i = 0; i < NUM_MOVE_UP_ASSERTS; i++) {
      List<Double> expectedPos = List.of(0.0, i * 20.01);
      assertEquals(expectedPos, e.getPosition());
//      e.handleVerticalCollision(new ImageEntity("TestEntity2"), elapsedTime);
    }
  }

  @Test
  void testCollisionDetection() {
    double elapsedTime = 1.0;
    Entity a = new ImageEntity();
    a.setPosition(List.of(0.,0.));
    Entity b = new ImageEntity();

    b.setPosition(List.of(0.,0.));
    assertTrue(new OogaCollisionDetector().isColliding(a,b, elapsedTime));

    a.move(2.0 * a.getWidth(),0);
    assertFalse(new OogaCollisionDetector().isColliding(a,b, elapsedTime));

    b.move(0,b.getHeight()*2.0);
    assertFalse(new OogaCollisionDetector().isColliding(b,a, elapsedTime));

    a.setPosition(b.getPosition());
    assertTrue(new OogaCollisionDetector().isColliding(b,a, elapsedTime));
  }

  @Test
  public void testDestroySelfCollision() {
    double elapsedTime = 1.0;
    Entity removable = new ImageEntity();
    removable.setCollisionBehaviors(Map.of("entity2",List.of(new DestroySelfEffect(new ArrayList<>()))));
//    removable.handleVerticalCollision(new ImageEntity("entity2"), elapsedTime);
    assertTrue(removable.isDestroyed());
  }

  @Test
  public void testStopDownwardVelocity(){
    double elapsedTime = 1.0;
    Entity fallingEnity = new ImageEntity();
    fallingEnity.setCollisionBehaviors(Map.of("Floor", List.of(new StopDownwardVelocity(new ArrayList<>()))));
    fallingEnity.setVelocity(100.0,100.0);
//    fallingEnity.handleCollision(new ImageEntity("Floor"), elapsedTime);
    List<Double> velocity = fallingEnity.getVelocity();
    assertEquals(0.0,velocity.get(1));
  }
}
