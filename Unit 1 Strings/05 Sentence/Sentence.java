// Name:  J2-07-22
// Date:  09/13/22
  
public class Sentence
{
   private String mySentence;
   private int myNumWords;
   
   //Precondition:  str is not empty.
   //               Words in str separated by exactly one blank.
   public Sentence( String str )
   { 
      mySentence = str;
      str = removePunctuation(str);
      myNumWords = str.split(" ").length;
   }
   
   public int getNumWords()
   {  
      return myNumWords;  
   }
   
   public String getSentence()
   {
      return mySentence; 
   }
   
   //Returns true if mySentence is a palindrome, false otherwise.
   //calls the 3-arg isPalindrome(String, int, int)
   public boolean isPalindrome()
   {
      String palindrome = getSentence();
      
      palindrome = removeBlanks(palindrome);
      palindrome = lowerCase(palindrome);
      palindrome = removePunctuation(palindrome);
      
      int lastIndex = palindrome.length() - 1;
      
      if (palindrome.length() != 0)
         return isPalindrome(palindrome, 0, lastIndex);
      else if (palindrome.length() != 1)
         return isPalindrome(palindrome, 0, lastIndex);
      else
         return true;
   }
   
   //Precondition: s has no blanks, no punctuation, and is in lower case.
   //Recursive method.
   //Returns true if s is a palindrome, false otherwise.
   public static boolean isPalindrome( String s, int start, int end )
   {  
      // if the start and end just don't match up
      if (end - start == -1)
         return true;
      
      // if the first letter and the last letter are not the same(not a palindrome)
      if (s.charAt(start) != s.charAt(end))
         return false;
      
      // if the end and start have no or one character(s) in between
      else if (end - start == 0)
         return true;
      else if (end - start == 1)
         return true;
      
      // if it is the same, recurse the method, getting rid of the first and last characters
      else{
         start +=1;
         end -= 1;
         return isPalindrome(s, start, end);
      }
   }
   //Returns copy of String s with all blanks removed.
   //Postcondition:  Returned string contains just one word.
   public static String removeBlanks( String s )
   {  
      String removed = "";
      String[] removedArray = s.split(" ");
      for (int i = 0; i < removedArray.length; i++){
         removed += removedArray[i];
      }
      return removed;
   }
   
   //Returns copy of String s with all letters in lowercase.
   //Postcondition:  Number of words in returned string equals
   //						number of words in s.
   public static String lowerCase( String s )
   {  
      return s.toLowerCase();
   }
   
   //Returns copy of String s with all punctuation removed.
   //Postcondition:  Number of words in returned string equals
   //						number of words in s.
   public static String removePunctuation( String s )
   { 
      String punct = ".,'?!:;\"(){}[]<>";
      for (int i = 0; i < punct.length(); i++){
         s = s.replace(punct.charAt(i) + "", "");
      }
      return s;
   }
}

 /*****************************************
   
 PALINDROME TESTER
 "Hello there!" she said.
 4
 false
 
 A Santa lived as a devil at NASA.
 8
 true
 
 Flo, gin is a sin! I golf.
 7
 true
 
 Eva, can I stab bats in a cave?
 8
 true
 
 Madam, I'm Adam.
 3
 true

 **********************************************/