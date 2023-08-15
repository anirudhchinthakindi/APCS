// Name: J2-07-23
// Date: 05/16/2023
import java.util.*;
import java.io.*;
public class Huffman
{
   public static Scanner keyboard = new Scanner(System.in);
   public static void main(String[] args) throws IOException
   {
      //Prompt for two strings 
      System.out.print("Encoding using Huffman codes");
      System.out.print("\nWhat message? ");
      String message = keyboard.nextLine();
   
      System.out.print("\nEnter middle part of filename:  ");
      String middlePart = keyboard.next();
   
      huffmanize( message, middlePart );
   }
   
   public static void huffmanize(String message, String middlePart) throws IOException
   {
      Map<String, Integer> frequencyTable = new HashMap<>();
      for(char letter : message.toCharArray())
         frequencyTable.put(letter + "", frequencyTable.getOrDefault(letter + "", 0) + 1);
      
      List<HuffmanTreeNode> nodes = new ArrayList<>();
      for(Map.Entry<String, Integer> entry : frequencyTable.entrySet())
         nodes.add(new HuffmanTreeNode(entry.getKey(), entry.getValue()));
      
      PriorityQueue<HuffmanTreeNode> pq = new PriorityQueue<>(nodes);
   
      while(pq.size() > 1)
         pq.add(new HuffmanTreeNode(pq.remove(), pq.remove()));
   
      HuffmanTreeNode root = pq.remove();
      String binaryMessage = "";
      for(char letter : message.toCharArray())
         binaryMessage += root.getPathForCharacter(letter + "");

      System.out.println("Binary Message: " + binaryMessage);
      
      try(PrintWriter writer = new PrintWriter(new FileWriter("message." + middlePart + ".txt"))) {
         writer.println(binaryMessage.toString());
      }
      
      System.out.println("Scheme from the Huffman Tree:");
      root.printScheme();
      
      try(PrintWriter writer = new PrintWriter(new FileWriter("scheme." + middlePart + ".txt"))) {
         root.writeSchemeToFile(writer);
      }
   }
}
	/*
	  * This tree node stores two values.  Look at TreeNode's API for some help.
	  * The compareTo method must ensure that the lowest frequency has the highest priority.
	  */
class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {
   private String letter;
   private Integer freq;
   private HuffmanTreeNode leftChild;
   private HuffmanTreeNode rightChild;

   public HuffmanTreeNode(String letter, int freq) {
      this.letter = letter;
      this.freq = freq;
   }

   public HuffmanTreeNode(HuffmanTreeNode leftChild, HuffmanTreeNode rightChild) {
      this.letter = "";
      this.freq = leftChild.getFreq() + rightChild.getFreq();
      this.leftChild = leftChild;
      this.rightChild = rightChild;
   }

   public boolean isLeaf(){
      return leftChild == null && rightChild == null;
   }

   public String getLetter(){
      return letter;
   }

   public int getFreq(){
      return freq;
   }

   public HuffmanTreeNode getLeftChild(){
      return leftChild;
   }

   public HuffmanTreeNode getRightChild(){
      return rightChild;
   }

   @Override
   public int compareTo(HuffmanTreeNode other){
      return Integer.compare(freq, other.getFreq());
   }

   public String getPathForCharacter(String c){
      if(isLeaf()) 
         return "";
   
      if(leftChild != null && leftChild.containsCharacter(c))
         return "0" + leftChild.getPathForCharacter(c); 
      else if(rightChild != null && rightChild.containsCharacter(c))
         return "1" + rightChild.getPathForCharacter(c);
   
      return "";
   }

   private boolean containsCharacter(String c){
      if(isLeaf())
         return letter.equals(c);
   
      return (leftChild != null && leftChild.containsCharacter(c)) || (rightChild != null && rightChild.containsCharacter(c));
   }

   public void printScheme(){
      printSchemeHelper("");
   }

   private void printSchemeHelper(String prefix){
      if(isLeaf())
         System.out.println(letter + ": " + prefix);
      else{
         if(leftChild != null)
            leftChild.printSchemeHelper(prefix + "0");
         if(rightChild != null)
            rightChild.printSchemeHelper(prefix + "1");
      }
   }

   public void writeSchemeToFile(PrintWriter writer) {
      writeSchemeToFileHelper(writer, "");
      writer.flush();
   }

   private void writeSchemeToFileHelper(PrintWriter writer, String prefix){
      if(isLeaf())
         writer.println(letter + ": " + prefix);
      else{
         if(leftChild != null)
            leftChild.writeSchemeToFileHelper(writer, prefix + "0");
         if(rightChild != null)
            rightChild.writeSchemeToFileHelper(writer, prefix + "1");
      }
   }
}
