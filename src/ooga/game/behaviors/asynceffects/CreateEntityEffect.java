package ooga.game.behaviors.asynceffects;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.TimeDelayedEffect;

public class CreateEntityEffect extends TimeDelayedEffect {

  private String createdEntityType;
  //TODO: Allow the user to use the relative entity location.
  private List<Double> relativeEntityLocation;

  public CreateEntityEffect(List<String> args) throws IndexOutOfBoundsException {
    createdEntityType = args.get(0);
    if(args.size() > 1){
      setTimeDelay(args.get(1));
    }
  }

  /**
   * Performs the effect
   *
   * @param subject     The entity that owns this. This is the entity that should be modified.
   * @param otherEntity
   * @param elapsedTime
   * @param variables
   * @param game
   */
  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    game.createEntity(createdEntityType,List.of(subject.getPosition().get(0),subject.getPosition().get(1)-subject.getHeight()));
    //TODO: make it so this can create an entity related to a variable if the above fails
  }
}
