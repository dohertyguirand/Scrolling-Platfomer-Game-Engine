package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.Entity;
import ooga.game.behaviors.TimeDelayedEffect;

public class VelocityInputEffect extends TimeDelayedEffect {

  private String xAccelPerFrameData;
  private String yAccelPerFrameData;
  private String myMaxSpeedData;
  private static final double MAX_SPEED_DEFAULT = 1000.0;

  public VelocityInputEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  /**
   * Processes the String arguments given in the data file into values used by this effect.
   *
   * @param args The String arguments given for this effect in the data file.
   */
  @Override
  public void processArgs(List<String> args) {
    xAccelPerFrameData = args.get(0);
    yAccelPerFrameData = args.get(1);
    myMaxSpeedData = args.get(2);
  }

  @Deprecated
  public VelocityInputEffect(double xAccel, double yAccel, double maxSpeed) {
    super(null);
    xAccelPerFrameData = String.valueOf(xAccel);
    yAccelPerFrameData = String.valueOf(yAccel);
    myMaxSpeedData = String.valueOf(maxSpeed);
  }

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
    double myMaxSpeed = parseData(myMaxSpeedData, subject, variables, MAX_SPEED_DEFAULT);
    double xAccelPerFrame = parseData(xAccelPerFrameData, subject, variables, 0.0);
    double yAccelPerFrame = parseData(yAccelPerFrameData, subject, variables, 0.0);
    if ((Math.abs(subject.getVelocity().get(0)) < myMaxSpeed)) {
      subject.changeVelocity(xAccelPerFrame, yAccelPerFrame);
    }
  }
}
