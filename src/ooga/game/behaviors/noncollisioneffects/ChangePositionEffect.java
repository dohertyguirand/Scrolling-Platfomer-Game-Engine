package ooga.game.behaviors.noncollisioneffects;

import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.ExpressionEvaluator;
import ooga.game.behaviors.TimeDelayedEffect;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class ChangePositionEffect extends TimeDelayedEffect {

    private String xPosChangeData;
    private String yPosChangeData;
    private String operatorData;

    public ChangePositionEffect(List<String> args) throws IndexOutOfBoundsException {
        super(args);
    }

    @Override
    public void processArgs(List<String> args) {
        xPosChangeData = args.get(0);
        yPosChangeData = args.get(1);
        operatorData = args.get(2);
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
        String operator = Effect.doVariableSubstitutions(operatorData, subject, variables);
        String formattedXVelocity = BigDecimal.valueOf(subject.getPosition().get(0)).toPlainString();
        String formattedYVelocity = BigDecimal.valueOf(subject.getPosition().get(1)).toPlainString();
        double newX = ExpressionEvaluator.eval(formattedXVelocity+ operator + parseData(xPosChangeData, subject, variables, 0.0));
        double newY = ExpressionEvaluator.eval(formattedYVelocity+ operator + parseData(yPosChangeData, subject, variables, 0.0));
        subject.setPosition(List.of(newX,newY));
    }
}
