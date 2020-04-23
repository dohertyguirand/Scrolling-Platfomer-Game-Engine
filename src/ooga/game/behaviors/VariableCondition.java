package ooga.game.behaviors;

import java.util.Map;

import ooga.Entity;

public interface VariableCondition {

  boolean isSatisfied(Entity behaviorEntity, Map<String, String> gameVariables, Map<String, String> subjectVariables);
}
