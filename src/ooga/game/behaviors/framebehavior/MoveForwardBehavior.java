package ooga.game.behaviors.framebehavior;

import java.util.List;
import ooga.Entity;
import ooga.game.behaviors.MovementBehavior;

/**
 * Very similar to VelocityInputBehavior
 */
public class MoveForwardBehavior implements MovementBehavior {

  private double xAccelPerFrame;
  private double yAccelPerFrame;
  private double myMaxSpeed;

  public MoveForwardBehavior(List<String> args) {
    xAccelPerFrame = Double.parseDouble(args.get(0));
    yAccelPerFrame = Double.parseDouble(args.get(1));
    myMaxSpeed = Double.parseDouble(args.get(2));
  }

  @Override
  public void doMovementUpdate(double elapsedTime, Entity subject) {
    if ((Math.abs(subject.getVelocity().get(0)) < myMaxSpeed)) {
      subject.changeVelocity(xAccelPerFrame,yAccelPerFrame);
    }
  }

}
