package ooga;

/**
 * Represents the subject in the Observer pattern for user input. Matches up with the
 * UserInputListener observer.
 * Allows subscribers to register to be notified of user input, without the user input
 * concrete class relying on the listeners' individual classes.
 */
public interface UserInputSubject {

  /**
   * Registers an object to be notified of user input.
   * @param listener The object to add to the list of notified objects.
   */
  void registerListener(UserInputListener listener);

  /**
   * Unregisters a listener if it is on the list of active listeners.
   * @param listener The object that will not be notified of user input detected by this class,
   *                 until further notice.
   */
  void unregisterListener(UserInputListener listener);
}
