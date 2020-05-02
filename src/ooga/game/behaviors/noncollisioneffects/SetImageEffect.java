package ooga.game.behaviors.noncollisioneffects;

import java.util.Map;

import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.TimeDelayedEffect;

import java.util.List;

/**
 * @author caryshindell, sam thompson
 * This effect changes the image location property of the entity. This will cause the corresponding view entity to
 * set the image to the file at the image location. For an entity that is not an image it will not do anything.
 * Assumptions/Notes: if the image location is invalid, the entity will still be there but will have no image (and possibly
 * have 1 pixel size...)
 * Dependencies: time delayed effect, entity internal
 * Example: when mario is moving left he sets his image to his entity variable Facing_Left_Image
 */
@SuppressWarnings("unused")
public class SetImageEffect extends TimeDelayedEffect {

  public static final String GAMES_LIBRARY_IMAGE_PATH = "file:data/games-library/";
  String newImageFileName;

  /**
   * @param args  1. The name (variable or filepath) of the image to change to.
   * @throws IndexOutOfBoundsException If there are arguments missing.
   */
  public SetImageEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void processArgs(List<String> args) {
    newImageFileName = args.get(0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doTimeDelayedEffect(EntityInternal subject, EntityInternal otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game) {
    setImage(subject, newImageFileName, variables, this);
  }


  public static void setImage(EntityInternal subject, String newImageFileName, Map<String, String> variables, Effect effectSource) {
//    if(subject.getEntityType().equals(Entity.imageEntityType)){
//      ImageEntity imageEntity = (ImageEntity)subject;
//      //TODO: add the specfic game directory to the path here
//      imageEntity.setImageLocation("file:data/games-library/" + Effect.doVariableSubstitutions(newImageFileName, subject, variables));
//    }
    subject.setImageLocation(GAMES_LIBRARY_IMAGE_PATH + Effect.doVariableSubstitutions(newImageFileName, subject, variables));
  }
}
