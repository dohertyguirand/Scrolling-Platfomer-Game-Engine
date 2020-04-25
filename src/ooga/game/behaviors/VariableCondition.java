package ooga.game.behaviors;

import java.util.Map;

import ooga.game.EntityInternal;

public interface VariableCondition {

  boolean isSatisfied(EntityInternal behaviorEntity, Map<String, String> gameVariables, Map<String, String> subjectVariables);
}
