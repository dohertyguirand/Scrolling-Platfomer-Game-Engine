package ooga.game.behaviors.inputbehavior;

import java.util.List;
import java.util.Map;
import ooga.game.behaviors.NonCollisionEffect;
import ooga.Entity;

public class VelocityInputBehavior implements NonCollisionEffect {

  private double xAccelPerFrame;
  private double yAccelPerFrame;
  private double myMaxSpeed;

  public VelocityInputBehavior(List<String> args) {
    xAccelPerFrame = Double.parseDouble(args.get(0));
    yAccelPerFrame = Double.parseDouble(args.get(1));
    myMaxSpeed = Double.parseDouble(args.get(2));
  }

  public VelocityInputBehavior(double xAccel, double yAccel, double maxSpeed) {
    xAccelPerFrame = xAccel;
    yAccelPerFrame = yAccel;
    myMaxSpeed = maxSpeed;
  }

  @Override
  public void doEffect(double elapsedTime, Entity subject,
      Map<String, Double> variables) {
    if ((Math.abs(subject.getVelocity().get(0)) < myMaxSpeed)) {
      subject.changeVelocity(xAccelPerFrame,yAccelPerFrame);
    }
  }
}
