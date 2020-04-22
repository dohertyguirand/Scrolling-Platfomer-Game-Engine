package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.Entity;

public class JumpEffect implements Effect {

  String myYVelocityData;

  public JumpEffect(List<String> args) throws IndexOutOfBoundsException {
    myYVelocityData = args.get(0);
  }

  public JumpEffect(double yVelocity) { myYVelocityData = String.valueOf(yVelocity); }

  @Override
  public void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables,
                       GameInternal game) {
      subject.changeVelocity(0, parseData(myYVelocityData, subject, variables, 0.0));
  }
}
