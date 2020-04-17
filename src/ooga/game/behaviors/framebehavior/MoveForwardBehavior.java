package ooga.game.behaviors.framebehavior;

import java.util.List;
import java.util.Map;

import ooga.Entity;
import ooga.game.behaviors.FrameBehavior;


/**
 * Very similar to VelocityInputBehavior
 */
public class MoveForwardBehavior implements FrameBehavior {

  private double xAccelPerFrame;
  private double yAccelPerFrame;
  private double myMaxSpeed;

  public MoveForwardBehavior(List<String> args) {
    xAccelPerFrame = Double.parseDouble(args.get(0));
    yAccelPerFrame = Double.parseDouble(args.get(1));
    myMaxSpeed = Double.parseDouble(args.get(2));
  }

  @Override
  public void doFrameUpdate(double elapsedTime, Entity subject, Map<String, Double> variables) {
    if ((Math.abs(subject.getVelocity().get(0)) < myMaxSpeed)) {
      subject.changeVelocity(xAccelPerFrame,yAccelPerFrame);
    }
  }

}
