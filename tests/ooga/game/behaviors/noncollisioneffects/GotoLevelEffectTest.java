package ooga.game.behaviors.noncollisioneffects;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ooga.OogaDataException;
import ooga.data.OogaDataReader;
import ooga.game.Game;
import ooga.game.Level;
import ooga.game.OogaGame;
import ooga.game.OogaLevel;
import ooga.game.behaviors.BehaviorInstance;
import ooga.game.behaviors.ConditionalBehavior;
import ooga.game.collisiondetection.DirectionalCollisionDetector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GotoLevelEffectTest {

  public static final String GAME_NAME = "Super Mario Bros";
  public static final double ELAPSED_TIME = 1.0;
  public static String TEST_GAME = "src/data/games-library/example-mario/example_mario.xml";

  private Game myGame;

  @BeforeEach
  void setUp() throws OogaDataException {
    myGame = new OogaGame(GAME_NAME, new OogaDataReader(),"","");
  }

  @Test
  void testGotoLevel() throws OogaDataException {

//    ConditionalBehavior behavior = new BehaviorInstance(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new ArrayList<>());
    ConditionalBehavior behavior = new BehaviorInstance(new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new ArrayList<>());
    assertFalse(myGame.getEntities().isEmpty());
    myGame.getEntities().get(0).setConditionalBehaviors(List.of(behavior));
    myGame.doGameStep(ELAPSED_TIME);
    assertEquals("2",myGame.getCurrentLevelId());
  }

}