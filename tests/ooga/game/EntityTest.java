package ooga.game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import ooga.Entity;
import ooga.OogaEntity;
import ooga.game.framebehavior.MoveForwardBehavior;
import org.junit.jupiter.api.Test;


class EntityTest {

  @Test
  void testUpdateSelf() {
    Entity e = new OogaEntity(new MoveForwardBehavior());
    List<Double> expectedPos = List.of(10.0,0.0);
    e.updateSelf(1.0);
    assertEquals(expectedPos,e.getPosition());
  }
}