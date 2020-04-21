package ooga.game.behaviors.asyncbehavior;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.CollisionEffect;

public class CreateEntityEffect implements CollisionEffect {

  private String createdEntityType;
  //TODO: Allow the user to use the relative entity location.
  private List<Double> relativeEntityLocation;

  public CreateEntityEffect(List<String> args) {
    createdEntityType = args.get(0);
  }

  @Override
  public void doCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    game.createEntity(createdEntityType,List.of(subject.getPosition().get(0),subject.getPosition().get(1)-subject.getHeight()));
  }
}
