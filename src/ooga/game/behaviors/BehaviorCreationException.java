package ooga.game.behaviors;

public class BehaviorCreationException extends Exception {

  String myMissingArg;

  public BehaviorCreationException(String missingArg) {
    myMissingArg = missingArg;
  }

  public String getMissingArg() {
    return myMissingArg;
  }
}
