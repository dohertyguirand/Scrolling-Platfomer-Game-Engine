package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

public class ChangeVelocityEffect extends TimeDelayedEffect {

  private String xAccelPerFrameData;
  private String yAccelPerFrameData;
  private String myMaxSpeedData;
  private static final double MAX_SPEED_DEFAULT = 1000.0;

  public ChangeVelocityEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  @Override
  public void processArgs(List<String> args) {
    xAccelPerFrameData = args.get(0);
    yAccelPerFrameData = args.get(1);
    myMaxSpeedData = args.get(2);
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
    double myMaxSpeed = parseData(myMaxSpeedData, subject, variables, MAX_SPEED_DEFAULT);
    //TODO: use dot product
    if ((Math.abs(subject.getVelocity().get(0)) < myMaxSpeed)) {
      subject.changeVelocity(parseData(xAccelPerFrameData, subject, variables, 0.0),
              parseData(yAccelPerFrameData, subject, variables, 0.0));
    }
  }
}
