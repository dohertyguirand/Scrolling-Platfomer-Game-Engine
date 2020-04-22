package ooga.game.behaviors.asynceffects;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.TimeDelayedEffect;

public class ChangeVelocity extends TimeDelayedEffect {

  private String velocityMultiplierData;

  public ChangeVelocity(List<String> args) throws IndexOutOfBoundsException {
    velocityMultiplierData = args.get(0);
    if(args.size() > 1){
      setTimeDelay(args.get(1));
    }
  }

  /**
   * Performs the effect
   *
   * @param subject     The entity that owns this. This is the entity that should be modified.
   * @param otherEntity
   * @param elapsedTime
   * @param variables
   * @param game
   */
  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    subject.changeVelocity(0.0, parseData(velocityMultiplierData, subject, variables, 0.0));
  }
}
