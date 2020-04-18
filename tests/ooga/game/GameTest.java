package ooga.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.OogaDataException;
import ooga.UserInputListener;
import ooga.data.ImageEntity;
import ooga.game.behaviors.asyncbehavior.DestroySelfEffect;
import ooga.game.behaviors.inputbehavior.JumpBehavior;
import org.junit.jupiter.api.Test;

public class GameTest {

  @Test
  void testGameInitialize() throws OogaDataException {
    Game loadTest = ControlsTestGameCreation.getGame();
    assertTrue(loadTest.getEntities().size() > 0);

  }

  @Test
  void testDoUpdateLoop() {
    Entity moveForwardEntity = new ImageEntity();
//    moveForwardEntity.setMovementBehaviors(List.of(new MoveForwardBehavior(10.0,20.0)));
    Level level = new OogaLevel(List.of(moveForwardEntity));
    OogaGame game = new OogaGame(level);
    double expectedX = 0;
    double expectedY = 0;
    for (int i = 0; i < 5; i ++) {
      double elapsedTime = (0.1 * (i));
      expectedX += elapsedTime * 10.0;
      expectedY += elapsedTime * 20.0;
      game.doGameStep(elapsedTime);
      Entity targetEntity = game.getEntities().get(0);
      assertEquals(List.of(expectedX,expectedY),targetEntity.getPosition());
    }
  }

  @Test
  void testDoCollisionLoop() {
    Entity destructibleEntity = new ImageEntity("entity1");
    destructibleEntity.setCollisionBehaviors(Map.of("entity2",List.of(new DestroySelfEffect(new ArrayList<>()))));
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
