package ooga.game.behaviors.dotoothereffects;

import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.noncollisioneffects.SetPositionEffect;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class SetOtherPositionEffect extends SetPositionEffect {
  /**
   * Construct the set position effect by setting desiredLocation. Note that it adds strings because it could depend on variables.
   *
   * @param args list of arguments from XMLGameDataReader
   */
  public SetOtherPositionEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game) {
    super.doTimeDelayedEffect(otherEntity, subject, elapsedTime, variables, game);
  }
}
