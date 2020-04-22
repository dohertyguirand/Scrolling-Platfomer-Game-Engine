package ooga.game.behaviors.asynceffects;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;

public class ChangeVelocity implements Effect {

  private String velocityMultiplierData;

  public ChangeVelocity(List<String> args) throws IndexOutOfBoundsException {
    velocityMultiplierData = args.get(0);
  }

  @Override
  public void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    subject.changeVelocity(0.0, parseData(velocityMultiplierData, subject, variables, 0.0));
  }
}
