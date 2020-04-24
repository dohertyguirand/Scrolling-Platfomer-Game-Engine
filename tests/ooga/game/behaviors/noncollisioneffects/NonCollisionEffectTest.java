package ooga.game.behaviors.noncollisioneffects;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ooga.Entity;
import ooga.OogaDataException;
import ooga.data.OogaDataReader;
import ooga.game.Game;
import ooga.game.GameInternal;
import ooga.game.Level;
import ooga.game.OogaGame;
import ooga.game.OogaLevel;
import ooga.game.behaviors.BehaviorInstance;
import ooga.game.behaviors.ConditionalBehavior;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.actions.IndependentAction;
import ooga.game.collisiondetection.DirectionalCollisionDetector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NonCollisionEffectTest {

  public static final String GAME_NAME = "Super Mario Bros";
  public static final double ELAPSED_TIME = 1.0;
  public static final String TARGET_LEVEL = "2";
  public static final String INVALID_LEVEL_NAME = "INVALIDLEVEL";
  public static String TEST_GAME = "src/data/games-library/example-mario/example_mario.xml";

  private Game myGame;
  private GameInternal myGameInternal;

  @BeforeEach
  void setUp() throws OogaDataException {
    OogaGame game = new OogaGame(GAME_NAME, new OogaDataReader(),"","");
    myGame = game;
    myGameInternal = game;
  }

  @Test
  void testGotoLevel() throws OogaDataException {
    createBehaviorWithEffect(new GotoLevelEffect(List.of(TARGET_LEVEL)));
    assertEquals("1",myGame.getCurrentLevelId());
    myGame.doGameStep(ELAPSED_TIME);
    assertEquals(TARGET_LEVEL,myGame.getCurrentLevelId());
  }

  @Test
  void testGotoInvalidLevel() {
    createBehaviorWithEffect(new GotoLevelEffect(List.of(INVALID_LEVEL_NAME)));
    assertEquals("1",myGame.getCurrentLevelId());
    myGame.doGameStep(ELAPSED_TIME);
    assertEquals(INVALID_LEVEL_NAME,myGame.getCurrentLevelId());
  }

  @Test
  void testChangeVelocity() {
    List<String> args = List.of("1.0","10.0","+","100.0");
    Entity affected = createBehaviorWithEffect(new ChangeVelocityEffect(args));
    assertEquals(List.of(0.0,0.0),affected.getVelocity());
    myGame.doGameStep(ELAPSED_TIME);
    assertEquals(List.of(1.0,10.0),affected.getVelocity());
  }

  @Test
  void testCreateEntityRelative() {
    List<String> args = List.of("SmallMario","0.0","0.0");
    createBehaviorWithEffect(new CreateEntityRelativeEffect(args));
    double startingEntities = myGame.getEntities().size();
    assertEquals(startingEntities,myGame.getEntities().size());
    myGame.doGameStep(ELAPSED_TIME);
    assertEquals(startingEntities+1,myGame.getEntities().size());
  }

  @Test
  void testChangePositionEffect() {
    List<String> args = List.of("10.0","10.0","+");
    Entity affected = createBehaviorWithEffect(new ChangePositionEffect(args));
    List<Double> startPos = affected.getPosition();
    assertEquals(startPos,affected.getPosition());
    myGame.doGameStep(ELAPSED_TIME);
    assertEquals(List.of(startPos.get(0)+10.0,startPos.get(1)+10.0),affected.getPosition());
  }

  @Test
  void testChangeVariableEffect() {
    List<String> args = List.of("Lives","+","5.0");
    createBehaviorWithEffect(new ChangeVariableEffect(args));
    double startingLives = Double.parseDouble(myGameInternal.getVariables().get(args.get(0)));
    assertEquals(startingLives, Double.parseDouble(myGameInternal.getVariables().get(args.get(0))));
    myGame.doGameStep(ELAPSED_TIME);
    assertEquals(startingLives + 5, Double.parseDouble(myGameInternal.getVariables().get(args.get(0))));
  }

  private Entity createBehaviorWithEffect(Effect effect) {
    IndependentAction action = new IndependentAction(new ArrayList<>(),List.of(effect));
    ConditionalBehavior behavior = new BehaviorInstance(new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), List.of(action));
    myGame.getEntities().get(0).setConditionalBehaviors(List.of(behavior));
    return myGame.getEntities().get(0);
  }

}