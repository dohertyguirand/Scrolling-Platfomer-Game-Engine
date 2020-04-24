package ooga.game.behaviors.noncollisioneffects;

import static ooga.game.behaviors.BehaviorUtil.getDotProduct;
import static ooga.game.behaviors.BehaviorUtil.getMagnitude;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

public class MoveTowardsEntityEffect extends TimeDelayedEffect {

  public static final double LARGE_DOUBLE_VALUE = 100000000.0;
  public static final double DEFAULT_ACCELERATION = 0.0;
  String myAcceleration;
  String myMaxSpeed;

  public MoveTowardsEntityEffect(List<String> args) {
    super(args);
  }

  @Override
  public void processArgs(List<String> args) {
    myAcceleration = args.get(0);
    myMaxSpeed = args.get(1);
  }

  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime,
      Map<String, String> variables, GameInternal game) {
    System.out.println(subject.getName());
    System.out.println(otherEntity.getName());
    double maxSpeed = parseData(myMaxSpeed,subject,variables, LARGE_DOUBLE_VALUE);
    double acceleration = parseData(myAcceleration,subject,variables, DEFAULT_ACCELERATION);
    double xDiff =  otherEntity.getPosition().get(0) - subject.getPosition().get(0);
    double yDiff = otherEntity.getPosition().get(1) - subject.getPosition().get(1);
    double magnitude = getMagnitude(List.of(xDiff,yDiff));
    if (getDotProduct(List.of(xDiff,yDiff),subject.getVelocity()) > Math.pow(maxSpeed,2)) {
      return;
    }
    subject.changeVelocity((xDiff / magnitude) * acceleration, (yDiff / magnitude) * acceleration);
  }
}
