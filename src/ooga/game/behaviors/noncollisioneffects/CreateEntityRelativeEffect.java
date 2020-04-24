package ooga.game.behaviors.noncollisioneffects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

@SuppressWarnings("unused")
public class CreateEntityRelativeEffect extends TimeDelayedEffect {

  public static final double DEFAULT_Y_OFFSET = -100.0;
  public static final double DEFAULT_X_OFFSET = 0.0;
  private String createdEntityType;
  private List<String> relativeEntityLocation;

  public CreateEntityRelativeEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  @Override
  public void processArgs(List<String> args) {
    createdEntityType = args.get(0);
    relativeEntityLocation = new ArrayList<>(2);
    relativeEntityLocation.add(args.get(1));
    relativeEntityLocation.add(args.get(2));

  }

  /**
   * Performs the effect
   *  @param subject     The entity that owns this. This is the entity that should be modified.
   * @param otherEntity
   * @param elapsedTime
   * @param variables
   * @param game
   */
  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game) {
    Double createX = parseData(relativeEntityLocation.get(0),subject,variables, DEFAULT_X_OFFSET);
    createX += subject.getPosition().get(0);
    Double createY = parseData(relativeEntityLocation.get(1),subject,variables, DEFAULT_Y_OFFSET);
    createY += subject.getPosition().get(1);
    game.createEntity(createdEntityType,List.of(createX,createY));
  }
}
