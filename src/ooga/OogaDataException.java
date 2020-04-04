package ooga;

public class OogaDataException extends Exception {
  private String myMessage;
  public OogaDataException(String message) {
    myMessage = message;
  }

  public String getMessage() {
    return myMessage;
  }
}
