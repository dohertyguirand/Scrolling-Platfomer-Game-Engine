package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.TimeDelayedEffect;

public class NextLevelEffect extends TimeDelayedEffect {


  public NextLevelEffect(List<String> args) throws IndexOutOfBoundsException {
    //has no relevant arguments.
    if(args.size() > 1){
      setTimeDelay(args.get(1));
    }
  }

  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    game.goToNextLevel();
  }
}
