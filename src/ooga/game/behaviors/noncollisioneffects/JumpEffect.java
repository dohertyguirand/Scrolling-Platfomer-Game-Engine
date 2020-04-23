package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.Entity;
import ooga.game.behaviors.TimeDelayedEffect;

@Deprecated
public class JumpEffect extends TimeDelayedEffect {

  String myYVelocityData;

  public JumpEffect(List<String> args) throws IndexOutOfBoundsException {
    myYVelocityData = args.get(0);
    if(args.size() > 1){
      setTimeDelay(args.get(1));
    }
  }

  public JumpEffect(double yVelocity) { myYVelocityData = String.valueOf(yVelocity); }

  /**
   * Performs the effect
   *
   * @param subject     The entity that owns this. This is the entity that should be modified.
   * @param otherEntity entity we are "interacting with" in this effect
   * @param elapsedTime time between steps in ms
   * @param variables   game variables
   * @param game        game instance
   */
  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    subject.changeVelocity(0, parseData(myYVelocityData, subject, variables, 0.0));
  }
}
