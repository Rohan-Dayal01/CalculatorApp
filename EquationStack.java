/**
 * @author Rohan Dayal
 * @ID_Number 112768867
 * @Recitation 02
 */
 
package homework3;

/**
 * EquationStack class represents a stack utilized to evaluate an equation.
 * In this project, used to evaluate postfix expressions
 * Has one MiStack object. MiStack class just extends Java API Stack class.
 */
public class EquationStack {
	
	private MiStack equation = new MiStack();
	/**
	 * Default constructor for EquationStack object
	 */
	public EquationStack() {
		
	}
	/**
	 * Void method to push a String s onto this EquationStack
	 * @param s is what will be pushed onto this EquationStack
	 */
	public void push(String s) {
		equation.push(s);
	}
	/**
	 * String method to pop String at the top of this EquationStack
	 * @return String that has been popped from this EquationStack (MiStack 
	 * equation stack is where it was stored)
	 */
	public String pop() {
		return (String)equation.pop();
	}
	/**
	 * Method to check whether this EquationStack is empty or not
	 * @return true if equation is empty,false otherwise
	 */
	public boolean isEmpty() {
		if(equation.empty())
			return true;
		else return false;
	}
	/**
	 * Method to return pointer to the Object at the top of equation MiStack/
	 * this EquationStack
	 * @return Object equation.peek(). Must be typecast to String by calling 
	 * function
	 */
	public Object peek() {
		return equation.peek();
	}
	/**
	 * Method to return the current size of the EquationStack object
	 * @return int representing the number of elements in the current
	 * EquationStack object
	 */
	public int size() {
		int counter = 0;
		MiStack temp = new MiStack();
		while(!this.isEmpty()) {
			temp.push(this.pop());
			counter++;
		}
		while(!temp.empty()) {
			this.push((String)temp.pop());
		}
		return counter;
	}
	/**
	 * Method to determine whether a given operator has a lower precedence than
	 * the one currently atop the EquationStack
	 * @param s is String representation of operator which is to be compared 
	 * with top
	 * @return true if operator has lower precedence. false otherwise
	 */
	public boolean isLowOrEqualPrecedence(String s) {
		if(this.isEmpty())
			return false;
		String top = (String)this.peek();
		//WILL NEED TO ENSURE THIS IS VALID
		//System.out.println("Verified top is " + top);
		
		if(s.contains("(")||s.contains(")")) {
			return false;
		}
		else if (s.contains("-")||s.contains("+")) {
			if(top.contains("-")||top.contains("+")||top.
					contains("*")||top.contains("/")||top.contains
					("%")||top.contains("^")) {
				return true;
			}
		}
		else if(s.contains("*")||s.contains("/")||s.contains("%")) {
			if(top.contains("%")||top.contains("/")||top.contains("*")||top.
					contains("^")) {
				return true;
			}
		}
		return false;
	}
}
