package homework3;
/**
 * @author Rohan Dayal
 */

/**
 * Class to represent the HistoryStack object which stores all equations
 * that have been entered into the calculator
 * Has two private MiStack. historical which stores all equation that have not
 * been undone, and undoRedo which stores equations that were entered but
 * undone. private int numEls counts number of equations in historical
 *
 */
public class HistoryStack {
	
	private MiStack historical = new MiStack();
	private MiStack undoRedo = new MiStack();
	private int numEls=0;
	
	/**
	 * Default constructor for HistoryStack object
	 */
	public HistoryStack() {
		
	}
	/**
	 * Method to push newEquation onto this HistoryStack
	 * @param newEquation is pushed onto historical of this HistoryStack
	 */
	public void push(Equation newEquation) {
		historical.push(newEquation);
		numEls++;
	}
	/**
	 * Method to pop the top most element of this HistoryStack
	 * @return Equation which represents the Equation that was popped from
	 * the top of history
	 */
	public Equation pop() {
		numEls--;
		return (Equation)historical.pop();
	}
	/**
	 * Method to get reference to top of stack w/out popping
	 * @return reference to top of the stack historical
	 */
	public Equation peek() {
		return (Equation)historical.peek();
	}
	/**
	 * Method to undo a calculation and remove it from this HistoryStack's
	 * history and push onto undoRedo
	 */
	public void undo() {
		numEls--;
		undoRedo.push(this.pop());
	}
	/**
	 * Method to check whether HistoryStack is empty or not
	 * @return boolean true if HistoryStack is empty. Otherwise, false.
	 * Checks the history MiStack object
	 */
	public boolean isEmpty() {
		if(historical.empty())
			return true;
		else return false;
	}
	/**
	 * Method to push undone calculation back onto the HistoryStack history MiStack 
	 * @throws NoLastUndoneException when the MiStack undoRedo is empty, as there
	 * is nothing to push onto history/this HistoryStack
	 */
	public void redo() throws NoLastUndoneException{
		if(undoRedo.empty())
			throw new NoLastUndoneException("No equation has been undone.");
		else {
			Equation guessWhoBack = (Equation)undoRedo.pop();
			guessWhoBack.setEqNumber(this.size()+1);
			historical.push(guessWhoBack);
			numEls++;
		}
	}
	/**
	 * Method to determine current size of this HistoryStack
	 * @return int representing number of elements in this HistoryStack
	 */
	public int size() {
		int counter = 0;
		MiStack temp = new MiStack();
		while(!this.isEmpty()) {
			temp.push(this.pop());
			counter++;
		}
		while(!temp.empty()) {
			this.push((Equation)temp.pop());
		}
		return counter;
	}
	/**
	 * Method to retrieve reference to equation at given position
	 * @param position is the position in the stack which's equation we want
	 * @return a reference to the Equation which is sought by calling method
	 * @throws InvalidStackPositionException is thrown when position sought
	 * is less than 1 or greater than the size of the stack
	 */
	public Equation getEquation(int position) throws 
	InvalidStackPositionException {//will pop position number of times and return the element at that position
		if(position<1) {
			throw new InvalidStackPositionException("Position is too small.");
		}
		else if(position>this.size())
			throw new InvalidStackPositionException("Position is too large.");
		MiStack temporary = new MiStack();//when the loop gets to the value of position (bc 
		/*while(counter<=position) {
			if(this.isEmpty()) {
				while(!temporary.empty()) {
					this.push((Equation)temporary.pop());
				}
				throw new InvalidStackPositionException("Position is out of bounds of the stack.");
			}
			else if(counter==position-1) {
				Equation toBeReturned = this.pop();
				while(!temporary.empty()) {
					this.push((Equation)temporary.pop());
				}
				this.push(toBeReturned);
				return toBeReturned;
			}	
			else
				temporary.push(this.pop());
		}*/
		Equation toBeReturned = new Equation();
		while(!this.isEmpty()) {
			if(this.peek().getNumber()==position) {
				toBeReturned = this.pop();
				temporary.push(toBeReturned);
				break;
			}
			else
				temporary.push(this.pop());
		}
		while(!temporary.empty()) {
			this.push((Equation)temporary.pop());
		}
		return toBeReturned;
	}
	/**
	 * toString method for this HistoryStack class and calling object
	 * @return a String representation of this HistoryStack object
	 */
	public String toString() {
			String equ =String.format("%-10s%-30s%-30s%-30s%-30s%-30s%-30s\n",
					"#", "Equation", "Prefix", "Postfix", "Answer", "Binary", "He"
							+ "xadecimal");
			equ = equ+"-----------------------------------------"
					+ "---------------------------------------------------------"
					+ "---"
					+ "--------------------------------------------------------"
					+ "---------------------"
					+ "-------\n";
			MiStack temp = new MiStack();//so we can put everything back
			while(!this.isEmpty()) {
				Equation cur = this.pop();
				temp.push(cur);//for 
				equ = equ+cur.toStringForMore();
			}
			while(!temp.empty()) {
				this.push((Equation)temp.pop());
			}
			return equ;
			
		}
	}

