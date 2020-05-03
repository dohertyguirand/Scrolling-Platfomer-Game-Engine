package ooga.game.behaviors.noncollisioneffects;

import static ooga.game.behaviors.EffectUtil.getDotProduct;
import static ooga.game.behaviors.EffectUtil.getMagnitude;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

/**
 * @author sam thompson
 * Causes the subject to patrol directly between two specified locations
 * at a specified acceleration and max speed.
 */
@SuppressWarnings("unused")
public class PatrolEffect extends TimeDelayedEffect {

  public static final double MARGIN = 20.0;

  private String accelPerFrameData;
  private String myMaxSpeedData;

  private List<String> myFirstPointData;
  private List<String> mySecondPointData;

  private List<String> myTargetPointData;

  /**
   * @param args  1. The acceleration to apply toward the current target point.
   *              2. The maximum speed this Effect can cause.
   *              3. The X value of the first patrol point.
   *              4. The Y value of the first patrol point.
   *              5. The X value of the second patrol point.
   *              6. The Y value of the second patrol point.
   * @throws IndexOutOfBoundsException If there are arguments missing.
   */
  public PatrolEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void processArgs(List<String> args) {
    int index = 0;
    accelPerFrameData = args.get(index++);
    myMaxSpeedData = args.get(index++);

    myFirstPointData = new ArrayList<>();
    myFirstPointData.add(args.get(index++));
    myFirstPointData.add(args.get(index++));
    mySecondPointData = new ArrayList<>();
    mySecondPointData.add(args.get(index++));
    mySecondPointData.add(args.get(index));

    myTargetPointData = myFirstPointData;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doTimeDelayedEffect(EntityInternal subject, EntityInternal otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game) {
    List<Double> difference = targetDifference(subject, variables);
    double distanceFromTarget = getMagnitude(difference);
    if (distanceFromTarget < MARGIN) {
      switchTargets(subject, variables);
    }
    double accelPerFrame = parseData(accelPerFrameData, subject, variables, 0.0);
    for (int i = 0; i < difference.size(); i ++) {
      difference.set(i,accelPerFrame * (1.0 / distanceFromTarget) * difference.get(i));
    }
    double myMaxSpeed = parseData(myMaxSpeedData, subject, variables, 0.0);
    if ((getDotProduct(subject.getVelocity(),difference)) < Math.pow(myMaxSpeed,2)) {
      subject.changeVelocity(difference.get(0), difference.get(1));
    }
  }

  private void switchTargets(EntityInternal subject, Map<String, String> variables) {
    List<Double> myTargetPoint = List.of(parseData(myTargetPointData.get(0), subject, variables, 0.0),
            parseData(myTargetPointData.get(1), subject, variables, 0.0));
    List<Double> myFirstPoint = List.of(parseData(myFirstPointData.get(0), subject, variables, 0.0),
            parseData(myFirstPointData.get(1), subject, variables, 0.0));
    if (myTargetPoint.equals(myFirstPoint)) {
      myTargetPointData = mySecondPointData;
    }
    else {
      myTargetPointData = myFirstPointData;
    }
  }

  private List<Double> targetDifference(EntityInternal subject, Map<String, String> variables) {
    List<Double> difference = new ArrayList<>();
    List<Double> myTargetPoint = List.of(parseData(myTargetPointData.get(0), subject, variables, 0.0),
            parseData(myTargetPointData.get(1), subject, variables, 0.0));
    for (int i = 0; i < subject.getPosition().size(); i ++) {
      difference.add(myTargetPoint.get(i) - subject.getPosition().get(i));
    }
    return difference;
  }


}
