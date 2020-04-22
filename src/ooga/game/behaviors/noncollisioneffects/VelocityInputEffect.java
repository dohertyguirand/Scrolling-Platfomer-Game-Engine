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
    xAccelPerFrameData = args.get(0);
    yAccelPerFrameData = args.get(1);
    myMaxSpeedData = args.get(2);
    if(args.size() > 3){
      setTimeDelay(args.get(3));
    }
  }

  public VelocityInputEffect(double xAccel, double yAccel, double maxSpeed) {
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
