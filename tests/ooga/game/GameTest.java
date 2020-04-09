package ooga.game;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import ooga.EntityAPI;
import ooga.data.ImageEntity;
import ooga.game.asyncbehavior.DestroySelfBehavior;
import ooga.game.framebehavior.MoveForwardBehavior;
import org.junit.jupiter.api.Test;

public class GameTest {
  @Test
  void testGameInitialize() {

  }

  @Test
  void testDoUpdateLoop() {
    EntityAPI moveForwardEntity = new ImageEntity();
    moveForwardEntity.setMovementBehaviors(List.of(new MoveForwardBehavior(10.0,20.0)));
    Level level = new OogaLevel(List.of(moveForwardEntity));
    OogaGame game = new OogaGame(level);
    double expectedX = 0;
    double expectedY = 0;
    for (int i = 0; i < 5; i ++) {
      double elapsedTime = (0.1 * (i));
      expectedX += elapsedTime * 10.0;
      expectedY += elapsedTime * 20.0;
      game.doUpdateLoop(elapsedTime);
      EntityAPI targetEntity = game.getEntities().get(0);
      assertEquals(List.of(expectedX,expectedY),targetEntity.getPosition());
    }
  }

  @Test
  void testDoCollisionLoop() {
    EntityAPI destructibleEntity = new ImageEntity("entity1");
    destructibleEntity.setCollisionBehaviors(Map.of("entity2",List.of(new DestroySelfBehavior())));
    EntityAPI obstacleEntity = new ImageEntity("entity2");
    Level level = new OogaLevel(List.of(destructibleEntity,obstacleEntity));
    OogaGame game = new OogaGame(level);
    game.doCollisionLoop();
    assertTrue(destructibleEntity.isDestroyed());


//    EntityAPI removable = new ImageEntity();
//    removable.setCollisionBehaviors(Map.of("entity2",List.of(new DestroySelfBehavior())));
//    removable.handleCollision("entity2");
//    assertTrue(removable.isDestroyed());
  }
}
