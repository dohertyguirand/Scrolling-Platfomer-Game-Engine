package ooga;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class EntityTest {

  @org.junit.jupiter.api.Test
  void updateSelf() {
    Entity e = new OogaEntity(new MoveForwardBehavior());
    List<Double> expectedPos = List.of(10.0,0.0);
    e.updateSelf(1.0);
    assertEquals(expectedPos,e.getPosition());
  }
}