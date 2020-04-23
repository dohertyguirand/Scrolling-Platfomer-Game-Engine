package ooga.game.behaviors.actions;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Action;
import ooga.game.behaviors.Effect;

//  VariableDeterminedAction: determined by this entity's variables. "howToFind" is variable name/key (maps to an entity ID)
public class VariableDeterminedAction extends Action {

  String myVariable;
  String myComparatorType;

  public VariableDeterminedAction(List<String> args, List<Effect> effects) throws IndexOutOfBoundsException {
    super(effects);
    myVariable = args.get(0);
    myComparatorType = args.get(1);
  }

  @Override
  public List<Entity> findOtherEntities(double elapsedTime, Entity subject,
                                        Map<String, String> variables, Map<Entity, Map<String, List<Entity>>> collisionInfo,
                                        GameInternal gameInternal) {
    String targetID = subject.getVariable(myVariable);
    Entity effectSubject = gameInternal.getEntityWithId(targetID);
    return List.of(effectSubject);
  }
}
