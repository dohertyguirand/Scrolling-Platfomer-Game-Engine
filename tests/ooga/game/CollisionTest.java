package ooga.game;

import ooga.CollisionBehavior;
import ooga.EntityAPI;
import ooga.game.asyncbehavior.MoveUpCollision;
import ooga.game.asyncbehavior.DestroySelfBehavior;
import ooga.game.framebehavior.MoveForwardBehavior;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollisionTest {

  public static int NUM_MOVE_UP_ASSERTS = 3;

  @Test
  void testMoveUpCollision() {
    EntityAPI e = new OogaEntity(new MoveForwardBehavior());
    Map<String, List<CollisionBehavior>> collisionMap = new HashMap<>();
    collisionMap.put("TestEntity2", List.of(new MoveUpCollision(e, 20.01)));
    e.setCollisionBehaviors(collisionMap);
    for (int i = 0; i < NUM_MOVE_UP_ASSERTS; i++) {
      List<Double> expectedPos = List.of(0.0, i * 20.01);
      assertEquals(expectedPos, e.getPosition());
      e.handleCollision("TestEntity2");
    }
  }

  @Test
  public void testDestroySelfCollision() {
//    EntityAPI e = new OogaEntity(new MoveForwardBehavior());
//    Map<String, List<CollisionBehavior>> collisionMap = new HashMap<>();
//    collisionMap.put("TestEntity2", List.of(new DestroySelfBehavior(e)));
//    e.setCollisionBehaviors(collisionMap);
//    Level testLevel = new OogaLevel(List.of(e));
//    e.handleCollision("TestEntity2");
//    assertEquals(0, testLevel.getEntities().size());
  }
}
