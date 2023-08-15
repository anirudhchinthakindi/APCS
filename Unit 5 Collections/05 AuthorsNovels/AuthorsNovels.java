// Name: J2-07-23
// Date: 01/10/2023

import java.io.*;
import java.util.*;

public class AuthorsNovels
{
   public static void main(String[] args) throws IOException
   {
      /*   test the AuthorEntry object  */
      AuthorEntry a = new AuthorEntry("Aaaa");
      System.out.println("name: " + a.getName());
      System.out.println("AuthorEntry.toString(): " + a);
      AuthorEntry b = new AuthorEntry("Dd", "y");
      System.out.println("name: " + b.getName());
      b.addNovel("z");
      b.addNovel("y");
      b.addNovel("x");
      System.out.println("AuthorEntry.toString(): " + b);
      System.out.println(b.compareTo(a));   // 3
      System.out.println(a.compareTo(b));   // -3
      
      /*  start the lab  */
      Scanner keyboard = new Scanner(System.in);
      System.out.print("\nEnter input file name: ");
      String inFileName = keyboard.nextLine().trim()+".txt";
      Scanner inputFile = new Scanner(new File(inFileName));
      //System.out.print("\nEnter output file name: ");
      //String outFileName = keyboard.nextLine().trim();
      AuthorList authors = readAndMakeTheList(inputFile);
      String outFileName = "authorsNovelsOut.txt";
      PrintWriter outputFile = new PrintWriter(new FileWriter(outFileName));
      outputFile.println( authors.toString() );
      inputFile.close(); 						
      outputFile.close();
      System.out.println("Done.");
   }
   
   public static AuthorList readAndMakeTheList(Scanner inputFile)
   {
      AuthorList theList = new AuthorList();
      while(inputFile.hasNextLine())
      {
         String str = inputFile.nextLine();
         theList.readOneLine(str);
      }
      return theList;
   }
}

class AuthorList extends ArrayList<AuthorEntry>
{
    /**   you get an ArrayList for free   **/
   public AuthorList()
   {
      super();
   }
     /** extracts the author and book from oneLine.
         calls addAuthorEntry      
      */
   public void readOneLine(String oneLine) 
   {
      String[] parts = oneLine.split(",");
      addAuthorEntry(parts[0], parts[1]);
   }
   
    /** use a listIterator.  Needs to call .previous() 
          either inserts a new AuthorEntry object, or 
          adds a novel to a previous AuthorEntry object,
          in alphabetic order
    */
   public void addAuthorEntry(String name, String book)
   {
      ListIterator<AuthorEntry> it = this.listIterator();
      while(it.hasNext()){
         AuthorEntry current = it.next();
         if(current.getName().compareTo(name)>0){
            it.previous();
            it.add(new AuthorEntry(name, book));
            return;
         }
         else if(current.getName().compareTo(name) == 0){
            current.addNovel(book);
            return;
         }
      }
      this.add(new AuthorEntry(name, book));
   }
   
   public String toString()
   {  
      String ret = "";
      for(AuthorEntry x: this){
         ret += x.toString();
      }
      return ret;
   }
}

class AuthorEntry implements Comparable<AuthorEntry>
{
   //fields
   private String name;
   private ArrayList<String> novels;
   
   //two constructors. argument n may be in lowercase. 
   public AuthorEntry(String n)
   {
      name = n.toUpperCase();
      novels = new ArrayList<String>();
   }
   public AuthorEntry(String n, String book)
   {
      name = n.toUpperCase();
      novels = new ArrayList<String>();
      novels.add(book);
   }
   
   /**  appends book to novels, but only if it is not already in that list.    
       */
   public void addNovel(String book)
   {
      boolean flag = false;;
      for(String x: novels)
         if(x.equals(book))
            flag = true;
      if(novels == null || flag == false)
         novels.add(book);
   }
   
   /** two standard accessor methods  */
   
   public String getName()
   {
      return name;
   }
   
   public ArrayList<String> getNovels()
   {
      return novels;
   }   
        
   /**  pre:  name is not an empty string.  novels might be an empty ArrayList.
       uses:  either a for-each loop or an iterator
       post:  returns a string representation of this AuthorEntry in the format as 
              shown on each line of the output file.  
     */
   public String toString()
   {
      String ret = "";
      for(String x: novels)
         ret += x + ", ";
      return name + ": " + ret.substring(0, ret.length()-1) + "\n";
   }
   
   public int compareTo(AuthorEntry other)
   {
      return name.compareTo(other.getName());
   }
}


/********************   Extension   
// class Author extends ArrayList<String> implements Comparable<Author>
// {
// }


/**********************  SAMPLE RUN  ********************************
 name: AAAA
 novels: []
 toString(): AAAA
 name: DD
 novels: [y, z, x]
 toString(): DD: y, z, x
 3
 -3
 
 Enter input file name: infile2
 Done.
 
 **********************************************************/
/******** Output file for infile2:

DOSTOEVSKI: Crime and Punishment, The Possessed, The Brothers Karamazov, The Grand Inquisitor
FLAUBERT: Madame Bovary, A Simple Heart, Memoirs of a Madman, Sentimental Education
STENDHAL: The Red and the Black
TOLSTOY: Anna Karenina, War and Peace, The Death of Ivan Illyich, The Kreutzer Sonata

 */