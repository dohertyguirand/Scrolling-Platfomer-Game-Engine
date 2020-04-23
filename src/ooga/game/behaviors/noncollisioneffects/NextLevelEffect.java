package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.TimeDelayedEffect;

public class NextLevelEffect extends TimeDelayedEffect {


  public static final int NUM_DEFAULT_ARGS = 0;

  public NextLevelEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args, NUM_DEFAULT_ARGS);
  }

  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    game.goToNextLevel();
  }
}
