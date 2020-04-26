package ooga;

/**
 * @author sam thompson
 * Represents every exception that occurs in the outward-facing XMLGameDataReader interface.
 * Contains an immutable, readable String field so that the program can display meaningful
 * information to the user when something fails.
 */
public class OogaDataException extends Exception {
  private final String myMessage;
  public OogaDataException(String message) {
    myMessage = message;
  }

  public String getMessage() {
    return myMessage;
  }
}
