package ooga.game.behaviors;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javafx.beans.property.DoubleProperty;
import ooga.Entity;
import ooga.game.Level;

public interface VariableCondition {

  boolean isSatisfied(Entity behaviorEntity, Map<String, String> gameVariables, Map<String, String> subjectVariables);
}
