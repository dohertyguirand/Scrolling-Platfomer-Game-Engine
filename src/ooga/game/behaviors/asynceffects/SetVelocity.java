package ooga.game.behaviors.asynceffects;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

public class SetVelocity extends TimeDelayedEffect {

  private String velocityXData;
  private String velocityYData;

  public SetVelocity(List<String> args) throws IndexOutOfBoundsException {
    velocityXData = args.get(0);
    velocityYData = args.get(1);
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
    subject.setVelocity(parseData(velocityXData, subject, variables, 0.0),
            parseData(velocityYData, subject, variables, 0.0));
  }
}
