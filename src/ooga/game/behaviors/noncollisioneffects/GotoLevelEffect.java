package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

public class GotoLevelEffect extends TimeDelayedEffect {

  private String myLevelId;

  public GotoLevelEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  @Override
  public void processArgs(List<String> args) {
    myLevelId = args.get(0);
  }

  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime,
                                     Map<String, String> variables, GameInternal game) {
    game.goToLevel(myLevelId);
  }
}
