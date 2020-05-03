package ooga.game.behaviors.noncollisioneffects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

/**
 * @author sam thompson
 * Exactly the same as CreateEntityRelative, but allows you to set the width and height of
 * the new entity.
 */
public class CreateEntityCustomEffect extends TimeDelayedEffect {

  public static final double DEFAULT_Y_OFFSET = -100.0;
  public static final double DEFAULT_X_OFFSET = 0.0;
  public static final double DEFAULT_DIMENSION = 100.0;
  private String createdEntityType;
  private List<String> relativeEntityLocation;
  private String createdEntityWidth;
  private String createdEntityHeight;

  /**
   * @param args  1. The type of entity to create. Must be an existing type for the effect to work.
   *              2. The X value relative to this Entity where the new one is created.
   *              3. The Y value relative to this Entity where the new one is created.
   *              4. The width of the created Entity.
   *              5. The height of the created Entity.
   * @throws IndexOutOfBoundsException If there are arguments missing.
   */
  public CreateEntityCustomEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void processArgs(List<String> args) {
    createdEntityType = args.get(0);
    relativeEntityLocation = new ArrayList<>(2);
    relativeEntityLocation.add(args.get(1));
    relativeEntityLocation.add(args.get(2));
    createdEntityWidth = args.get(3);
    createdEntityHeight = args.get(4);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doTimeDelayedEffect(EntityInternal subject, EntityInternal otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game) {
    Double createX = parseData(relativeEntityLocation.get(0),subject,variables, DEFAULT_X_OFFSET);
    createX += subject.getPosition().get(0);
    Double createY = parseData(relativeEntityLocation.get(1),subject,variables, DEFAULT_Y_OFFSET);
    createY += subject.getPosition().get(1);
    double width = parseData(createdEntityWidth,subject,variables, DEFAULT_DIMENSION);
    double height = parseData(createdEntityHeight,subject,variables, DEFAULT_DIMENSION);
    game.createEntity(createdEntityType,List.of(createX,createY),width,height);
  }
}
