package ooga.game.behaviors.inputbehavior;

import java.util.List;
import java.util.Map;
import ooga.game.behaviors.NonCollisionEffect;
import ooga.Entity;

public class JumpBehavior implements NonCollisionEffect {

  public static final int GROUND_LEVEL = 400;
  double myYVelocity;

  public JumpBehavior(List<String> args) {
    myYVelocity = Double.parseDouble(args.get(0));
  }

  public JumpBehavior(double yVelocity) {
    myYVelocity = yVelocity;
  }

  @Override
  public void doEffect(double elapsedTime, Entity subject,
      Map<String, Double> variables) {
//    if (subject.getPosition().get(1) >= GROUND_LEVEL) {
//    if (subject.getVelocity().get(1) == 0) {
      System.out.println("Jumping.");
      subject.changeVelocity(0, myYVelocity);
//    }
  }
}
