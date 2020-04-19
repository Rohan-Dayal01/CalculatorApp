/**
 * @author Rohan Dayal
 * @ID_Number 112768867
 * @Recitation 02
 */
 

package homework3;

import java.util.EmptyStackException;

/**
 * This class defines an Equation object. Equation object contains private
 * variables String equation (no spaces), String ogEquation (so spaces included
 * ), String prefix, String postfix, double answer, String binary, String hex,
 * boolean balanced, int eqNumber. 
 */
public class Equation {
	
	private String equation;
	private String ogEquation;
	private String prefix = "N/A";
	private String postfix = "N/A";
	private double answer = 0;
	private String binary = ""+0;
	private String hex = ""+0;
	private boolean balanced = true;
	private int eqNumber = 0;
	/**
	 * No arg constructor to create Equation object
	 */
	public Equation() {
		
	}
	/**
	 * setEqNumber is a setter method for eqNumber
	 * @param n is the number which eqNumber will be set to
	 */
	public void setEqNumber(int n) {
		eqNumber = n;
	}
	/**
	 * Getter method for eqNumber
	 * @return eqNumber - the number assigned to this specific equation object
	 */
	public int getNumber() {
		return eqNumber;
	}
	/**
	 * Constructor for equation with a given equation String input equ
	 * @param equ String which will be set equal to ogEquation.
	 * equation is set equal to this value with all spaces removed
	 */
	public Equation(String equ) {
		ogEquation = equ;
		equation = ogEquation.replaceAll("\\s", "");
		balanced = this.isBalanced();
		postfix = this.generatePostfix();
		prefix = this.generatePrefix();
		answer = this.getAnswer();
		binary = this.getBinary();
		hex = this.getHex();
	}
	/**
	 * Constructor for equation with given equation String input and equation 
	 * number.
	 * @param equ is the String ogEquation will be set equal to
	 * @param num is the int eqNumber will be set equal to
	 */
	public Equation(String equ, int num) {
		ogEquation = equ;
		equation = ogEquation.replaceAll("\\s", "");
		answer = this.getAnswer();
		balanced = this.isBalanced();
		postfix = this.generatePostfix();
		prefix = this.generatePrefix();
		binary = this.getBinary();
		hex = this.getHex();
		eqNumber = num;
	}
	/**
	 * Method for determining whether an input equation is balanced or not.
	 * Utilizes equation attribute of this Equation
	 * @return false if has already been declared as false, as that would
	 * happen if there was an error in calculation. If this has not occurred,
	 * then returns true if parenthesis balanced, false if not balanced.
	 */
	public boolean isBalanced() {
		if(balanced==false)//if already tripped, should return false;
			return false;
		EquationStack parenStack = new EquationStack();
		for(int x=0;x<equation.length();x++) {
			if(equation.charAt(x)=='(')
				parenStack.push("(");
			else if(equation.charAt(x)==')'){
				if(parenStack.isEmpty())
					return false;
				parenStack.pop();
			}
		}
		if(parenStack.isEmpty())
			return true;
		else
			return false;
	}
	/**
	 * Method to generate prefix format of equation. Utilizes generatePostfix
	 * @return String representing Prefix of this equation. Also sets
	 * prefix attribute equal to this String
	 */
	public String generatePrefix() {
		if(this.isBalanced()==false) {
			return "N/A";
		}
		Equation revved = new Equation();
		revved.setEquation(this.getEquation());
		String reverse = "";
		for(int x=revved.getEquation().length();x>0;x--) {
			if(this.getEquation().substring(x-1,x).equals("("))
				reverse = reverse+")";
			else if(this.getEquation().substring(x-1,x).equals(")"))
				reverse = reverse+"(";
			else
				reverse = reverse+this.getEquation().substring(x-1,x);
		}
		revved.setEquation(reverse);
		String revPref = revved.generatePostfix();
		String pref="";
		for(int x=revPref.length();x>0;x--) {
			pref = pref+ revPref.substring(x-1, x);
		}
		prefix = pref;
		return pref;
	}
	
	
	/**
	 * This method generates Postfix representation of this equation.
	 * @return a String representation of the Postfix form of this equation
	 * Also sets postfix equal to this String
	 */
	public String generatePostfix() { 
		if(this.isBalanced()==false)
			return "N/A";
		String posty = "";
		EquationStack stack = new EquationStack();
		for(int x=0;x<equation.length();x++) {
			String symbolic = equation.substring(x,x+1);//Symbol at position x that we want to add to stack
			if(Character.isDigit(equation.charAt(x))) {//just putting digits into post
				//do a while statement to do while next position is a digit. Also, as we concat, use continue function to increment x
				String conc = equation.charAt(x)+"";
				if(x!=equation.length()-1)
					while(Character.isDigit(equation.charAt(x+1))) {
						conc = conc+equation.charAt(x+1);
						x++;
						if(x==equation.length()-1)
							break;
					}
				posty = posty+conc+" ";
			}
			else if(symbolic.contains("-")||symbolic.contains("+")||symbolic.
					contains("/")
					||symbolic.contains("*")||symbolic.contains("%")||symbolic.
					contains("^")) {
				if(stack.isEmpty()) {
					stack.push(equation.charAt(x)+"");
				}
				else {
					if(stack.isLowOrEqualPrecedence(symbolic)){
						while(stack.isLowOrEqualPrecedence(symbolic)) {
							
							String temp = stack.pop();
							
							posty = posty + temp;
						}
						stack.push(symbolic);
					}
					else {
						stack.push(symbolic);
					}
				}
			}
			else if(symbolic.contains("(")) {
				stack.push("(");
			}
			else if(symbolic.contains(")")){				
				String sneakPeek = (String)stack.peek();
				while(!sneakPeek.contains("(")) {
					String temp = stack.pop();
					posty = posty+temp+" ";
					if(!stack.isEmpty())
						sneakPeek=(String)stack.peek();
					else
						continue;
				}
				stack.pop();
			}
			else if(equation.charAt(x)==' ')
				continue;
			else {//when something that was entered was not a number of operator
				balanced = false;
				hex = ""+0;
				binary = ""+0;
				answer = 0;
				prefix = "N/A";
				postfix = "N/A";
				return "N/A";
			}	
		}
		//now, just finish popping remainder of operator stack and add to the end
		while(stack.size()!=0) {
			String temp = stack.pop();
			if(!temp.equals("("))
				posty = posty + temp+" ";
			
			}
		postfix = posty;
		return posty;
	}
	 

	/**
	 * Void method to compute the answer to this given equation
	 * this double is set equal to answer attribute of this equation
	 * @exception EmptyStackException e is handled so to not allow program to 
	 * crash due to one seeking to peek or pop from an empty stack	
	 */
	public void computeAnswer() {
		try {
		EquationStack answering = new EquationStack();
		String post = this.generatePostfix();
		this.generatePrefix();
		for(int x=0;x<post.length();x++) {
			if(answering.isEmpty()) {
				if(Character.isDigit(post.charAt(x))) {
					String numStr=post.charAt(x)+"";
					while(Character.isDigit(post.charAt(x+1))) {
						numStr = numStr+post.charAt(x+1);
						x++;
						if(x==post.length()-1)
							break;	
					}
					answering.push(numStr);
				}
				else {
					System.out.println("Error - no numbers in stack but operato"
							+ "r encountered");
					return;
				}
			}
			else {//when stack is not empty
				if(Character.isDigit(post.charAt(x))) {
					String numStr=post.charAt(x)+"";
					while(Character.isDigit(post.charAt(x+1))) {
						numStr = numStr+post.charAt(x+1);
						x++;
						if(x==post.length()-1)
							break;	
					}
					answering.push(numStr);
				}
				else if(post.charAt(x)=='+'){//case where we have an operator.
					double operand1 = Double.parseDouble(answering.pop());
					double operand2 = Double.parseDouble(answering.pop());
					answering.push(String.valueOf(operand1+operand2));
				}
				else if(post.charAt(x)=='^') {
					double operand1 = Double.parseDouble(answering.pop());
					double operand2 = Double.parseDouble(answering.pop());
					answering.push(String.valueOf(Math.pow(operand2, operand1)));
				}
				else if(post.charAt(x)=='%') {
					double operand1 = Double.parseDouble(answering.pop());
					double operand2 = Double.parseDouble(answering.pop());
					answering.push(String.valueOf(operand2%operand1));
				}
				else if(post.charAt(x)=='*') {
					double operand1 = Double.parseDouble(answering.pop());
					double operand2 = Double.parseDouble(answering.pop());
					answering.push(String.valueOf(operand2*operand1));
				}
				else if(post.charAt(x)=='-') {
					double operand1 = Double.parseDouble(answering.pop());
					double operand2 = Double.parseDouble(answering.pop());
					answering.push(String.valueOf(operand2-operand1));
				}
				else if(post.charAt(x)=='/') {
					double operand1 = Double.parseDouble(answering.pop());
					double operand2 = Double.parseDouble(answering.pop());
					if(operand1==0.0) {
						System.out.println("Error, cannot divide by zero");
						balanced = false;
						prefix = "N/A";
						postfix = "N/A";
						answer=0;
						binary = 0+"";
						hex=0+"";
						return;
					}
					else answering.push(String.valueOf(operand2/operand1));
				}
				else//case where we have a whitespace
					continue;
			}
			
		}
		
		double ans= Double.parseDouble(answering.pop());
		this.answer = ans;
	}
		catch(EmptyStackException e){
			System.out.println("Please try again and enter an equation with val"
					+ "id format.");
			balanced = false;
			prefix = "N/A";
			postfix = "N/A";
			answer=0;
			binary = 0+"";
			hex=0+"";
		}
	}
	/**
	 * Method to convert decimal answer to binary.
	 * @return String of binary representation of answer and
	 * sets binary attribute equal to this String
	 */
	public String decToBin() {
		computeAnswer();
		int quo;
		if(this.answer%1.0>=0.5)
			quo = (int)Math.ceil(this.answer);
		else
			quo = (int)Math.floor(this.answer);
		String bin="";
		while(quo!=0) {
			bin = quo%2 + bin;
			quo/=2;
		}
		binary = bin;
		return bin;
	}
	/**
	 * Method to convert decimal answer to hexadecimal.
	 * @return String of hexadecimal representation of answer and sets
	 * hex attribute equal to this String
	 */
	public String decToHex() {
		computeAnswer();
		int quo;
		if(this.answer%1.0>=0.5)
			quo = (int)Math.ceil(this.answer);
		else
			quo = (int)Math.floor(this.answer);
		String hexy = "";
		while(quo!=0) {
			if(quo%16<10) {
				hexy = quo%16 + hexy;
			}
			else if(quo%16>9) {
				hexy = (char)(quo%16+55) + hexy;
			}
			quo/=16;
		}
		hex = hexy;
		return hexy;
	}
	
	/**
	 * toString method for an Equation object
	 * @return a String representing the calling Equation object
	 */
	public String toString() {
		String equ =String.format("%-10s%-30s%-30s%-30s%-30s%-30s%-30s\n",
				"#", "Equation", "Prefix", "Postfix", "Answer", "Binary", "Hexa"
						+ "decimal");
		equ = equ+"-----------------------------------------"
				+ "------------------------------------------------------------"
				+ "------------------------------------------------------------"
				+ "-----------------"
				+ "-------\n";
		equ = equ + String.format("%-10d%-30s%-30s%-30s%-30.3f%-30s%-30s\n",
				eqNumber, ogEquation, this.getPrefix(), this.getPostfix(), 
				this.getAnswer(), this.getBinary(), this.getHex());
		return equ;
	}
	/**
	 * toString method sans header so that it can easily be called when 
	 * printing the HistoryStack
	 * @return String representing this equation w/out header
	 */
	public String toStringForMore() {
		String equ = String.format("%-10d%-30s%-30s%-30s%-30.3f%-30s%-30s\n",
				eqNumber, ogEquation, this.getPrefix(), this.getPostfix(),
				this.getAnswer(), this.getBinary(), this.getHex());
		return equ;
	}
	/**
	 * Getter method for equation
	 * @return String equation
	 */
	public String getEquation() {
		return equation;
	}
	/**
	 * Getter method for ogEquation
	 * @return String ogEquation
	 */
	public String getOgEquation() {
		return this.ogEquation;
	}
	/**
	 * Setter method for ogEquation and equation
	 * @param str is what ogEquation is set equal to. So is equation,
	 * but whitespaces are removed
	 */
	public void setEquation(String str) {
		ogEquation = str;
		equation = ogEquation.replaceAll("\\s", "");
	}
	/**
	 * Getter method for prefix
	 * @return String prefix
	 */
	public String getPrefix() {
		this.generatePrefix();
		return prefix;
	}
	/**
	 * Setter method for prefix
	 * @param pref is what prefix is set equal to
	 */
	public void setPrefix(String pref) {
		prefix = pref;
	}
	/**
	 * Getter method for postfix attribute
	 * @return String postfix
	 */
	public String getPostfix() {
		//generatePostfix();
		return postfix;
	}
	/**
	 * Setter method for postfix
	 * @param post is what postfix is set equal to
	 */
	public void setPostfix(String post) {
		postfix = post;
	}
	/**
	 * Getter method for answer
	 * @return double answer
	 * If equation not balanced, returns 0.0
	 */
	public double getAnswer() {
		if(this.isBalanced()==false)
			return 0.0;
		computeAnswer();
		return answer;
	}
	/**
	 * Setter method for answer
	 * @param answer is what this.answer is set equal to
	 */
	public void setAnswer(double answer) {
		this.answer = answer;
	}
	/**
	 * Setter method for binary
	 * @param binary is String which this.binary is set equal to
	 */
	public void setBinary(String binary) {
		this.binary = binary;
	}
	/**
	 * Getter method for binary
	 * @return String binary
	 * If calling Equation is not balanced, returns "0"
	 */
	public String getBinary() {
		if(this.isBalanced()==false)
			return 0+"";
		this.decToBin();
		return binary;
	}
	/**
	 * Setter method for hex
	 * @param hex is what this.hex is set to
	 */
	public void setHex(String hex) {
		
		this.hex = hex;
	}
	/**
	 * Getter method for hex
	 * @return String hex
	 * If calling Equation is not balanced, return "0"
	 */
	public String getHex() {
		if(this.isBalanced()==false)
			return 0+"";
		this.decToHex();
		return this.hex;
	}
	/**
	 * Setter method for equation number
	 * @param n is what eqNumber is set to equal
	 */
	public void setNumber(int n) {
		eqNumber =n;
	}
}
