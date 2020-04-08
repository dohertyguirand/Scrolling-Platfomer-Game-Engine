package ooga.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import ooga.data.Entity;
import ooga.game.framebehavior.MoveForwardBehavior;

class EntityTest {

  @org.junit.jupiter.api.Test
  void updateSelf() {
    Entity e = new Entity(new MoveForwardBehavior());
    List<Double> expectedPos = List.of(10.0,0.0);
    e.updateSelf(1.0);
    assertEquals(expectedPos,e.getPosition());
  }
}

