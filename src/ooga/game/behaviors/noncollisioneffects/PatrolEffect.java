package ooga.game.behaviors.noncollisioneffects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

@SuppressWarnings("unused")
public class PatrolEffect extends TimeDelayedEffect {

  public static final double MARGIN = 20.0;

  private String accelPerFrameData;
  private String myMaxSpeedData;

  private List<String> myFirstPointData;
  private List<String> mySecondPointData;

  private List<String> myTargetPointData;

  public PatrolEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  /**
   * Processes the String arguments given in the data file into values used by this effect.
   *
   * @param args The String arguments given for this effect in the data file.
   */
  @Override
  public void processArgs(List<String> args) {
    accelPerFrameData = args.get(0);
    myMaxSpeedData = args.get(1);

    myFirstPointData = new ArrayList<>();
    myFirstPointData.add(args.get(2));
    myFirstPointData.add(args.get(3));
    mySecondPointData = new ArrayList<>();
    mySecondPointData.add(args.get(4));
    mySecondPointData.add(args.get(5));

    myTargetPointData = myFirstPointData;
  }

  /**
   * Performs the effect
   *  @param subject     The entity that owns this. This is the entity that should be modified.
   * @param otherEntity entity we are "interacting with" in this effect
   * @param elapsedTime time between steps in ms
   * @param variables   game variables
   * @param game        game instance
   */
  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game) {
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

  private void switchTargets(Entity subject, Map<String, String> variables) {
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

  private List<Double> targetDifference(Entity subject, Map<String, String> variables) {
    List<Double> difference = new ArrayList<>();
    List<Double> myTargetPoint = List.of(parseData(myTargetPointData.get(0), subject, variables, 0.0),
            parseData(myTargetPointData.get(1), subject, variables, 0.0));
    for (int i = 0; i < subject.getPosition().size(); i ++) {
      difference.add(myTargetPoint.get(i) - subject.getPosition().get(i));
    }
    return difference;
  }

  private double getMagnitude(List<Double> vector) {
    return Math.sqrt(Math.pow(vector.get(0),2) + Math.pow(vector.get(1),2));
  }

  private double getDotProduct(List<Double> a, List<Double> b) {
    return (a.get(0) * b.get(0)) + (a.get(1) * b.get(1));
  }
}
