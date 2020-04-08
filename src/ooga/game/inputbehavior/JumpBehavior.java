package ooga.game.inputbehavior;

import ooga.CollisionBehavior;
import ooga.ControlsBehavior;
import ooga.data.Entity;
import ooga.game.EntityInternal;

public class JumpBehavior implements ControlsBehavior {

  public static final int GROUND_LEVEL = 0;
  double myYVelocity;

  public JumpBehavior(double yVelocity) {
    myYVelocity = yVelocity;
  }

  @Override
  public void reactToControls(Entity subject) {
    if (subject.getPosition().get(1) <= GROUND_LEVEL) {
      subject.changeVelocity(0, myYVelocity);
    }
  }
}
