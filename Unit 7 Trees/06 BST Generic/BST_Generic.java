// Name: J2-07-23
// Date: 03/06/2023

interface BST_Generic_interface<E>
{
   public int size();
   public TreeNode<E> getRoot() ;
   public boolean contains(E obj);
   public void add(E obj);         //does not balance
   public void addBalanced(E obj); //AVL
   public void remove(E obj);      //does not balance
   public E min();
   public E max();
   public String display();
   public String toString();
}

/*******************
Copy your BST code.  Implement generics.
If you skipped remove() and/or addBalanced(), just leave the method bodies empty.
**********************/
public class BST_Generic<E extends Comparable<E>> implements BST_Generic_interface<E>
{
   private TreeNode<E> root;
   private int size;
   public BST_Generic()
   {
      root = null;
      size = 0;
   }
   public int size()
   {
      return size;
   }
   public TreeNode<E> getRoot()   //accessor method
   {
      return root;
   }
   /***************************************
   @param s -- one string to be inserted
   ****************************************/
   public void add(E e) 
   {
      root = add(root, e);
      size++;
   }
   private TreeNode<E> add(TreeNode<E> t, E e) //recursive helper method
   {      
      if(t == null)
         return new TreeNode(e);
      else if(t.getValue().compareTo(e) >= 0)
         t.setLeft(add(t.getLeft(), e));
      else if(t.getValue().compareTo(e) < 0)
         t.setRight(add(t.getRight(), e));
      return t;  
   }
     /*************************
      Copy the display() method from TreeLab. 
      **********************/
   public String display()
   {
      return display(root, 0);
   }
   private String display(TreeNode t, int level) //recursive helper method
   {
      String ret = "";
      if(t == null)
         return "";
      ret += display(t.getRight(), level + 1); //recurse right
      for(int k = 0; k < level; k++)
         ret += "\t";
      ret += t.getValue() + "\n";
      ret += display(t.getLeft(), level + 1); //recurse left
      return ret;
   }
   
   public boolean contains(E e)
   {
      return contains(root, e);
   }
   private boolean contains(TreeNode<E> t, E e) //recursive helper method
   {
      if(t == null)
         return false;
      E val = t.getValue();
      
      if(e.equals(val))
         return true; // it's there
      else if(e.compareTo(val) > 0)
         return contains(t.getRight(), e); // go to right. go to higher values
      else if(e.compareTo(val) < 0)
         return contains(t.getLeft(), e); // go to left. go to lower values
      return false; // it's not there
   }
   
   public E min()
   {
      return min(root);
   }
   private E min(TreeNode<E> t)  //use iteration
   {
      if(t==null)
         return null;
      while(t.getLeft() != null)
         t = t.getLeft();
      return t.getValue();
   }
   
   public E max()
   {
      return max(root);
   }
   private E max(TreeNode<E> t)  //recursive helper method
   {
      if(t==null)
         return null;
      if(t.getRight() != null)
         return max(t.getRight());
      else
         return t.getValue();
   }
   
   public String toString()
   {
      return toString(root);
   }
   private String toString(TreeNode t)  //an in-order traversal.  Use recursion.
   {
      String ret = "";
      if(t == null)
         return ret;
      ret += toString(t.getLeft());   //recurse left
      ret += t.getValue() + " ";
      ret += toString(t.getRight());  //recurse right
      return ret;
   }
   /*  precondition:  target must be in the tree.
                      implies that tree cannot be null.
   */
   public void remove(E target)
   {
      root = remove(root, target);
      size--;
   }
   private TreeNode remove(TreeNode<E> top, E target)
   {
      TreeNode<E> targetNode = top;
      TreeNode<E> parent = null;
      while(top != null){
         if(target.compareTo(targetNode.getValue()) == 0){
            if(parent == null && targetNode.getLeft() == null && targetNode.getRight() == null) // Case 1b
               return null;
            if(targetNode.getLeft() == null && targetNode.getRight() == null){ // Case 1a
               if(parent.getLeft() == targetNode)
                  parent.setLeft(null);
               else if(parent.getRight() == targetNode)
                  parent.setRight(null);
               return top;
            }
            if((targetNode.getLeft() != null && targetNode.getRight() == null) || (targetNode.getRight() != null && targetNode.getLeft() == null)){ // Case 2 | 1 Child
               if(parent == null && targetNode.getLeft() != null) // 2c
                  return targetNode.getLeft();
               else if(parent == null && targetNode.getRight() != null) // 2d
                  return targetNode.getRight();
               else if(parent.getLeft() == targetNode){ // 2a
                  if(targetNode.getLeft() != null)
                     parent.setLeft(targetNode.getLeft());
                  else
                     parent.setLeft(targetNode.getRight());
               }
               else if(parent.getRight() == targetNode){ // 2b
                  if(targetNode.getLeft() != null)
                     parent.setRight(targetNode.getLeft());
                  else
                     parent.setRight(targetNode.getRight());               
               }
               return top;
            }
            if(targetNode.getLeft() != null && targetNode.getRight() != null){ // Case 3 || 2 Children
               TreeNode<E> pointerParent = targetNode;
               TreeNode<E> pointer = targetNode.getLeft();
               while(pointer.getRight() != null){
                  pointerParent = pointer;
                  pointer = pointer.getRight();
               }
               targetNode.setValue(pointer.getValue());
               if(pointerParent != targetNode)
                  pointerParent.setRight(pointer.getLeft());
               else
                  pointerParent.setLeft(pointer.getLeft());
               return top;
            }
         }
         else{
            parent = targetNode;
            if(target.compareTo(targetNode.getValue()) < 0)
               targetNode = targetNode.getLeft();
            else
               targetNode = targetNode.getRight();
         }
      }
      return targetNode;
   }
   /*  start the addBalanced methods */
   private int calcBalance(TreeNode current) //height to right minus 
   {                                    //height to left
      if (current == null)
         return 0;
      else
         return height(current.getRight()) - height(current.getLeft());
   }

   private int height(TreeNode t)   //from TreeLab
   {
      if (t == null) 
         return -1;
      return 1 + Math.max(height(t.getLeft()), height(t.getRight()));
   }

   public void addBalanced(E value)  
   {
      add(value);
      root = balanceTree(root, value);   // for an AVL tree.  You may change this line.
   }
   
   private TreeNode<E> balanceTree(TreeNode<E> current, E e)  //recursive.  Whatever makes sense.
   {
      if(current == null) 
         return null;
      if(current.getValue().compareTo(e) >= 0)
         current.setLeft(balanceTree(current.getLeft(), e));
      else
         current.setRight(balanceTree(current.getRight(), e));
      
      if(calcBalance(current) > 1) // right heavy
         return (calcBalance(current.getRight()) < 0) ? rightToLeft(current) : toLeft(current);
      else if(calcBalance(current) < -1) // left heavy
         return (calcBalance(current.getLeft()) > 0) ? leftToRight(current) : toRight(current);
      return current;
   }
   
   // 4 rotation methods for add
   private TreeNode toLeft(TreeNode current) 
   {
      TreeNode cr = current.getRight(); 
      TreeNode crl = cr.getLeft();
      current.setRight(crl);
      cr.setLeft(current); 
      return cr;
   }
   private TreeNode toRight(TreeNode current) 
   {
      TreeNode cl = current.getLeft(); 
      TreeNode clr = cl.getRight();
      current.setLeft(clr);
      cl.setRight(current);
      return cl;
   }
   private TreeNode leftToRight(TreeNode current) 
   {
      current.setLeft(toLeft(current.getLeft()));
      return toRight(current);
   }
   private TreeNode rightToLeft(TreeNode current) 
   {
      current.setRight(toRight(current.getRight()));
      return toLeft(current);
   }
}

/*******************
  Copy your TreeNode code.  Implement generics.
**********************/
class TreeNode<E>
{
   private E value; 
   private TreeNode<E> left, right;
   
   public TreeNode(E initValue)
   { 
      value = initValue; 
      left = null; 
      right = null; 
   }
   
   public TreeNode(E initValue, TreeNode initLeft, TreeNode initRight)
   { 
      value = initValue; 
      left = initLeft; 
      right = initRight; 
   }
   
   public E getValue()
   { 
      return value; 
   }
   
   public TreeNode<E> getLeft() 
   { 
      return left; 
   }
   
   public TreeNode<E> getRight() 
   { 
      return right; 
   }
   
   public void setValue(E theNewValue) 
   { 
      value = theNewValue; 
   }
   
   public void setLeft(TreeNode<E> theNewLeft) 
   { 
      left = theNewLeft;
   }
   
   public void setRight(TreeNode<E> theNewRight)
   { 
      right = theNewRight;
   }
}