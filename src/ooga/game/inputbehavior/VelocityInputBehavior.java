package ooga.game.inputbehavior;

import java.util.List;
import ooga.ControlsBehavior;
import ooga.Entity;

public class VelocityInputBehavior implements ControlsBehavior {

  private double xAccelPerFrame;
  private double yAccelPerFrame;
  private double myMaxSpeed;

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
