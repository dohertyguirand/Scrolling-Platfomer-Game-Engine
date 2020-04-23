package ooga.game.behaviors.actions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.Game;
import ooga.game.GameInternal;
import ooga.game.behaviors.Action;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.OogaVariableCondition;
import ooga.game.behaviors.VariableCondition;
import ooga.game.behaviors.comparators.VariableComparator;
import ooga.game.behaviors.comparators.VariableEquals;

/**
 * VariableDeterminedAction: determined by entity variables. Action will be executed on any entity that has a matching variable
 * NOTE: this automatically switches the order of subject and otherEntity when executing the effects
 * Example: move all entities who have entity variable "movable" set to "true"
  */
@SuppressWarnings("unused")
public class VariableDeterminedAction extends Action {

  final String myVariable;
  final String myValueData;
  final String myComparatorData;

  public VariableDeterminedAction(List<String> args, List<Effect> effects) throws IndexOutOfBoundsException {
    super(effects);
    myVariable = args.get(0);
    myComparatorData = args.get(1);
    myValueData = args.get(2);
  }

  @Override
  public List<Entity> findOtherEntities(double elapsedTime, Entity subject,
                                        Map<String, String> variables, Map<Entity, Map<String, List<Entity>>> collisionInfo,
                                        GameInternal gameInternal) {
    VariableComparator myComparator = new VariableEquals(); //Effect.doVariableSubstitutions(myComparatorData, subject, variables);
    //TODO: use other comparators as well
    List<Entity> otherEntities = new ArrayList<>();
    for(Entity otherEntity : ((Game)gameInternal).getEntities()){
      VariableCondition variableCondition = new OogaVariableCondition(myVariable, myComparator, myValueData);
      if(variableCondition.isSatisfied(subject, variables, subject.getVariables())){
        otherEntities.add(otherEntity);
      }
    }
    return otherEntities;
  }

  @Override
  public void doAction(double elapsedTime, Entity subject, Map<String, String> variables, Map<Entity, Map<String, List<Entity>>> collisionInfo, GameInternal gameInternal) {
    List<Entity> otherEntities = findOtherEntities(elapsedTime,subject,variables,collisionInfo,gameInternal);
    for (Entity e : otherEntities) {
      doEffects(elapsedTime,subject,e,variables,gameInternal);
    }
  }
}
