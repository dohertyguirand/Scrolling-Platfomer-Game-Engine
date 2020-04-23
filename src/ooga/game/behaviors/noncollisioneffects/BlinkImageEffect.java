package ooga.game.behaviors.noncollisioneffects;

import ooga.Entity;
import ooga.data.ImageEntity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;

import java.util.List;
import java.util.Map;

/**
 * Blinks the image between 2 different images at a set rate, until total blink time has passed.
 * Note: this does not implement TimeDelayedEffect because it has its own time delays
 * This can be coupled with another time delayed effect like respawn (but this effect must complete before respawn)
 */
public class BlinkImageEffect implements Effect {

  String image1Data;
  String image2Data;
  String finalImageData;
  String blinkTimeDelayData;
  String totalBlinkTimeData;
  double timePassed = 0.0;
  double timePassedSinceLastBlink = 0.0;
  String currentImage;

  public BlinkImageEffect(List<String> args) throws IndexOutOfBoundsException{
    image1Data = args.get(0);
    image2Data = args.get(1);
    finalImageData = args.get(2);
    blinkTimeDelayData = args.get(3);
    totalBlinkTimeData = args.get(4);
    currentImage = image1Data;
  }

  /**
   * Requires the effect to have a reference to the
   * instance that uses it in order to have an effect on that instance.
   *  @param subject     The entity that owns this controls behavior. This is the entity that should
   *                    be modified.
   * @param otherEntity
   * @param elapsedTime
   * @param variables
   * @param game
   */
  @Override
  public void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game) {
    double blinkTimeDelay = parseData(blinkTimeDelayData, subject, variables, 0.0);
    double totalBlinkTime = parseData(totalBlinkTimeData, subject, variables, 0.0);
    String finalImage = doVariableSubstitutions(finalImageData, subject, variables);
    String image1 = doVariableSubstitutions(image1Data, subject, variables);
    String image2 = doVariableSubstitutions(image2Data, subject, variables);
    timePassedSinceLastBlink += elapsedTime;
    if(timePassed >= totalBlinkTime){
      setImage(subject, finalImage, variables);
      timePassed = 0.0;
      /* note that due to this method only being called when the behavior conditions are true means timePassed will be
       somewhat meaningless; that is, this effect is unlikely to give you the desired final image unless the numbers
       are perfect
       */
    }
    else if(timePassedSinceLastBlink >= blinkTimeDelay){
      if(currentImage.equals(image1)) {
        setImage(subject, image2, variables);
        currentImage = image2;
      } else{
        setImage(subject, image1, variables);
        currentImage = image1;
      }
      timePassedSinceLastBlink = 0.0;
    }
    timePassed += elapsedTime;
  }

  private void setImage(Entity subject, String newImageFileName, Map<String, String> variables) {
    //TODO: find a better way than using instanceof
    if(subject instanceof ImageEntity){
      ImageEntity imageEntity = (ImageEntity)subject;
      imageEntity.setImageLocation("file:data/games-library/" + doVariableSubstitutions(newImageFileName, subject, variables));
    }
  }
}
