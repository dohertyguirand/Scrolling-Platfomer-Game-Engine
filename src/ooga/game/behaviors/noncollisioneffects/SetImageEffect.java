package ooga.game.behaviors.noncollisioneffects;

import java.util.Map;

import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.TimeDelayedEffect;

import java.util.List;

@SuppressWarnings("unused")
public class SetImageEffect extends TimeDelayedEffect {

  public static final String GAMES_LIBRARY_IMAGE_PATH = "file:data/games-library/";
  String newImageFileName;

  public SetImageEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  /**
   * Processes the String arguments given in the data file into values used by this effect.
   *
   * @param args The String arguments given for this effect in the data file.
   */
  @Override
  public void processArgs(List<String> args) {
    newImageFileName = args.get(0);
  }

  /**
   * Checks if the specified data value maps to an entity variable. Changes image to that variable, otherwise to preset value.
   * @param subject     The entity that owns this. This is the entity that should be modified.
   * @param otherEntity entity we are "interacting with" in this effect
   * @param elapsedTime time between steps in ms
   * @param variables   game variables
   * @param game        game instance
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
