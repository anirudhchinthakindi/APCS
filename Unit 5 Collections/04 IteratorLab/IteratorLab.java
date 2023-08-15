 // Name: J2-07-23
 // Date: 01/10/2023
 
 // use for-each loops or iterators, not regular for-loops
import java.io.*;
import java.util.*;
public class IteratorLab
{
   public static void main(String[] args)
   {
      System.out.println("Iterator Lab\n");
      int[] rawNumbers = {-9, 4, 2, 5, -10, 6, -4, 24, 20, -28};
      for(int n : rawNumbers )
         System.out.print(n + " ");    
      ArrayList<Integer> numbers = createNumbers(rawNumbers);
      System.out.println("\nArrayList: "+ numbers);      //Implicit Iterator!
      System.out.println("Count negative numbers: " + countNeg(numbers));
      System.out.println("Average: " + average(numbers));
      replaceNeg(numbers);
      System.out.println("Replace negative numbers: " + numbers);
      deleteZero(numbers);
      System.out.println("Delete zeros: " + numbers);
      String[] rawMovies = {"High_Noon", "High_Noon", "Star_Wars", "Tron", "Mary_Poppins", 
               "Dr_No", "Dr_No", "Mary_Poppins", "High_Noon", "Tron"};
      ArrayList<String> movies = createMovies(rawMovies);
      System.out.println("Movies: " + movies);
      System.out.println("Movies: " +  removeDupes(movies));
   }
      // pre: an array of int values 
   	// post: return an ArrayList containing all the values
   public static ArrayList<Integer> createNumbers(int[] rawNumbers) 
   {
      ArrayList<Integer> al = new ArrayList<Integer>();
      for(int i: rawNumbers){
         al.add(i);
      }
      return al;
   }
      // pre: an array of just Strings  
   	// post: return an ArrayList containing all the Strings
   public static ArrayList<String> createMovies(String[] rawWords) 
   {
      ArrayList<String> al = new ArrayList<String>();
      for(String str: rawWords)
         al.add(str);
      return al;
   }
   
   	// pre: ArrayList a is not empty and contains only Integer objects
   	// post: return the number of negative values in the ArrayList a
   public static int countNeg(ArrayList<Integer> a)
   {
      int negCount = 0;
      for(int i: a)
         if (i < 0)
            negCount++;
      return negCount;
   }
   	// pre: ArrayList a is not empty and contains only Integer objects
   	// post: return the average of all values in the ArrayList a
   public static double average(ArrayList<Integer> a)
   {
      int sum = 0;
      double count = 0.0;
      for(int i: a){
         sum += i;
         count++;
      }
      return sum/count;
   }
     	// pre: ArrayList a is not empty and contains only Integer objects
   	// post: replaces all negative values with 0 
   public static void replaceNeg(ArrayList<Integer> a)
   {
      ListIterator<Integer> it = a.listIterator();
      while(it.hasNext())
         if (!(it.next().compareTo(0) >= 0))
            it.set(0);
   }
     	// pre: ArrayList a is not empty and contains only Integer objects
   	// post: deletes all zeros in the ArrayList a
   public static void deleteZero(ArrayList<Integer> a)
   {
      ListIterator<Integer> it = a.listIterator();
      while(it.hasNext())
         if (it.next().equals(0))
            it.remove();
   }
      // pre: ArrayList a is not empty and contains only String objects
   	// post: return a new ArrayList without duplicate movie titles
      //       the parameter a is not modified!
		// strategy: start with an empty array and add movies as needed
   public static ArrayList<String> removeDupes(ArrayList<String> a)
   {
      ArrayList<String> al = new ArrayList<String>();
      for(String str: a){
         boolean flag = false;
         for(String x: al)
            if(x.equals(str))
               flag = true;
         if(flag == false)
            al.add(str);
      }
      return al;
   }
}