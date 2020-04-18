package ooga.game.behaviors.noncollisioneffects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.NonCollisionEffect;

public class PatrolEffect implements NonCollisionEffect {

  public static double MARGIN = 20.0;

  private double accelPerFrame;
  private double myMaxSpeed;

  private List<Double> myFirstPoint;
  private List<Double> mySecondPoint;

  private List<Double> myTargetPoint;

  public PatrolEffect(List<String> args) {
    accelPerFrame = Double.parseDouble(args.get(0));
    myMaxSpeed = Double.parseDouble(args.get(1));

    myFirstPoint = new ArrayList<>();
    myFirstPoint.add(Double.parseDouble(args.get(2)));
    myFirstPoint.add(Double.parseDouble(args.get(3)));
    mySecondPoint = new ArrayList<>();
    mySecondPoint.add(Double.parseDouble(args.get(4)));
    mySecondPoint.add(Double.parseDouble(args.get(5)));

    myTargetPoint = myFirstPoint;
  }

  @Override
  public void doEffect(double elapsedTime, Entity subject, Map<String, Double> variables,
      GameInternal game) {
    List<Double> difference = targetDifference(subject);
    double distanceFromTarget = getMagnitude(difference);
    if (distanceFromTarget < MARGIN) {
      switchTargets();
    }
    for (int i = 0; i < difference.size(); i ++) {
      difference.set(i,accelPerFrame * (1.0 / distanceFromTarget) * difference.get(i));
    }
    if ((getDotProduct(subject.getVelocity(),difference)) < Math.pow(myMaxSpeed,2)) {
      subject.changeVelocity(difference);
    }
  }

  private void switchTargets() {
    if (myTargetPoint.equals(myFirstPoint)) {
      myTargetPoint = mySecondPoint;
    }
    else {
      myTargetPoint = myFirstPoint;
    }
  }

  private List<Double> targetDifference(Entity subject) {
    List<Double> difference = new ArrayList<>();
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
