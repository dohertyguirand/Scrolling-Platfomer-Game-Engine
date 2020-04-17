package ooga.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.OogaDataException;
import ooga.UserInputListener;
import ooga.data.ImageEntity;
import ooga.data.OogaDataReader;
import ooga.data.OogaEntity;
import ooga.game.behaviors.asyncbehavior.DestroySelfBehavior;
import ooga.game.behaviors.framebehavior.MoveForwardBehavior;
import ooga.game.behaviors.inputbehavior.JumpBehavior;
import org.junit.jupiter.api.Test;

public class OogaGameTest {

  @Test
  void testDoUpdateLoop() {
    final double FRICTION_ACCELERATION = OogaEntity.FRICTION_ACCELERATION;
    try {
      OogaGame game = new OogaGame("Super Mario Bros", new OogaDataReader("data/games-library"));
      double expectedX = 0;
      double expectedY = 0;
      Entity targetEntity = game.getEntities().get(0);
      targetEntity.setVelocity(1.0,2.0);
      targetEntity.setMovementBehaviors(new ArrayList<>());
      for (int i = 0; i < 5; i ++) {
        double elapsedTime = (0.1 * (i));
        expectedX += elapsedTime * (1.0 - 2.0 * FRICTION_ACCELERATION);
        expectedY += elapsedTime * (2.0 - 2.0 * FRICTION_ACCELERATION);
        game.doGameStep(elapsedTime);
        assertEquals((Double)expectedX,targetEntity.getPosition().get(0));
        assertEquals((Double)expectedY,targetEntity.getPosition().get(1));
//        assertEquals(List.of(expectedX,expectedY),targetEntity.getPosition());
      }
    }
    catch (OogaDataException e) {
      fail();
    }
  }

  @Test
  void testDoCollisionLoop() {
    Entity destructibleEntity = new ImageEntity("entity1");
    destructibleEntity.setCollisionBehaviors(Map.of("entity2",List.of(new DestroySelfBehavior(new ArrayList<>()))));
    Entity obstacleEntity = new ImageEntity("entity2");
    Level level = new OogaLevel(List.of(destructibleEntity,obstacleEntity));
    OogaGame game = new OogaGame(level);
    game.doGameStep(1.0);
    assertTrue(destructibleEntity.isDestroyed());
  }

  @Test
  void testInputHandling() {
    double startingHeight = 400;
    double highJumpHeight = 50.0;
    Entity highJumpEntity = new ImageEntity("high");
    highJumpEntity.setPosition(List.of(0.0,startingHeight));
    highJumpEntity.setControlsBehaviors(Map.of("UpKey",List.of(new JumpBehavior(highJumpHeight))));
//    highJumpEntity.setControlsBehaviors(Map.of("DownKey",List.of(new JumpBehavior(highJumpHeight))));
    double lowJumpHeight = 10.0;
    Entity lowJumpEntity = new ImageEntity("low");
    lowJumpEntity.setPosition(List.of(0.0,startingHeight));
    lowJumpEntity.setControlsBehaviors(Map.of("UpKey",List.of(new JumpBehavior(lowJumpHeight))));

    double elapsedTime = 1.0;
    Level testLevel = new OogaLevel(List.of(lowJumpEntity,highJumpEntity));
    Game testGame = new OogaGame(testLevel);
    UserInputListener listener = testGame.makeUserInputListener();
    listener.reactToKeyPress("W");
    testGame.doGameStep(elapsedTime);
    double expectedHighHeight = elapsedTime * highJumpHeight;
    double expectedLowHeight = elapsedTime * lowJumpHeight;
    assertEquals(List.of(0.0,startingHeight+expectedHighHeight),highJumpEntity.getPosition());
    assertEquals(List.of(0.0,startingHeight+expectedLowHeight),lowJumpEntity.getPosition());

  }
}
