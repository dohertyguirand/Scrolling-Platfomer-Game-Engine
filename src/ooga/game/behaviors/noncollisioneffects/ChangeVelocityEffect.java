package ooga.game.behaviors.noncollisioneffects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.ExpressionEvaluator;
import ooga.game.behaviors.TimeDelayedEffect;

public class ChangeVelocityEffect extends TimeDelayedEffect {

  private String xAccelerationPerFrameData;
  private String yAccelerationPerFrameData;
  private String operatorData;
  private String myMaxSpeedData;
  private static final double MAX_SPEED_DEFAULT = 1000.0;

  public ChangeVelocityEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  @Override
  public void processArgs(List<String> args) {
    int index = 0;
    xAccelerationPerFrameData = args.get(index++);
    yAccelerationPerFrameData = args.get(index++);
    operatorData = args.get(index++);
    myMaxSpeedData = args.get(index);
  }

  /**
   * Performs the effect
   *  @param subject     The entity that owns this. This is the entity that should be modified.
   * @param otherEntity
   * @param elapsedTime
   * @param variables
   * @param game
   */
  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game) {
    double myMaxSpeed = parseData(myMaxSpeedData, subject, variables, MAX_SPEED_DEFAULT);
    String operator = Effect.doVariableSubstitutions(operatorData, subject, variables);
    //TODO: use dot product
    if ((Math.abs(subject.getVelocity().get(0)) < myMaxSpeed)) {
      String formattedXVelocity = BigDecimal.valueOf(subject.getVelocity().get(0)).toPlainString();
      String formattedYVelocity = BigDecimal.valueOf(subject.getVelocity().get(1)).toPlainString();
      double newX = ExpressionEvaluator.eval(formattedXVelocity+ operator + parseData(xAccelerationPerFrameData, subject, variables, 0.0));
      double newY = ExpressionEvaluator.eval(formattedYVelocity+ operator + parseData(yAccelerationPerFrameData, subject, variables, 0.0));
      subject.setVelocity(newX, newY);
    }
  }
}
