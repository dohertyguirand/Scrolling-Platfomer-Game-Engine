package ooga.game.inputbehavior;

import ooga.ControlsBehavior;
import ooga.EntityAPI;

public class JumpBehavior implements ControlsBehavior {

  public static final int GROUND_LEVEL = 0;
  double myYVelocity;

  public JumpBehavior(double yVelocity) {
    myYVelocity = yVelocity;
  }

  @Override
  public void reactToControls(EntityAPI subject) {
    if (subject.getPosition().get(1) <= GROUND_LEVEL) {
      subject.changeVelocity(0, myYVelocity);
    }
  }
}
