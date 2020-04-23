package ooga.game.behaviors;

import java.util.Comparator;
import java.util.Map;
import ooga.Entity;

public class OogaVariableCondition implements VariableCondition {

  private Comparator<String> myComparator;
  private String myCompareTo;
  private String myVariableName;

  public OogaVariableCondition(String varName, Comparator<String> comparator, String value) {
    myComparator = comparator;
    myCompareTo = value;
    myVariableName = varName;
  }

  @Override
  public boolean isSatisfied(Entity behaviorEntity, Map<String, String> gameVariables,
      Map<String, String> subjectVariables) {
    //0. By default, pretend it's a value.
    String compareToValue = myCompareTo;
    //1. is myCompareTo a GAME variable?
    if (gameVariables.containsKey(myCompareTo)) {
      compareToValue = gameVariables.get(myCompareTo);
    }
    //2. is myCompareTo a variable of this Entity?
    else if (behaviorEntity.getVariable(myCompareTo) != null) {
      compareToValue = behaviorEntity.getVariable(myCompareTo);
    }
    //3. compare directly.
    return (myComparator.compare(subjectVariables.get(myVariableName),myCompareTo) > 0);
  }

}
