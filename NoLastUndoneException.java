/**
 * @author Rohan Dayal
 * @ID_Number 112768867
 * @Recitation 02
 */
 
package homework3;
/**
 * Class to define NoLastUndoneException. Extends Exception class
 */
public class NoLastUndoneException extends Exception{
	/**
	 * Constructor for NoLastUndoneException
	 * @param message is what is to be displayed when this exception is caught
	 */
	public NoLastUndoneException(String message) {
		super(message);
	}

}
