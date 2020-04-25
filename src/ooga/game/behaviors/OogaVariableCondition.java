package ooga.game.behaviors;

import java.util.Map;
import ooga.Entity;
import ooga.game.behaviors.comparators.VariableComparator;

public class OogaVariableCondition implements VariableCondition {

  private final VariableComparator myComparator;
  private final String myCompareTo;
  private final String myVariableName;

  public OogaVariableCondition(String varName, VariableComparator comparator, String value) {
    myComparator = comparator;
    myCompareTo = value;
    myVariableName = varName;
  }

  @Override
  public boolean isSatisfied(Entity behaviorEntity, Map<String, String> gameVariables,
      Map<String, String> subjectVariables) {
    if (!subjectVariables.containsKey(myVariableName)) {
      return false;
    }
    String compareToValue = myCompareTo;
    if (gameVariables.containsKey(myCompareTo)) {
      compareToValue = gameVariables.get(myCompareTo);
    }
    else if (behaviorEntity.getVariable(myCompareTo) != null) {
      compareToValue = behaviorEntity.getVariable(myCompareTo);
    }
    return (myComparator.compareVars(subjectVariables.get(myVariableName),compareToValue));
  }

  @Override
  public String toString() {
    return "Compares variable " + myVariableName + " to value " + myCompareTo;
  }
}
