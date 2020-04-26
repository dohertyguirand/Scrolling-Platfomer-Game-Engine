package ooga.game.behaviors;

import java.util.List;

/**
 * @author sam thompson
 */
public class EffectUtil {

  private EffectUtil() {
    //A private constructor exists to prevent an implicit public constructor,
    // since this is a utility class.
  }

  public static double getMagnitude(List<Double> vector) {
    return Math.sqrt(Math.pow(vector.get(0),2) + Math.pow(vector.get(1),2));
  }

  public static double getDotProduct(List<Double> a, List<Double> b) {
    return (a.get(0) * b.get(0)) + (a.get(1) * b.get(1));
  }
}
