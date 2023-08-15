// Name: J2-07-23
// Date: 01/24/2023
//uses PostfixEval

import java.util.*;
public class InfixPostfixEval
{
   public static final String LEFT  = "([{<";
   public static final String RIGHT = ")]}>";
   public static final String operators = "+ - * / % ^ !";
   
   public static void main(String[] args)
   {
      System.out.println("Infix  \t-->\tPostfix\t\t-->\tEvaluate");
      /*build your list of Infix expressions here  */
      List<String> infixExp = new ArrayList<>();
      infixExp.add("5 - 1 - 1");
      infixExp.add("5 - 1 + 1");
      infixExp.add("12 / 6 / 2");
      infixExp.add("3 + 4 * 5");
      infixExp.add("3 * 4 + 5");
      infixExp.add("1.3 + 2.7 + -6 * 6");
      infixExp.add("( 33 + -43 ) * ( -55 + 65 )");
      infixExp.add("8 + 1 * 2 - 9 / 3");
      infixExp.add("3 * ( 4 * 5 + 6 )");
      infixExp.add("3 + ( 4 - 5 - 6 * 2 )");
      infixExp.add("2 + 7 % 3");
      infixExp.add("( 2 + 7 ) % 3");
      
      for( String infix : infixExp )
      {
         String pf = infixToPostfix(infix);  //get the conversion to work first
         System.out.println(infix + "\t\t\t" + pf );  
         //System.out.println(infix + "\t\t\t" + pf + "\t\t\t" + eval(pf));  //PostfixEval must work!
      }
   }
   
   public static String infixToPostfix(String infix)
   {
      List<String> nums = new ArrayList<String>(Arrays.asList(infix.split(" ")));
      /* enter your code here  */
      String postFix = "";
      Stack<String> oper = new Stack<String>();
      for(String s: nums){
         if(!operators.contains(s) && !LEFT.contains(s) && !RIGHT.contains(s))
            postFix += s + " ";
         else if(LEFT.contains(s))
            oper.push(s);
         else if(RIGHT.contains(s)){
            while(!(oper.peek().equals(LEFT.substring(RIGHT.indexOf(s), RIGHT.indexOf(s)+1))))
               postFix += oper.pop() + " ";
            oper.pop();
         }
         else{
            while(!oper.empty() && operators.contains(s) && !isHigherOrEqual(s, oper.peek()))
               postFix += oper.pop() + " ";
            oper.push(s);
         }
      }
      while(!oper.empty())
         postFix += oper.pop() + " ";
      return postFix.trim();
   }
   
   public static boolean isHigherOrEqual(String next, String top)
   {
      if((next.equals("+") || next.equals("-")) && (top.equals("+") || top.equals("-")))
         return false;
      else if((next.equals("*") || next.equals("/") || next.equals("%")) && (top.equals("*") || top.equals("/") || top.equals("%")))
         return false;
      else if((next.equals("^") || next.equals("!")) && (top.equals("^") || top.equals("!")))
         return false;
      else if((next.equals("+") || next.equals("-")) && (top.equals("*") || top.equals("/") || top.equals("%") || top.equals("!") || top.equals("^")))
         return false;
      else if((next.equals("*") || next.equals("/") || next.equals("%")) && (top.equals("^") || top.equals("!")))
         return false;
      return true;
   }
}

/********************************************

Infix  	-->	Postfix		-->	Evaluate
 5 - 1 - 1			5 1 - 1 -			3.0
 5 - 1 + 1			5 1 - 1 +			5.0
 12 / 6 / 2			12 6 / 2 /			1.0
 3 + 4 * 5			3 4 5 * +			23.0
 3 * 4 + 5			3 4 * 5 +			17.0
 1.3 + 2.7 + -6 * 6			1.3 2.7 + -6 6 * +			-32.0
 ( 33 + -43 ) * ( -55 + 65 )			33 -43 + -55 65 + *			-100.0
 8 + 1 * 2 - 9 / 3			8 1 2 * + 9 3 / -			7.0
 3 * ( 4 * 5 + 6 )			3 4 5 * 6 + *			78.0
 3 + ( 4 - 5 - 6 * 2 )			3 4 5 - 6 2 * - +			-10.0
 2 + 7 % 3			2 7 3 % +			3.0
 ( 2 + 7 ) % 3			2 7 + 3 %			0.0
      
***********************************************/
