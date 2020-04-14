package ooga.game.behaviors.inputbehavior;

import java.util.List;
import ooga.ControlsBehavior;
import ooga.Entity;

public class JumpBehavior implements ControlsBehavior {

  public static final int GROUND_LEVEL = 400;
  double myYVelocity;

  public JumpBehavior(List<String> args) {
    myYVelocity = Double.parseDouble(args.get(0));
  }

  public JumpBehavior(double yVelocity) {
    myYVelocity = yVelocity;
  }

  @Override
  public void reactToControls(Entity subject) {
//    if (subject.getPosition().get(1) >= GROUND_LEVEL) {
//    if (subject.getVelocity().get(1) == 0) {
      System.out.println("Jumping.");
      subject.changeVelocity(0, myYVelocity);
//    }
  }
}
