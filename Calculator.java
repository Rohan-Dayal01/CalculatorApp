/**
  * @author Rohan Dayal
 */
 

package homework3;

import java.util.Scanner;
/**
 * This class Calculator is a driver class for the calculator project 
 * which also utilizes Equation, EquationStack, HistoryStack, and other
 * classes
 */
public class Calculator {
	/**
	 * Main method to drive the simulation
	 * @param args
	 */
	public static void main(String[]args) {
		Scanner rod = new Scanner(System.in);
		System.out.println("Welcome to calculat0r.");
		HistoryStack calcMemory = new HistoryStack();
		String entd = "Z";
		while(!entd.equals("Q")) {
			System.out.println("[A] Add new equation");
			System.out.println("[F] Change equation from history");
			System.out.println("[B] Print previous equation");
			System.out.println("[P] Print full history");
			System.out.println("[U] Undo");
			System.out.println("[R] Redo");
			System.out.println("[C] Clear history");
			System.out.println("[Q] Quit calculator");
			System.out.println("Select an option:");
			entd = rod.next();
			entd = entd.toUpperCase();
			if(entd.equals("Q")) {
				System.out.println("Program terminating successfully.");
				break;
			}
			else if(entd.equals("A")) {
				System.out.println("Please enter an equation (in-fix notation)"
						+ "");
				rod.nextLine();
				String inf = rod.nextLine();
				System.out.println();
				Equation newEquation = new Equation(inf, calcMemory.size()+1);
				calcMemory.push(newEquation);
				if(newEquation.isBalanced())
					System.out.printf("The equation is balanced and the answer "
							+ "is %.3f\n", newEquation.getAnswer());
				else
					System.out.println("The equation is not balanced.");
			}
			else if(entd.equals("F")) {// NEED TO CHANGE 
				System.out.println("Which equation would you like to change?");
				int eqpos = rod.nextInt();// THIS WILL ONLY TAKE IN HUMAN READABLE POSITIONS
				
				try {
					Equation curEq=calcMemory.getEquation(eqpos);
					System.out.println("Equation at position" + eqpos+": "+
					curEq.getOgEquation());
					String repl = curEq.getOgEquation();
					boolean moreChange = true;
					while(moreChange) {
						System.out.println("What would you like to do with this"
								+ " equation (Replace, remove, add)?");
						String action = rod.next();
						action = action.toUpperCase();
						if(action.equals("REPLACE")) {
							System.out.println("What position would you like to"
									+ " change?");
							int posToChange = rod.nextInt();
							posToChange = posToChange-1;//so in [0,length-1] form
							if(posToChange<0||posToChange>=repl.length())
								System.out.println("Position out of bounds to c"
										+ "hange.");
							else {
								System.out.println("What would you like to repl"
										+ "ace it with?");
								String novel = rod.next();
								repl = repl.substring(0,posToChange)+novel+repl
										.substring(posToChange+1);
								System.out.println();
								System.out.println("Equation: "+repl);
							}
						}
						else if(action.equals("REMOVE")) {
							System.out.println("What position would you like to"
									+ " remove?");
							int posToChange = rod.nextInt();
							posToChange = posToChange-1;//so in [0,length-1] form
							if(posToChange<0||posToChange>=repl.length())
								System.out.println("Position out of bounds to r"
										+ "emove.");
							else {
								repl = repl.substring(0,posToChange)+repl.
										substring(posToChange+1);
								System.out.println();
								System.out.println("Equation: " + repl);
							}
						}
						else if(action.equals("ADD")) {
							System.out.println("What position would you like to"
									+ " add to?");
							int posToChange = rod.nextInt();
							posToChange = posToChange-1;
							if(posToChange>repl.length()-1) {
								System.out.println("Position out of bounds to "
										+ "add.");
							}
							else {
								System.out.println("What would you like to add?"
										+ "");
								String addendum = rod.next();
								repl = repl.substring(0,posToChange) + addendum 
										+ repl.substring(posToChange);
								System.out.println();
								System.out.println("Equation: " + repl);
							}
						}
						System.out.println("Would you like to make any more cha"
								+ "nges?");
						String yesOrNo = rod.next();
						yesOrNo=yesOrNo.toUpperCase();
						while(!yesOrNo.equals("YES")&&!yesOrNo.equals("Y")&&!
								yesOrNo.equals("NO")&&!yesOrNo.equals("N")) {
							System.out.println("Please enter a valid response"
									+ " (Yes, no, y, n - case insensitive)");
							yesOrNo = rod.next();
							yesOrNo = yesOrNo.toUpperCase();
						}
						if(yesOrNo.equals("YES")||yesOrNo.equals("Y")) {
							moreChange=true;
							continue;
						}
						else {
							moreChange=false;
							break;
						}		
					}
					Equation newEquation = new Equation(repl, calcMemory.size()
							+1);
					if(newEquation.isBalanced()) {
						System.out.printf("The equation is balanced and the ans"
								+ "wer is %.3f\n", newEquation.getAnswer());
					}
					else
						System.out.println("The new equation is not balanced.");
					calcMemory.push(newEquation);
					//HERE, SAY WHETHER NEW EQUATION IS BALANCED AND WHAT THE ANSWER IS. ADD IT TO THE HISTORY STACK NOW
				}
				catch(InvalidStackPositionException e) {
					System.out.println(e.getMessage());
				}
			}
			else if(entd.equals("B")) {
				if(calcMemory.isEmpty()) {
					System.out.println("No equations to print.");
				}
				else {
					System.out.println(calcMemory.peek().toString());
				}
			}
			else if(entd.equals("P")) {
				System.out.println(calcMemory.toString());
			}
			else if(entd.equals("U")) {
				if(calcMemory.isEmpty()) {
					System.out.println("There is no equation to undo.");
				}
				else {
					Equation cur = calcMemory.peek();
					calcMemory.undo();
					System.out.println("Equation '"+cur.getEquation()+"' has be"
							+ "en undone.");
				}
			}
			else if(entd.equals("R")) {
				try {
					calcMemory.redo();
					System.out.println("Redoing equation '"+calcMemory.peek().
							getEquation()+"'.");
				} catch (NoLastUndoneException e) {
					System.out.println(e.getMessage());
				}
				
			}
			else if(entd.equals("C")){
				System.out.println("Restting calculator.");
				calcMemory = new HistoryStack();
			}
		}
	}
}
