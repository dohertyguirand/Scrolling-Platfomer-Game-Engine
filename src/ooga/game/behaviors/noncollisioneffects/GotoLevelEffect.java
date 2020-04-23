package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;

public class GotoLevelEffect implements Effect {

  private String myLevelId;

  public GotoLevelEffect(List<String> args) throws IndexOutOfBoundsException {
    myLevelId = args.get(0);
  }

  @Override
  public void doEffect(Entity subject, Entity otherEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    game.goToLevel(myLevelId);
  }
}
