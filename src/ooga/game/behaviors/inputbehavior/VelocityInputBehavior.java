package ooga.game.behaviors.inputbehavior;

import java.util.List;
import ooga.game.behaviors.ControlsBehavior;
import ooga.Entity;

public class VelocityInputBehavior implements ControlsBehavior {

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
  public void reactToControls(Entity subject) {
    if ((Math.abs(subject.getVelocity().get(0)) < myMaxSpeed)) {
      subject.changeVelocity(xAccelPerFrame,yAccelPerFrame);
    }
  }
}
