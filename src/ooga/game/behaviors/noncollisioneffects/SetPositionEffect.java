package ooga.game.behaviors.noncollisioneffects;

import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sam thompson
 * Sets the position of the subject to a specified value.
 */
@SuppressWarnings("unused")
public class SetPositionEffect extends TimeDelayedEffect {

  private List<String> desiredLocation;

  /**
   * @param args  1. The X position for this Entity to go to.
   *              2. The Y position for this Entity to go to.
   * @throws IndexOutOfBoundsException If there are arguments missing.
   */
  public SetPositionEffect(List<String> args) throws IndexOutOfBoundsException{
    super(args);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void processArgs(List<String> args) {
    desiredLocation = new ArrayList<>();
    desiredLocation.add(args.get(0));
    desiredLocation.add(args.get(1));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doTimeDelayedEffect(EntityInternal subject, EntityInternal otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game) {
    List<Double> newLocation = new ArrayList<>();
    for(String coordinateData : desiredLocation){
      newLocation.add(parseData(coordinateData, subject, variables, 0.0));
    }
    subject.setPosition(newLocation);
  }
}
