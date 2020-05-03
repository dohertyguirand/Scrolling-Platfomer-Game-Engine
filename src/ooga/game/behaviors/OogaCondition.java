// This abstract class contains the non-public shared behavior between all Ooga Conditions. The only
// behavior they all share (besides isSatisfied()) is that they require certain arguments, as stated
// in their arguments resource file. So, this abstract class provides a protected method that
// checks for arguments and generates an exception when needed. I chose to make a new type of
// exception which simply carries the name of the missing argument, rather than use OogaDataException,
// because there's no reason to tie Behaviors to the Data module when the Data module already relies
// on Behavior interfaces. I chose to make this an abstract class, rather than add a default
// method to the Condition interface, because processArgument() should not be used externally.
// Even though I've given all the tools for Data to tell when it's unable to create Conditions,
// trusting Data to correctly error check for them would be relying on an implementation.
// NOTE: I didn't include BehaviorCreationException in the masterpiece code, but it is very simple.

package ooga.game.behaviors;

import java.util.Map;

/**
 * Contains all non-public shared behavior between Condition implementations in Ooga. This means
 * that it handles checking whether the required arguments for the condition are given, and it
 * generates the BehaviorCreationException necessary if a required argument is missing.
 * Dependencies:  Relies on BehaviorCreationException as the exception to throw when an argument
 *                is missing.
 * Example: An Input condition might require the name of an input and a desired status for that
 *          input, and would not be a fully defined condition otherwise.
 */
public abstract class OogaCondition implements Condition {

  // Checks whether the String argument is present in the given map of arguments. If it is not
  // present, throws a BehaviorCreationException to tell whoever is instantiating the
  // Condition that they haven't collected the required arguments.
  protected String processArgument(String argName, Map<String,String> args)
      throws BehaviorCreationException {
    if (args.containsKey(argName)) {
      return args.get(argName);
    }
    throw new BehaviorCreationException(argName);
  }
}