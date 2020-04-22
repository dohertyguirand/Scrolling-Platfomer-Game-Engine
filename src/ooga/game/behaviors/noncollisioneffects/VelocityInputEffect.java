package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.Entity;

public class VelocityInputEffect implements Effect {

  private double xAccelPerFrame;
  private double yAccelPerFrame;
  private double myMaxSpeed;

  public VelocityInputEffect(List<String> args) throws IndexOutOfBoundsException {
    xAccelPerFrame = Double.parseDouble(args.get(0));
    yAccelPerFrame = Double.parseDouble(args.get(1));
    myMaxSpeed = Double.parseDouble(args.get(2));
  }

  public VelocityInputEffect(double xAccel, double yAccel, double maxSpeed) {
    xAccelPerFrame = xAccel;
    yAccelPerFrame = yAccel;
    myMaxSpeed = maxSpeed;
  }

  @Override
  public void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables,
                       GameInternal game) {
    if ((Math.abs(subject.getVelocity().get(0)) < myMaxSpeed)) {
      subject.changeVelocity(xAccelPerFrame,yAccelPerFrame);
    }
  }
}
