package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

/**
 * @author sam thompson
 */
@SuppressWarnings("unused")
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
  protected void doTimeDelayedEffect(EntityInternal subject, EntityInternal otherEntity, double elapsedTime,
                                     Map<String, String> variables, GameInternal game) {
    game.goToLevel(myLevelId);
  }
}
