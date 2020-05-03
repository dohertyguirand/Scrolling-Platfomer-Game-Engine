package ooga.game.behaviors;

import ooga.game.behaviors.comparators.VariableComparator;
import ooga.game.behaviors.comparators.VariableEquals;
import ooga.game.behaviors.comparators.VariableGreaterEquals;
import ooga.game.behaviors.comparators.VariableGreaterThan;
import ooga.game.behaviors.comparators.VariableLessEquals;
import ooga.game.behaviors.comparators.VariableLessThan;

public class VariableComparatorFactory {

  public VariableComparator makeComparator(String type) {
//    Equals = comparators.VariableEquals
//    LessThan = comparators.VariableLessThan
//    GreaterThan = comparators.VariableGreaterThan
//    LessThanEquals = comparators.VariableLessEquals
//    GreaterThanEquals = comparators.VariableGreaterEquals
    switch (type) {
      case "Equals":
        return new VariableEquals();
      case "LessThan":
        return new VariableLessThan();
      case "GreaterThan":
        return new VariableGreaterThan();
      case "LessThanEquals":
        return new VariableLessEquals();
      case "GreaterThanEquals":
        return new VariableGreaterEquals();
    }
    return new VariableEquals();
  }
}
