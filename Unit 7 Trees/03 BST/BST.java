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
   private TreeNode remove(TreeNode current, String target)
   {
      TreeNode r = current;
      TreeNode parent = null;
      while(current != null){
         if(target.compareTo(r.getValue().toString()) > 0){
            parent = r;
            r = r.getRight();
         }
         else if(target.compareTo(r.getValue().toString()) < 0){
            parent = r;
            r = r.getLeft();
         }
         else{ // c == 0
            if(parent == null && r.getLeft() == null && r.getRight() == null)
               return null;
            if(r.getLeft() == null && r.getRight() == null){ // Case 1
               if(parent.getLeft() == r)
                  parent.setLeft(null);
               else if(parent.getRight() == r)
                  parent.setRight(null);
               return current;
            }
            if((r.getLeft() != null && r.getRight() == null) || (r.getRight() != null && r.getLeft() == null)){ // Case 2
               if(parent == null && r.getLeft() != null)
                  return r.getLeft();
               else if(parent == null && r.getRight() != null)
                  return r.getRight();
               else if(parent.getLeft() == r){
                  if(r.getLeft() != null)
                     parent.setLeft(r.getLeft());
                  else
                     parent.setLeft(r.getRight());
               }
               else if(parent.getRight() == r){
                  if(r.getLeft() != null)
                     parent.setRight(r.getLeft());
                  else
                     parent.setRight(r.getRight());               
               }
               return current;
            }
            if(r.getLeft() != null && r.getRight() != null){ // Case 3
               TreeNode pointer = r.getLeft();
               TreeNode pointerParent = r;
               while(pointer.getRight() != null){
                  pointerParent = pointer;
                  pointer = pointer.getRight();
               }
               r.setValue(pointer.getValue());
               if(pointerParent != r)
                  pointerParent.setRight(pointer.getLeft());
               else
                  pointerParent.setLeft(pointer.getLeft());
               return current;
            }
         }     
      }
      return r;
   }
}