package ooga.game.behaviors.actions;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Action;
import ooga.game.behaviors.Effect;

//  VariableDeterminedAction: determined by this entity's variables. "howToFind" is variable name/key (probably maps to an entity ID)
public class VariableDeterminedAction extends Action {

  String myVariable;

  public VariableDeterminedAction(List<String> args, List<Effect> effects) throws IndexOutOfBoundsException {
    super(effects);
    myVariable = args.get(0);
  }

  @Override
  public List<Entity> findOtherEntities(double elapsedTime, Entity subject,
      Map<String, Double> variables, Map<Entity, Map<String, List<Entity>>> collisionInfo,
      GameInternal gameInternal) {
    String targetID = subject.getVariable(myVariable);
    Entity effectSubject = gameInternal.getEntityWithId(targetID);
    return List.of(effectSubject);
  }
}
