// Name: J2-07-23
// Date: 02-09-23

import java.util.*;

public class TreeLab
{
   public static TreeNode root = null;
   public static String s = "XCOMPUTERSCIENCE";
   //public static String s = "XThomasJeffersonHighSchool"; 
   //public static String s = "XAComputerScienceTreeHasItsRootAtTheTop";
   //public static String s = "XA";   //comment out lines 44-46 below
   //public static String s = "XAF";  //comment out lines 44-46 below
   //public static String s = "XAFP";  //comment out lines 44-46 below
   //public static String s = "XDFZM";  //comment out lines 44-46 below 
   
   public static void main(String[] args)
   {
      root = buildTree( s );  //we are building trees of Strings only!
      System.out.print( display( root, 0) );
   
      System.out.print("\nPreorder: " + preorderTraverse(root));
      System.out.print("\nInorder: " + inorderTraverse(root));
      System.out.print("\nPostorder: " + postorderTraverse(root));
   
      System.out.println("\n\nNodes = " + countNodes(root));
      System.out.println("Leaves = " + countLeaves(root));
      System.out.println("Only children = " + countOnlys(root));
      System.out.println("Grandparents = " + countGrandParents(root));
   
      System.out.println("\nHeight of tree = " + height(root));
      System.out.println("Longest path = " + longestPath(root));
      System.out.println("Min = " + min(root));
      System.out.println("Max = " + max(root));	
   
      System.out.println("\nBy Level: ");
      System.out.println(displayLevelOrder(root));
   }
 
 /*  students, do not try to understand this method.
     */
   public static TreeNode buildTree(String s)
   {
      root = new TreeNode("" + s.charAt(1), null, null);
      for(int pos = 2; pos < s.length(); pos++)
         insert(root, "" + s.charAt(pos), pos, 
            (int)(1 + Math.log(pos) / Math.log(2)));
   
      insert(root, "AAA", 17, 5); 
      insert(root, "BBB", 18, 5); 
      insert(root, "CCC", 37, 6); //BBB's right child
      return root;
   }
   
    /*  students, do not try to understand this method.
     */
   public static void insert(TreeNode t, String s, int pos, int level)
   {
      TreeNode p = t;
      for(int k = level - 2; k > 0; k--)
      {
         if((pos & (1 << k)) == 0)
            p = p.getLeft();
         else
            p = p.getRight();
      }
      if((pos & 1) == 0)
         p.setLeft(new TreeNode(s, null, null));
      else
         p.setRight(new TreeNode(s, null, null));
   }
   
// tilt your head towards your left shoulder to see the tree
   public static String display(TreeNode t, int level)
   {
      String toRet = "";
      if(t == null)
         return "";
      toRet += display(t.getRight(), level + 1); //recurse right
      for(int k = 0; k < level; k++)
         toRet += "\t";
      toRet += t.getValue() + "\n";
      toRet += display(t.getLeft(), level + 1); //recurse left
      return toRet;
   }
   
   public static String preorderTraverse(TreeNode t)
   { 
      String toReturn = "";
      if(t == null)
         return toReturn;
      toReturn += t.getValue() + " ";              //process root
      toReturn += preorderTraverse(t.getLeft());   //recurse left
      toReturn += preorderTraverse(t.getRight());  //recurse right
      return toReturn;
   }
   
   public static String inorderTraverse(TreeNode t)
   {
      String toReturn = "";
      if(t == null)
         return toReturn;
      toReturn += inorderTraverse(t.getLeft());   //recurse left
      toReturn += t.getValue() + " ";
      toReturn += inorderTraverse(t.getRight());  //recurse right
      return toReturn;
   }
   
   public static String postorderTraverse(TreeNode t)
   {
      String toReturn = "";
      if(t == null)
         return "";
      toReturn += postorderTraverse(t.getLeft());   //recurse left
      toReturn += postorderTraverse(t.getRight());  //recurse right
      toReturn += t.getValue() + " ";                            //process root
      return toReturn;
   }
   
   public static int countNodes(TreeNode t)
   {
      int left = 0;
      int right = 0;
      if(t == null)
         return 0;
      left += countNodes(t.getLeft());
      right += countNodes(t.getRight());
      return left+right+1;
   }
   
   public static int countLeaves(TreeNode t)
   {
      int left = 0;
      int right = 0;
      if(t == null)
         return 0;
      else if(t.getLeft() == null && t.getRight() == null){ //if it's a leaf
         left += countLeaves(t.getLeft());
         right += countLeaves(t.getRight());
         return left+right+1;
      }
      else{
         left += countLeaves(t.getLeft());
         right += countLeaves(t.getRight());
         return left+right;
      }
   }
   
   /*  hard way: use t.getLeft().getLeft(), etc.
       clever way:  use height(t)
       */
   public static int countGrandParents(TreeNode t)
   {
      int left = 0;
      int right = 0;
      if(t == null || t.getRight() == null && t.getLeft() == null)
         return 0;
      else if((t.getLeft() == null && t.getRight() != null) && (t.getRight().getRight() == null && t.getRight().getLeft() == null))
         return 0;
      else if((t.getLeft() != null && t.getRight() == null) && (t.getLeft().getRight() == null && t.getLeft().getLeft() == null))
         return 0;
      else if((t.getLeft() != null && t.getRight() != null)&& (t.getRight().getRight() == null && t.getRight().getLeft() == null && t.getLeft().getLeft() == null && t.getLeft().getRight() == null))
         return 0;
      else{
         left += countGrandParents(t.getLeft());
         right += countGrandParents(t.getRight());
         return left+right+1;
      }
   }
   
   public static int countOnlys(TreeNode t)
   {
      int left = 0;
      int right = 0;
      if(t == null)
         return 0;
      else if(t.getLeft() != null && t.getRight() == null){
         right += countOnlys(t.getLeft()) + 1;
         return right;
      }
      else if(t.getLeft() == null && t.getRight() != null){
         left += countOnlys(t.getRight()) + 1;
         return left;
      }
      else{
         left += countOnlys(t.getLeft());
         right += countOnlys(t.getRight());
         return left + right;
      }
   }
   
  /** returns the max of the heights to the left and the heights to the right  
      returns -1 in case the tree is null
    */
   public static int height(TreeNode t)
   {
      int left = 0;
      int right = 0;
      if(t == null)
         return -1;
      else{
         left += height(t.getLeft()) + 1;
         right += height(t.getRight()) + 1;
         return Math.max(left, right);
      }
   }
   
   /* The length of the longest path connecting any two nodes.  
      Usually connects two bottom-most leaves in the tree.  
      Often goes through root, but not always. 
   */
   public static int longestPath(TreeNode t)
   {
      return longestPathHelper(t) + 2;
   }
   
   public static int longestPathHelper(TreeNode t)
   {
      int left = 0;
      int right = 0;
      int rootPath = 0;
      if(t == null)
         return 0;
      else{
         rootPath += height(t.getLeft()) + height(t.getRight());
         left += longestPathHelper(t.getLeft());
         right += longestPathHelper(t.getRight());
         int biggest = Math.max(left, right);
         return Math.max(rootPath, biggest);
      }
   }
   
   @SuppressWarnings("unchecked")//this removes the warning message
   /*  Objects in a TreeNode must be cast to String or Comparable 
           in order to call .compareTo  
       */
   public static String min(TreeNode t)
   {
      if (t == null)
         return "z";
      return min(t, t.getValue()+"");  //calls the private recursive method
   }
   private static String min(TreeNode t, String min)
   {
      String left = min(t.getLeft()).toString();
      String right = min(t.getRight()).toString();
      if(left.compareTo(min) < 0)
         min = left;
      if(right.compareTo(min) < 0)
         min = right;
      return min;
   }
   
   @SuppressWarnings("unchecked")//this removes the warning message
   /*  Objects in a TreeNode must be cast to String or Comparable 
           in order to call .compareTo  */
   public static String max(TreeNode t)
   {
      if (t == null)
         return "A";
      return max(t, t.getValue()+"");  //calls the private recursive method
   }
   
   private static String max(TreeNode t, String max)
   {
      String left = max(t.getLeft());
      String right = max(t.getRight());
      if(left.compareTo(max) > 0)
         max = left;
      if(right.compareTo(max) > 0)
         max = right;
      return max;
   }
      
  /* this method is not recursive.  Use a local queue
     to store the children, if they exist, of the current TreeNode.
     */
   public static String displayLevelOrder(TreeNode t)
   {      
      String ret = "";
      if(t == null) 
         return ret;
      Queue<TreeNode> q = new LinkedList<TreeNode>();
      q.add(t);
      while(!q.isEmpty())
         for(int i=0; i<q.size(); i++){
            TreeNode node = q.remove();
            ret += node.getValue() + " ";
            if(node.getLeft() != null) 
               q.add(node.getLeft());
            if(node.getRight() != null) 
               q.add(node.getRight());
         }
      return ret; 
   } 
}

/***************************************************
  
       			E
 		E
 			C
 	M
 			N
 		T
 			E
 C
 			I
 		U
 			C
 	O
 			S
 					CCC
 				BBB
 		P
 				AAA
 			R
 
 Preorder: C O P R AAA S BBB CCC U C I M T E N E C E 
 Inorder: R AAA P BBB CCC S O C U I C E T N M C E E 
 Postorder: AAA R CCC BBB S P C I U O E N T C E E M C 
 
 Nodes = 18
 Leaves = 8
 Only children = 3
 Grandparents = 5
 
 Height of tree = 5
 Longest path = 8
 Min = AAA
 Max = U
 
 By Level: 
 C O M P U T E R S C I E N C E AAA BBB CCC 
     
     
     
     
     
     
 public static int height(TreeNode t)
   {
      return heightHelper(t) - 1;
   }
   
   public static int heightHelper(TreeNode t)
   {
      int left = 0;
      int right = 0;
      if(t == null)
         return -1;
      else{
         left = heightHelper(t.getLeft());
         right = heightHelper(t.getRight());
         return 1 + Math.max(left, right);
      }
   }
 /*******************************************************/