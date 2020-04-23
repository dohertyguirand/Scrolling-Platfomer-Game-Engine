package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

public class ChangeVelocity extends TimeDelayedEffect {

  private String velocityXMultiplierData;
  private String velocityYMultiplierData;

  public ChangeVelocity(List<String> args) throws IndexOutOfBoundsException {
    velocityXMultiplierData = args.get(0);
    velocityYMultiplierData = args.get(1);
    if(args.size() > 2){
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
    subject.changeVelocity(parseData(velocityXMultiplierData, subject, variables, 0.0),
            parseData(velocityYMultiplierData, subject, variables, 0.0));
  }
}
