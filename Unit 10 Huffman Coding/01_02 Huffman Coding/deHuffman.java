// Name: J2-07-23
// Date: 05/11/2023
 
import java.util.*;
import java.io.*;
public class deHuffman
{
   public static void main(String[] args) throws IOException
   {
      Scanner keyboard = new Scanner(System.in);
      System.out.print("\nWhat binary message (middle part)? ");
      String middlePart = keyboard.next();
      Scanner sc = new Scanner(new File("message."+middlePart+".txt")); 
      String binaryCode = sc.next();
      Scanner huffLines = new Scanner(new File("scheme."+middlePart+".txt")); 
      	
      TreeNode root = huffmanTree(huffLines);
      String message = dehuff(binaryCode, root);
      System.out.println(message);
      
      sc.close();
      huffLines.close();
   }
   public static TreeNode huffmanTree(Scanner huffLines) {
      TreeNode root = new TreeNode("");
      ArrayList<String> scanArr = new ArrayList<String>();
      while(huffLines.hasNextLine())
         scanArr.add(huffLines.nextLine());
      
      for(String ln : scanArr){
         TreeNode pos = root;
         for(char i : ln.toCharArray()){
            if(i == '0'){
               if(pos.getLeft() == null)
                  pos.setLeft(new TreeNode(""));
            }
            else if(i == '1'){
               if(pos.getRight() == null)
                  pos.setRight(new TreeNode(""));
            }
            pos = (i == '0') ? pos.getLeft() : ((i == '1') ? pos.getRight() : pos);
         }
         pos.setValue(ln.substring(0, 1));
      }
      return root;
   }

   public static String dehuff(String text, TreeNode root) {
      TreeNode pos = root;
      String ret = "";
      for(char c : text.toCharArray()){
         if(c == '0'){
            boolean left = pos.getLeft() == null;
            if(left)
               ret += pos.getValue();
            pos = left ? root.getLeft() : pos.getLeft();
         } 
         else if(c == '1'){
            boolean right = pos.getRight() == null;
            if(right)
               ret += pos.getValue();
            pos = right ? root.getRight() : pos.getRight();
         }
      }
      return ret + pos.getValue();
   }
}

 /* TreeNode class for the AP Exams */
class TreeNode
{
   private Object value; 
   private TreeNode left, right;
   
   public TreeNode(Object initValue)
   { 
      value = initValue; 
      left = null; 
      right = null; 
   }
   
   public TreeNode(Object initValue, TreeNode initLeft, TreeNode initRight)
   { 
      value = initValue; 
      left = initLeft; 
      right = initRight; 
   }
   
   public Object getValue()
   { 
      return value; 
   }
   
   public TreeNode getLeft() 
   { 
      return left; 
   }
   
   public TreeNode getRight() 
   { 
      return right; 
   }
   
   public void setValue(Object theNewValue) 
   { 
      value = theNewValue; 
   }
   
   public void setLeft(TreeNode theNewLeft) 
   { 
      left = theNewLeft;
   }
   
   public void setRight(TreeNode theNewRight)
   { 
      right = theNewRight;
   }
}
