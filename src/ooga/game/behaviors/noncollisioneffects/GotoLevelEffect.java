package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.TimeDelayedEffect;

public class GotoLevelEffect extends TimeDelayedEffect {

  private String myLevelId;

  public GotoLevelEffect(List<String> args) throws IndexOutOfBoundsException {
    myLevelId = args.get(0);
    if(args.size() > 1){
      setTimeDelay(args.get(1));
    }
  }

  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    game.goToLevel(myLevelId);
  }
}
