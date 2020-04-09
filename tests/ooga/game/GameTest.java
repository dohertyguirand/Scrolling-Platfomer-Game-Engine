package ooga.game;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.OogaDataException;
import ooga.data.ImageEntity;
import ooga.data.OogaDataReader;
import ooga.data.OogaEntity;
import ooga.game.asyncbehavior.DestroySelfBehavior;
import ooga.game.framebehavior.MoveForwardBehavior;
import ooga.game.inputbehavior.JumpBehavior;
import org.junit.jupiter.api.Test;

public class GameTest {
  @Test
  void testGameInitialize() throws OogaDataException {
    Game loadTest = new OogaGame("Super Mario Bros", new OogaDataReader());
    assertTrue(loadTest.getEntities().size() > 0);
  }

  @Test
  void testDoUpdateLoop() {
    Entity moveForwardEntity = new ImageEntity();
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
      Entity targetEntity = game.getEntities().get(0);
      assertEquals(List.of(expectedX,expectedY),targetEntity.getPosition());
    }
  }

  @Test
  void testDoCollisionLoop() {
    Entity destructibleEntity = new ImageEntity("entity1");
    destructibleEntity.setCollisionBehaviors(Map.of("entity2",List.of(new DestroySelfBehavior())));
    Entity obstacleEntity = new ImageEntity("entity2");
    Level level = new OogaLevel(List.of(destructibleEntity,obstacleEntity));
    OogaGame game = new OogaGame(level);
    game.doCollisionLoop();
    assertTrue(destructibleEntity.isDestroyed());
  }

  @Test
  void testInputHandling() {
    double highJumpHeight = 50.0;
    Entity highJumpEntity = new ImageEntity("high");
    highJumpEntity.setControlsBehaviors(Map.of("UpKey",List.of(new JumpBehavior(highJumpHeight))));
//    highJumpEntity.setControlsBehaviors(Map.of("DownKey",List.of(new JumpBehavior(highJumpHeight))));
    double lowJumpHeight = 10.0;
    Entity lowJumpEntity = new ImageEntity("low");
    lowJumpEntity.setControlsBehaviors(Map.of("UpKey",List.of(new JumpBehavior(lowJumpHeight))));

    double elapsedTime = 1.0;
    Level testLevel = new OogaLevel(List.of(lowJumpEntity,highJumpEntity));
    Game testGame = new OogaGame(testLevel);
    testGame.handleUserInput("W");
    testGame.doUpdateLoop(elapsedTime);
    double expectedHighHeight = elapsedTime * highJumpHeight;
    double expectedLowHeight = elapsedTime * lowJumpHeight;
    assertEquals(List.of(0.0,expectedHighHeight),highJumpEntity.getPosition());
    assertEquals(List.of(0.0,expectedLowHeight),lowJumpEntity.getPosition());

  }
}
