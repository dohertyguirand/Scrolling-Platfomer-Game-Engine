package ooga.game.behaviors.dotoothereffects;

import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.noncollisioneffects.SetVariableEffect;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class SetOtherVariableEffect extends SetVariableEffect {
  public SetOtherVariableEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game) {
    super.doTimeDelayedEffect(otherEntity, subject, elapsedTime, variables, game);
  }
}