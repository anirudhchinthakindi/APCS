// Name: J2-07-23
// Date: 02/22/23

interface BSTinterface
{
   public int size();
   public TreeNode getRoot();
   public boolean contains(String obj);
   public void add(String obj);           //does not balance
   //public void addBalanced(String obj);  //AVL
   public void remove(String obj);    
   //public void removeBalanced(String obj); //extra lab
   public String min();
   public String max();
   public String display();
   public String toString();
}

/*******************
BST. Implement the remove() method.
Test it with BST_Remove_Driver.java
**********************/
public class BST implements BSTinterface
{
   /*  copy your BST code here  */
   private TreeNode root;
   private int size;
   public BST()
   {
      root = null;
      size = 0;
   }
   public int size()
   {
      return size;
   }
   public TreeNode getRoot()   //accessor method
   {
      return root;
   }
   /***************************************
   @param s -- one string to be inserted
   ****************************************/
   public void add(String s) 
   {
      root = add(root, s);
      size++;
   }
   private TreeNode add(TreeNode t, String s) //recursive helper method
   {      
      if(t == null)
         return new TreeNode(s);
      else if(t.getValue().toString().compareTo(s) >= 0)
         t.setLeft(add(t.getLeft(), s));
      else if(t.getValue().toString().compareTo(s) < 0)
         t.setRight(add(t.getRight(), s));
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
   
   public boolean contains(String s)
   {
      return contains(root, s);
   }
   private boolean contains(TreeNode t, String s) //recursive helper method
   {
      if(t == null)
         return false;
      String val = t.getValue().toString();
      
      if(s.equals(val))
         return true; // it's there
      else if(s.compareTo(val) > 0)
         return contains(t.getRight(), s); // go to right. go to higher values
      else if(s.compareTo(val) < 0)
         return contains(t.getLeft(), s); // go to left. go to lower values
      return false; // it's not there
   }
   
   public String min()
   {
      return min(root);
   }
   private String min(TreeNode t)  //use iteration
   {
      if(t==null)
         return null;
      while(t.getLeft() != null)
         t = t.getLeft();
      return t.getValue().toString();
   }
   
   public String max()
   {
      return max(root);
   }
   private String max(TreeNode t)  //recursive helper method
   {
      if(t==null)
         return null;
      if(t.getRight() != null)
         return max(t.getRight());
      else
         return t.getValue().toString();
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
   public void remove(String target)
   {
      root = remove(root, target);
      size--;
   }
   private TreeNode remove(TreeNode top, String target)
   {
      TreeNode targetNode = top;
      TreeNode parent = null;
      while(top != null){
         if(target.compareTo(targetNode.getValue().toString()) == 0){
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
               TreeNode pointerParent = targetNode;
               TreeNode pointer = targetNode.getLeft();
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
            if(target.compareTo(targetNode.getValue().toString()) < 0)
               targetNode = targetNode.getLeft();
            else
               targetNode = targetNode.getRight();
         }
      }
      return targetNode;
   }
}