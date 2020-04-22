package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.Entity;

public class JumpEffect implements Effect {

  public static final int GROUND_LEVEL = 400;
  double myYVelocity;

  public JumpEffect(List<String> args) {
    myYVelocity = Double.parseDouble(args.get(0));
  }

  public JumpEffect(double yVelocity) {
    myYVelocity = yVelocity;
  }

  @Override
  public void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables,
                       GameInternal game) {
      subject.changeVelocity(0, myYVelocity);
  }
}
