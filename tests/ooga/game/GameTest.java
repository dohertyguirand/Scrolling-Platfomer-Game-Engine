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
import ooga.data.OogaDataReader;
import ooga.game.behaviors.collisioneffects.DestroySelfEffect;
import ooga.game.behaviors.Effects.JumpEffect;
import ooga.game.collisiondetection.DirectionalCollisionDetector;
import org.junit.jupiter.api.Test;

public class GameTest {

  @Test
  void testGameInitialize() throws OogaDataException {

  }

  @Test
  void testDoUpdateLoop() {
    Entity moveForwardEntity = new ImageEntity();
//    moveForwardEntity.setMovementBehaviors(List.of(new MoveForwardBehavior(10.0,20.0)));
    Level level = new OogaLevel(List.of(moveForwardEntity), "");
    OogaGame game = new OogaGame(level, new DirectionalCollisionDetector());
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
  }

  @Test
  void testInputHandling() {
    double startingHeight = 400;
    double highJumpHeight = 50.0;
    Entity highJumpEntity = new ImageEntity("high");
    highJumpEntity.setPosition(List.of(0.0,startingHeight));
    highJumpEntity.setControlsBehaviors(Map.of("UpKey",List.of(new JumpEffect(highJumpHeight))));
//    highJumpEntity.setControlsBehaviors(Map.of("DownKey",List.of(new JumpBehavior(highJumpHeight))));
    double lowJumpHeight = 10.0;
    Entity lowJumpEntity = new ImageEntity("low");
    lowJumpEntity.setPosition(List.of(0.0,startingHeight));
    lowJumpEntity.setControlsBehaviors(Map.of("UpKey",List.of(new JumpEffect(lowJumpHeight))));

    double elapsedTime = 1.0;
    Level testLevel = new OogaLevel(List.of(lowJumpEntity,highJumpEntity), );
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
