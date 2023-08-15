// Name:   
// Date: 
import java.util.*;
import java.io.*;
public class PigLatin
{
   public static void main(String[] args) 
   {
      part_1_using_pig();
      // part_2_using_piglatenizeFile();
      
      /*  extension only    */
      // String pigLatin = pig("What!?");
      // System.out.print(pigLatin + "\t\t" + pigReverse(pigLatin));   //Yahwta!?
      // pigLatin = pig("{(Hello!)}");
      // System.out.print("\n" + pigLatin + "\t\t" + pigReverse(pigLatin)); //{(Yaholle!)}
      // pigLatin = pig("\"McDonald???\"");
      // System.out.println("\n" + pigLatin + "  " + pigReverse(pigLatin));//"YaDcmdlano???"
   }

   public static void part_1_using_pig()
   {
      Scanner sc = new Scanner(System.in); //input from the keyboard
      while(true)
      {
         System.out.print("\nWhat word? ");
         String s = sc.next();     //reads up to white space
         if(s.equals("-1"))
         {
            System.out.println("Goodbye!"); 
            System.exit(0);
         }
         String p = pig(s);
         System.out.println(p);
      }		
   }

   public static final String punct = ",./;:'\"?<>[]{}|`~!@#$%^&*()";
   public static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
   public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   public static final String vowels = "AEIOUaeiou";
   public static String pig(String s)
   {
      int stringLength = s.length();
      if(stringLength == 0)
         return "";
   
      //remove and store the punctuation 
           
      String puncts = "";
      String beginningPunct = "";
      String intermediateEndingPunct = "";
      String endingPunct = "";
      String newString = "";
      for (int i = 0; i < stringLength; i++){
         for (int j = 0; j < punct.length(); j++){
            
            if(i == 0 && j == 0) {
               for(int k = 0; k < stringLength; k++) {
                  if(punct.indexOf(s.charAt(0))>=0) {
                     beginningPunct = beginningPunct + s.charAt(0);
                     s = s.substring(1);
                  }
               }
           
               for(int k = (s.length()-1); k > 0; k--) {
                  if(punct.indexOf(s.charAt(s.length()-1))>=0){
                     intermediateEndingPunct = intermediateEndingPunct + s.charAt(s.length()-1);
                     s = s.substring(0,s.length()-1);
                  }
               }
               for(int k = intermediateEndingPunct.length()-1; k >=0; k--){
                  endingPunct = endingPunct + intermediateEndingPunct.charAt(k);
               }
            }

         }
      }
         
      //START HERE with the basic case:
      //y is a vowel if it is not the first letter
      boolean isyFirst = false;
      char y = 'y';
      char Y = 'Y';
      
      if(s.charAt(0) == y)
         isyFirst = true;
      if(s.charAt(0) == Y)
         isyFirst = true;
      
      //if the first letter is a vowel and not y
      if (isyFirst == false){
         if (vowels.indexOf(s.charAt(0)) >= 0){
            return beginningPunct + s + "way" + endingPunct;
            }
      } 
      
      //find the index of the first vowel
      int firstVowelIndex = 0;  
      boolean flag = false;
      for (int i = 0; i < s.length() && flag == false; i++){
         if (vowels.indexOf(s.charAt(i)) >= 0){
            firstVowelIndex = i;
            if (s.charAt(firstVowelIndex) == 'u' || s.charAt(firstVowelIndex) == 'U'){
               if (s.charAt(firstVowelIndex-1) == 'q' || s.charAt(firstVowelIndex-1) == 'Q'){
                  i++;
               }
            }
            else{
               flag = true;
            }
         }
      }
      
      //y is in the middle
      
      if ((firstVowelIndex == 0) || (s.indexOf("y") < firstVowelIndex)){
         if((s.indexOf("y"))>0) {
            firstVowelIndex = s.indexOf("y");
         }
      }
      
      if (firstVowelIndex == 0 || (s.indexOf("Y") < firstVowelIndex)){
         if((s.indexOf("Y"))>0) {
            firstVowelIndex = s.indexOf("Y");
         }
      }
      
      //basic rule
      String beginningConsonants = "";
      boolean isFirstCapital = Character.isUpperCase(s.charAt(0));
      if (firstVowelIndex > 0){
         if(isFirstCapital == true){
            s = Character.toLowerCase(s.charAt(0)) + s.substring(1);
         }
         beginningConsonants = s.substring(0, firstVowelIndex);
         s = s.substring(firstVowelIndex, s.length());
         if(isFirstCapital == true){
            s = Character.toUpperCase(s.charAt(0)) + s.substring(1);
            isFirstCapital = false;
         }
         return beginningPunct + s + beginningConsonants + "ay" + endingPunct;
      }
      
      //qu
      
      //Capitalization
      
      
      //if no vowel has been found
      String noVowels = "**** NO VOWEL ****";
      for (int i = 0; i < s.length(); i++){
         if (s.indexOf(vowels.charAt(i)) < 0){
            return noVowels;
         }
      }      
      
      
      //return the piglatinized word 
      
      return s;
      
   }


   public static void part_2_using_piglatenizeFile() 
   {
      Scanner sc = new Scanner(System.in);
      System.out.print("input filename including .txt: ");
      String fileNameIn = sc.next();
      System.out.print("output filename including .txt: ");
      String fileNameOut = sc.next();
      piglatenizeFile( fileNameIn, fileNameOut );
      System.out.println("Piglatin done!");
   }

/****************************** 
*  piglatinizes each word in each line of the input file
*    precondition:  both fileNames include .txt
*    postcondition:  output a piglatinized .txt file 
******************************/
   public static void piglatenizeFile(String fileNameIn, String fileNameOut) 
   {
      Scanner infile = null;
      try
      {
         infile = new Scanner(new File(fileNameIn));  
      }
      catch(IOException e)
      {
         System.out.println("oops");
         System.exit(0);   
      }
   
      PrintWriter outfile = null;
      try
      {
         outfile = new PrintWriter(new FileWriter(fileNameOut));
      }
      catch(IOException e)
      {
         System.out.println("File not created");
         System.exit(0);
      }
   	//process each word in each line
      
      while (infile.hasNext()){
         
         String outfileActual = "";
         
         String line = infile.nextLine();
         String[] splitWords = line.split(" ");
         
         for (String element: splitWords){
            outfileActual = outfileActual + pig(element) + " ";
         }
         
         boolean hasNext = infile.hasNext();
         
         if (!hasNext){
            outfile.print(outfileActual.trim());
         }
         
         if (hasNext){
            outfile.println(outfileActual.trim());
         }
      }
      outfile.close();
      infile.close();
}
   
   /** EXTENSION: Output each PigLatin word in reverse, preserving before-and-after 
       punctuation.  
   */
   public static String pigReverse(String s)
   {
      if(s.length() == 0)
         return "";
         
      return "";   //just to compile   
   }
}