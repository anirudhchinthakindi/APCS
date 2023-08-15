// Name: J2-07-23
// Date: 02/15/23
/*  Represents a binary expression tree.
 *  The BXT builds itself from postorder expressions. It can
 *  evaluate and print itself.  Also prints inorder and postorder strings. 
 */
 
import java.util.*;

public class BXT
{
   public static final String operators = "+ - * / % ^ !";
   private TreeNode root;   
   
   public BXT()
   {
      root = null;
   }
   public TreeNode getRoot()   //for Codepost
   {
      return root;
   }
   public void buildTree(String str)
   {
      Stack<TreeNode> stk = new Stack<TreeNode>();
      for(String s: str.split(" ")){
         if(!isOperator(s))
            stk.push(new TreeNode(s)); 
         else{
            TreeNode right = stk.pop(); 
            TreeNode left = stk.pop(); 
            stk.push(new TreeNode(s, left, right));
         }
      }
      root = stk.pop();
   }
   
   public double evaluateTree()
   {
      return evaluateNode(root);
   }
   
   private double evaluateNode(TreeNode t)  //recursive
   {
      String val = t.getValue().toString();
      if(isOperator(val))
         return computeTerm(val, evaluateNode(t.getLeft()), evaluateNode(t.getRight()));
      else
         return Double.parseDouble(val);
   }
   
   private double computeTerm(String s, double a, double b)
   {
      if(s.equals("+"))
         return a + b;
      else if(s.equals("-"))
         return a - b;
      else if(s.equals("*"))
         return a * b;
      else if(s.equals("/"))
         return a / b; 
      else if(s.equals("&"))
         return a % b;
      else
         return 0;
   }
   
   private boolean isOperator(String s)
   {
      return operators.indexOf(s) != -1;
   }
   
   public String display()
   {
      return display(root, 0);
   }
   
   private String display(TreeNode t, int level)
   {
      if(t == null)
         return "";
      String toRet = "";
      toRet += display(t.getRight(), level + 1); //recurse right
      for(int k = 0; k < level; k++)
         toRet += "\t";
      toRet += t.getValue() + "\n";
      toRet += display(t.getLeft(), level + 1); //recurse left
      return toRet;
   }
    
   public String inorderTraverse()
   {
      return inorderTraverse(root);
   }
   
   private  String inorderTraverse(TreeNode t)
   {
      String toReturn = "";
      if(t == null)
         return toReturn;
      toReturn += inorderTraverse(t.getLeft());   //recurse left
      toReturn += t.getValue() + " ";
      toReturn += inorderTraverse(t.getRight());  //recurse right
      return toReturn;
   }
   
   public String preorderTraverse()
   {
      return preorderTraverse(root);
   }
   
   private String preorderTraverse(TreeNode t)
   {
      String toReturn = "";
      if(t == null)
         return toReturn;
      toReturn += t.getValue() + " ";              //process root
      toReturn += preorderTraverse(t.getLeft());   //recurse left
      toReturn += preorderTraverse(t.getRight());  //recurse right
      return toReturn;
   }
   
  /* extension */
   // public String inorderTraverseWithParentheses()
   // {
      // return inorderTraverseWithParentheses(root);
   // }
//    
   // private String inorderTraverseWithParentheses(TreeNode t)
   // {
      // return "";
   // }
}