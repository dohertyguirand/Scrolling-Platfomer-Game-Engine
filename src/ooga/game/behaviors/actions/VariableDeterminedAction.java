package ooga.game.behaviors.actions;

import static java.lang.Class.forName;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.Action;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.OogaVariableCondition;
import ooga.game.behaviors.VariableCondition;
import ooga.game.behaviors.comparators.VariableComparator;
import ooga.game.behaviors.comparators.VariableEquals;

/**
 * @author sam thompson, caryshindell
 * VariableDeterminedAction: determined by entity variables. Action will be executed on any entity that has a matching variable
 * NOTE: this automatically switches the order of subject and otherEntity when executing the effects
 * Dependencies:  Relies on EntityInternal because it returns a List of Entities affected by
 *                the Action's effects.
 *                Relies on Effect because its constructor gives it a built-in list of Effects
 *                that it owns.
 *                Relies on GameInternal to ask for a List of all Entities with a given name.
 *                Relies on VariableCondition and VariableComparator because it compares entity
 *                variable values against a target. Relies on VariableEquals for a default comparison.
 * Example: move all entities who have entity variable "movable" set to "true"
  */
@SuppressWarnings("unused")
public class VariableDeterminedAction extends Action {

  public static final String COMPARATOR_FILE_PATH = "ooga/game/behaviors/comparators/";
  public static final String COMPARATOR_RESOURCE_FILE = "ooga/data/resources/comparators.properties";
  final ResourceBundle myComparatorResources = ResourceBundle.getBundle(COMPARATOR_RESOURCE_FILE);

  final String myVariable;
  final String myValueData;
  final String myComparatorData;

  /**
   * @param args 1. The name of the variable to check against the target value to determine which
   *             entities are affected.
   *             2. The Comparator to use to check the variable value against the target
   *                (Example: 'Equals')
   *             3. The target value to compare to the value of the variable.
   * @param effects The effects that this action executes when it finds its targets.
   */
  public VariableDeterminedAction(List<String> args, List<Effect> effects) throws IndexOutOfBoundsException {
    super(effects);
    myVariable = args.get(0);
    myComparatorData = args.get(1);
    myValueData = args.get(2);
  }

  /**
   * @param subject The EntityInternal that owns the behavior with this effect.
   * @param variables The Map of Game variables, mapping variable names to String values.
   * @param collisionInfo Maps EntityInternals with the entities that they collided with
   *                      this frame and the String identifying the direction.
   * @param gameInternal The Game utility class that provides access to game level data
   *                     like the list of entities.
   * @return A List of all Entities which have a variable with the target name, set to a
   *         value that the Comparator approves of in relation to the target value.
   */
  @Override
  public List<EntityInternal> findOtherEntities(EntityInternal subject,
                                        Map<String, String> variables, Map<EntityInternal, Map<String, List<EntityInternal>>> collisionInfo,
                                        GameInternal gameInternal) {
    VariableComparator myComparator = determineComparator();
    List<EntityInternal> otherEntities = new ArrayList<>();
    for(EntityInternal otherEntity : gameInternal.getInternalEntities()){
      VariableCondition variableCondition = new OogaVariableCondition(myVariable, myComparator, myValueData);
      if(variableCondition.isSatisfied(subject, variables, subject.getVariables())){
        otherEntities.add(otherEntity);
      }
    }
    return otherEntities;
  }

  private VariableComparator determineComparator() {
    VariableComparator myComparator;
    try {
      String comparatorClassName = myComparatorResources.getString(myComparatorData);
      Class<?> cls = forName(COMPARATOR_FILE_PATH + comparatorClassName);
      Constructor<?> cons = cls.getConstructor();
      myComparator = (VariableComparator)cons.newInstance();
    } catch (Exception e) {
      myComparator = new VariableEquals();
    }
    return myComparator;
  }


  /**
   * Carries out the effect on each Entity, but primarily affects the Entities returned by
   * findOtherEntities(), rather than primarily affecting the Entity that owns this behavior.
   * {@inheritDoc}
   */
  @Override
  public void doAction(double elapsedTime, EntityInternal subject, Map<String, String> variables,
      Map<EntityInternal, Map<String, List<EntityInternal>>> collisionInfo,
      GameInternal gameInternal) {
    List<EntityInternal> otherEntities = findOtherEntities(subject,variables,collisionInfo,gameInternal);
    for (EntityInternal e : otherEntities) {
      doEffects(elapsedTime,subject,e,variables,gameInternal);
    }
  }
}
