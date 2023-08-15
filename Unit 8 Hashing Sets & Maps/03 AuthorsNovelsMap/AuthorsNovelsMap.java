// Name: J2-07-23
// Date: 03/14/2023

import java.io.*;
import java.util.*;

public class AuthorsNovelsMap
{
   public static void main(String[] args) throws IOException
   {
      Scanner keyboard = new Scanner(System.in);
      System.out.print("\nEnter input file name: ");
      String inFileName = keyboard.nextLine().trim()+".txt";
      Scanner inputFile = new Scanner(new File(inFileName));
      //System.out.print("\nEnter output file name: ");
      //String outFileName = keyboard.nextLine().trim();
      AuthorsMap a1 = new AuthorsMap();
      a1.addAuthorOrNovel("Bob", "John");
      System.out.println(a1);
   
      AuthorsMap authors = readAndMakeTheList(inputFile);
      PrintWriter outputFile = new PrintWriter(new FileWriter("authorsNovelsOut.txt"));
      outputFile.println( authors.toString() );
      inputFile.close(); 						
      outputFile.close();
      System.out.println("File created.");
   }
   
   public static AuthorsMap readAndMakeTheList(Scanner inputFile)
   {
      AuthorsMap theAuthors = new AuthorsMap();
      while(inputFile.hasNextLine())
         theAuthors.readOneLine(inputFile.nextLine());
      return theAuthors;
   }
}

class AuthorsMap extends TreeMap<String, Set<String>>
{
   /** extracts the author and book from oneLine.
       calls addAuthorOrNovel      
      */
   public void readOneLine(String oneLine) 
   { 
      String[] parts = oneLine.split(", "); // split the line using comma as delimiter and remove any whitespace after it
      if(parts.length > 1)
         addAuthorOrNovel(parts[0].toUpperCase(), parts[1]);
   }
   
   /**  either inserts a new Author mapping, or updates a previous Author mapping
        */
   public void addAuthorOrNovel(String name, String book)
   {
      Set<String> books = get(name.toUpperCase());
      if(books == null){
         books = new TreeSet<String>();
         put(name.toUpperCase(), books);
      }
      books.add(book);
   }
   
   public String toString()
   {
      String result = "";
      for(String authors: keySet()){
         result += authors.toUpperCase() + ": ";
         for(String books: get(authors.toUpperCase()))
            result += books + ", ";
         result = result.substring(0, result.length() - 2) + "\n";
      }
      return result.substring(0, result.length() - 1);
   }
}
   

/**********************  SAMPLE RUN  ********************************
   /******** Output file for infile2:
   
   DOSTOEVSKI: Crime and Punishment, The Possessed, The Brothers Karamazov, The Grand Inquisitor
   FLAUBERT: Madame Bovary, A Simple Heart, Memoirs of a Madman, Sentimental Education
   STENDHAL: The Red and the Black
   TOLSTOY: Anna Karenina, War and Peace, The Death of Ivan Illyich, The Kreutzer Sonata
   
    **********************************/