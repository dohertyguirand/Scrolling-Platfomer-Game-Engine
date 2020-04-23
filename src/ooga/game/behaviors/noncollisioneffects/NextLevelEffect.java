package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;

public class NextLevelEffect implements Effect {


  public NextLevelEffect(List<String> args) throws IndexOutOfBoundsException {
    //has no relevant arguments.
  }

  @Override
  public void doEffect(Entity subject, Entity otherEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    game.goToNextLevel();
  }
}
