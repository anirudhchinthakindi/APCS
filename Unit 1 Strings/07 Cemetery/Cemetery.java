// Name: J2-07-22
// Date: 09/15/2022
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class Cemetery
{
   public static void main (String [] args)
   {
      // File file = new File("cemetery_short.txt");
      File file = new File("cemetery.txt");
      int numEntries = countEntries(file);
      Person[] cemetery = readIntoArray(file, numEntries); 
      //see what you have
      for (int i = 0; i < cemetery.length; i++) 
         System.out.println(cemetery[i].getName() + ", " + cemetery[i].getBurialDate() + ", " + cemetery[i].getAge());
         
      int min = locateMinAgePerson(cemetery);
      int max = locateMaxAgePerson(cemetery); 
      System.out.println("\nIn the St. Mary Magdelene Old Fish Cemetery --> ");
      System.out.println("Name of youngest person: " + cemetery[min].getName());
      System.out.println("Age of youngest person: " + cemetery[min].getAge());    
      System.out.println("Name of oldest person: " + cemetery[max].getName());
      System.out.println("Age of oldest person: " + cemetery[max].getAge()); 
      
      //you may create other testing cases here
      
      int first = firstFirstName(cemetery);
      int last = lastFirstName(cemetery); 
      System.out.println("\nIn the St. Mary Magdelene Old Fish Cemetery --> ");
      System.out.println("First name(sorted alphabetically): " + cemetery[first].getName());
      System.out.println("Age of alphabetically first person: " + cemetery[first].getAge());    
      System.out.println("Last name(sorted alphabetically): " + cemetery[last].getName());
      System.out.println("Age of alphabetically last person: " + cemetery[last].getAge()); 
   }
   
   /* Counts and returns the number of entries in File f. 
      Returns 0 if the File f is not valid.
      Uses a try-catch block.   
      @param f -- the file object
   */
   public static int countEntries(File f)
   {      
      int entries = 1;
      boolean table = false;
      String firstLine;
      
      try
      {
         Scanner countEntry = new Scanner(f);
         firstLine = countEntry.nextLine();
         if (firstLine.charAt(0) == ' ')
            table = true;
         
         while(countEntry.hasNextLine()) {
            countEntry.nextLine();
            entries++;
         }
         countEntry.close();
      }
      catch(IOException e)
      {
         System.out.println("0");
         System.exit(0);
      }
      
      if (table == true)        
         return entries - 4;
      else
         return entries;
   }

   /* Reads the data from file f (you may assume each line has same allignment).
      Fills the array with Person objects. If File f is not valid return null.
      @param f -- the file object 
      @param num -- the number of lines in the File f  
   */
   public static Person[] readIntoArray (File f, int num)
   {
      Person[] data = new Person[num];
      int dataIndex = 0;
      
      try{
         Scanner arrayReader = new Scanner(f);
         while (arrayReader.hasNext()){
            String line = arrayReader.nextLine();
            if (line.charAt(0) == ' '){
               line = arrayReader.nextLine();
               line = arrayReader.nextLine();
               line = arrayReader.nextLine();
               line = arrayReader.nextLine();
            }
         
            data[dataIndex] = makeObjects(line);         
            dataIndex += 1;
         }
         
         arrayReader.close();
         return data;
      }
      catch(FileNotFoundException e){
         return null;
      }
   }
   
   /* A helper method that instantiates one Person object.
      @param entry -- one line of the input file.
      This method is made public for gradeit testing purposes.
      This method should not be used in any other class!!!
   */
   public static Person makeObjects(String entry)
   {      
      Person dead = new Person(entry.substring(0, 25).trim(), entry.substring(25, 36).trim(), entry.substring(37, 42).trim());
      
      return dead;
   } 
   
   public static int firstFirstName(Person[] arr)
   {
      int firstName = 0;
      
      for(int i = 0; i < arr.length; i++){
         if(arr[firstName].getName().compareTo(arr[i].getName()) > 0)
            firstName = i;
      }
      
      return firstName;
   }
   
   public static int lastFirstName(Person[] arr)
   {
      int lastName = 0;
      
      for(int i = 0; i < arr.length; i++){
         if(arr[lastName].getName().compareTo(arr[i].getName()) < 0)
            lastName = i;
      }
      
      return lastName;
   }
   
   /* Finds and returns the location (the index) of the Person
      who is the youngest. (if the array is empty it returns -1)
      If there is a tie the lowest index is returned.
      @param arr -- an array of Person objects.
   */
   public static int locateMinAgePerson(Person[] arr)
   {
      if (arr.length == 0)
         return -1;
         
      int minAgeIndex = 0;
            
      for (int i = 0; i < arr.length; i++) {
         if (arr[minAgeIndex].getAge() > arr[i].getAge())
            minAgeIndex = i;
      }
      return minAgeIndex;
   }   
   
   /* Finds and returns the location (the index) of the Person
      who is the oldest. (if the array is empty it returns -1)
      If there is a tie the lowest index is returned.
      @param arr -- an array of Person objects.
   */
   public static int locateMaxAgePerson(Person[] arr)
   {
      if (arr.length == 0)
         return -1;
         
      int maxAgeIndex = 0;
            
      for (int i = 0; i < arr.length; i++) {
         if (arr[maxAgeIndex].getAge() < arr[i].getAge())
            maxAgeIndex = i;
      }
      return maxAgeIndex;
   }        
} 

class Person
{
   //constant that can be used for formatting purposes
   private static final DecimalFormat df = new DecimalFormat("0.0000");
   /* private fields */
   
   private String personName;
   private String personBurialDate;
   private double personAge;
      
   /* a three-arg constructor  
    @param name, burialDate may have leading or trailing spaces
    It creates a valid Person object in which each field has the leading and trailing
    spaces eliminated*/
   public Person(String name, String burialDate, String age)
   {
      personName = name.trim();
      personBurialDate = burialDate.trim();
      personAge = calculateAge(age);
   }
   /* any necessary accessor methods (at least "double getAge()" and "String getName()" )
   make sure your get and/or set methods use the same data type as the field  */
   
   public String getName(){
      return personName;
   }
   public void setName(String names){
      personName = names;
   }
   
   public String getBurialDate(){
      return personBurialDate;
   }
   public void setBurialDate(String burialDates){
      personBurialDate = burialDates;
   }
   
   public double getAge(){
      return personAge;
   }
   public void setAge(double ages){
      personAge = ages;
   }
   
   /*handles the inconsistencies regarding age
     @param a = a string containing an age from file. Ex: "12", "12w", "12d"
     returns the age transformed into year with 4 decimals rounding
   */
   public double calculateAge(String a)
   {
      if (a.contains("w")){
         a = a.replace("w","");
         double weekDouble = Double.parseDouble(a);
         double weekActual = weekDouble/52.143;
         return Double.parseDouble(df.format(weekActual));
      }
      else if (a.contains("d")){
         a = a.replace("d","");
         double dayDouble = Double.parseDouble(a);
         double dayActual = dayDouble/365;
         return Double.parseDouble(df.format(dayActual));
      }
      else{
         double regularActual = Double.parseDouble(a);
         return Double.parseDouble(df.format(regularActual)); 
      }
   }
}

/******************************************

 John William ALLARDYCE, 17 Mar 1844, 2.9
 Frederic Alex. ALLARDYCE, 21 Apr 1844, 0.17
 Philip AMIS, 03 Aug 1848, 1.0
 Thomas ANDERSON, 06 Jul 1845, 27.0
 Edward ANGEL, 20 Nov 1842, 22.0
 Lucy Ann COLEBACK, 23 Jul 1843, 0.2685
 Thomas William COLLEY, 08 Aug 1833, 0.011
 Joseph COLLIER, 03 Apr 1831, 58.0
 
 In the St. Mary Magdelene Old Fish Cemetery --> 
 Name of youngest person: Thomas William COLLEY
 Age of youngest person: 0.011
 Name of oldest person: Joseph COLLIER
 Age of oldest person: 58.0
 
 **************************************/