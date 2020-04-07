package ooga;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MoveUpCollisionTest {
  @Test
  void doCollision() {
    Entity e = new OogaEntity(new MoveForwardBehavior());
    Map<String,List<CollisionBehavior>> CollisionMap = new HashMap<>();
    CollisionMap.put("TestEntity2",List.of(new MoveUpCollision(e,20.01)));
    e.setCollisionBehaviors(CollisionMap);
    List<Double> expectedPos = List.of(0.0,20.01);
    e.handleCollision("TestEntity2");
    assertEquals(expectedPos,e.getPosition());
  }
}