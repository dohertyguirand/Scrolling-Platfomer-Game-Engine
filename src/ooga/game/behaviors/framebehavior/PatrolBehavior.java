package ooga.game.behaviors.framebehavior;

import java.util.ArrayList;
import java.util.List;
import ooga.Entity;
import ooga.game.behaviors.MovementBehavior;

public class PatrolBehavior implements MovementBehavior {

  public static double MARGIN = 20.0;

  private double accelPerFrame;
  private double myMaxSpeed;

  private List<Double> myFirstPoint;
  private List<Double> mySecondPoint;

  private List<Double> myTargetPoint;

  public PatrolBehavior(List<String> args) {
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
  public void doMovementUpdate(double elapsedTime, Entity subject) {
    List<Double> difference = targetDifference(subject);
    double distanceFromTarget = getMagnitude(difference);
    System.out.println("distanceFromTarget = " + distanceFromTarget);
    if (distanceFromTarget < MARGIN) {
      System.out.println("SWITCHING TARGETS");
      switchTargets();
    }
    for (int i = 0; i < difference.size(); i ++) {
      difference.set(i,accelPerFrame * (1.0 / distanceFromTarget) * difference.get(i));
    }
    System.out.println("getMagnitude(difference) = " + getMagnitude(difference));
    if ((getDotProduct(subject.getVelocity(),difference)) < Math.pow(myMaxSpeed,2)) {
      subject.changeVelocity(difference);
    }
    else {
      System.out.println("HIT MAX SPEED");
    }
  }

  private void switchTargets() {
    System.out.println("SWITCHED TARGET POINTS");
    if (myTargetPoint.equals(myFirstPoint)) {
      System.out.println("SWITCHED FIRST TO SECOND");
      myTargetPoint = mySecondPoint;
    }
    else {
      System.out.println("SWITCHED SECOND TO FIRST");
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
