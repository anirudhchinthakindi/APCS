// Name: J2-07-23
// Date: 01/19/2023

import java.util.*;

public class PostfixEval
{
   public static final String operators = "+ - * / % ^ !";
   
   public static void main(String[] args)
   {
      System.out.println("Postfix  -->  Evaluate");
      ArrayList<String> postfixExp = new ArrayList<String>();
      /*  build your list of expressions here  */
      postfixExp.add("3 4 5 * +");
      postfixExp.add("3 4 * 5 +");
      postfixExp.add("4 5 + 5 3 * -");
      postfixExp.add("3 4 + 5 6 + *");
      postfixExp.add("3 4 5 + * 2 - 5 /");
      postfixExp.add("8 1 2 * + 9 3 / -");
      postfixExp.add("2 3 ^");
      postfixExp.add("20 3 %");
      postfixExp.add("21 3 %");
      postfixExp.add("22 3 %");
      postfixExp.add("23 3 %");
      postfixExp.add("5 !");
      postfixExp.add("1 1 1 1 1 + + + + !");
      for( String pf : postfixExp )
      {
         System.out.println(pf + "\t\t" + eval(pf));
      }
   }
   
   public static double eval(String pf)
   {
      Stack<Double> doubles = new Stack<Double>();
      for(String s: pf.split(" ")){
         if(!isOperator(s)) // a regular number
            doubles.push(Double.parseDouble(s));
         else{
            if(s.equals(operators.charAt(operators.length()-1) + ""))
               doubles.push(eval(0.0, doubles.pop(), s)); // case for factorial
            else
               doubles.push(eval(doubles.pop(), doubles.pop(), s));
         }
      }
      return doubles.pop();
   }
   
   public static double eval(double a, double b, String op)
   {
      switch(op){
         case "+": 
            return b + a;
         case "-": 
            return b - a;
         case "/": 
            return b / a;
         case "*": 
            return b * a;
         case "%": 
            return b % a;
         case "^":
            return Math.pow(b, a);
         case "!":
            for(int i=(int)b-1;i>0; i--)
               b*=i;
            return b;
         default:
            return 0;
      }
   }
   
   public static boolean isOperator(String op)
   {
      return operators.contains(op);
   }
}

/**********************************************
Postfix  -->  Evaluate
 3 4 5 * +		23
 3 4 * 5 +		17
 10 20 + -6 6 * +		-6
 3 4 + 5 6 + *		77
 3 4 5 + * 2 - 5 /		5
 8 1 2 * + 9 3 / -		7
 2 3 ^		8
 20 3 %		2
 21 3 %		0
 22 3 %		1
 23 3 %		2
 5 !		120
 1 1 1 1 1 + + + + !		120
 
 
 *************************************/