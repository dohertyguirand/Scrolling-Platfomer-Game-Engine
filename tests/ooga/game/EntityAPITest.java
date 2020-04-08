package ooga.game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import ooga.EntityAPI;
import ooga.game.framebehavior.MoveForwardBehavior;
import org.junit.jupiter.api.Test;


class EntityAPITest {

  @Test
  void testUpdateSelf() {
    EntityAPI e = new OogaEntity(new MoveForwardBehavior());
    List<Double> expectedPos = List.of(10.0,0.0);
    e.updateSelf(1.0);
    assertEquals(expectedPos,e.getPosition());
  }
}