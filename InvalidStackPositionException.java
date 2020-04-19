package homework3;
/**
 * @author Rohan Dayal
 * @ID_Number 112768867
 * @Recitation 02
 */
/**
 * Class for InvalidStackPositionException. Extends Exception class
 *
 */
public class InvalidStackPositionException extends Exception {
	/**
	 * Constructor for InvalidStackPositionException object
	 * @param message is the message to be displayed when this 
	 * exception is caught
	 */
	public InvalidStackPositionException(String message) {
		super(message);
	}
}
