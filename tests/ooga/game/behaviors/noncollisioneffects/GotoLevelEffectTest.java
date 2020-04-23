package ooga.game.behaviors.noncollisioneffects;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import ooga.game.Game;
import ooga.game.GameInternal;
import ooga.game.OogaGame;
import ooga.game.OogaLevel;
import ooga.game.collisiondetection.DirectionalCollisionDetector;
import org.junit.jupiter.api.Test;

class GotoLevelEffectTest {

  @Test
  void testGotoLevel() {

    OogaGame game = new OogaGame(new OogaLevel(new ArrayList<>()),new DirectionalCollisionDetector());

  }

}