package ooga.game.behaviors.comparators;

import java.util.Comparator;

public class VariableEquals implements Comparator<String> {

  @Override
  public int compare(String o1, String o2) {
    try {
      Double d1 = Double.parseDouble(o1);
      Double d2 = Double.parseDouble(o2);
      return d1.compareTo(d2);
    } catch (NumberFormatException e) {
      return o1.compareTo(o2);
    }
  }
}
