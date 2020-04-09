package ooga.game;

import ooga.CollisionBehavior;
import ooga.EntityAPI;
import ooga.data.ImageEntity;
import ooga.game.asyncbehavior.MoveUpCollision;
import ooga.game.asyncbehavior.DestroySelfBehavior;
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
    EntityAPI e = new ImageEntity();
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
  void testCollisionDetection() {
    EntityAPI a = new ImageEntity();
    a.setPosition(List.of(0.,0.));
    EntityAPI b = new ImageEntity();

    b.setPosition(List.of(0.,0.));
    assertTrue(new OogaCollisionDetector().isColliding(a,b));

    a.move(2.0 * a.getWidth(),0);
    assertFalse(new OogaCollisionDetector().isColliding(a,b));

    b.move(0,b.getHeight()*2.0);
    assertFalse(new OogaCollisionDetector().isColliding(b,a));

    a.setPosition(b.getPosition());
    assertTrue(new OogaCollisionDetector().isColliding(b,a));
  }

  @Test
  public void testDestroySelfCollision() {
    EntityAPI removable = new ImageEntity();
    removable.setCollisionBehaviors(Map.of("entity2",List.of(new DestroySelfBehavior())));
    removable.handleCollision("entity2");
    assertTrue(removable.isDestroyed());
  }
}
