// Name: J2-07-23
// Date: 03/14/2023

import java.io.*;
import java.util.*;

public class Dictionary
{
   public static void main(String[] args) 
   {
      Scanner infile = null;
      PrintWriter outputFile = null;
      try
      {
         infile = new Scanner(new File("spanglish.txt"));
         outputFile = new PrintWriter(new FileWriter("dictionaryOutput.txt"));
      }
      catch(Exception e)
      {
         System.out.println( e );
      }
      
      Map<String, Set<String>> eng2spn = makeDictionary( infile );
      outputFile.println("ENGLISH TO SPANISH");
      outputFile.println(display(eng2spn));
   
      Map<String, Set<String>> spn2eng = reverse(eng2spn);
      outputFile.println("SPANISH TO ENGLISH");
      outputFile.println(display(spn2eng));
      outputFile.close();
      
      System.out.println("File created.");
   }
   
   public static Map<String, Set<String>> makeDictionary(Scanner infile)
   {
      Map<String, Set<String>> dict = new HashMap<String, Set<String>>();
      while(infile.hasNextLine())
         add(dict, infile.nextLine(), infile.nextLine());
      return dict;
   }
   
   public static void add(Map<String, Set<String>> dictionary, String word, String translation)
   { 
      Set<String> translator = dictionary.containsKey(word) == false ? new HashSet<String>() : dictionary.get(word);
      translator.add(translation);
      dictionary.put(word, translator);
   }
   
   public static String display(Map<String, Set<String>> m)
   {
      String ret = "";
      for(String s: m.keySet())
         ret += s + " " + m.get(s) + "\n";
      return ret;
   }
   
   public static Map<String, Set<String>> reverse(Map<String, Set<String>> dictionary)
   {
      Map<String, Set<String>> reverse = new HashMap<String, Set<String>>();
      for(String english: dictionary.keySet())
         for(String spanish: dictionary.get(english))
            add(reverse, spanish, english);
      return reverse;
   }
}

   /********************
	FILE INPUT:
   	holiday
		fiesta
		holiday
		vacaciones
		party
		fiesta
		celebration
		fiesta
     <etc.>
  *********************************** 
	FILE OUTPUT:
		ENGLISH TO SPANISH
			banana [banana]
			celebration [fiesta]
			computer [computadora, ordenador]
			double [doblar, doble, duplicar]
			father [padre]
			feast [fiesta]
			good [bueno]
			hand [mano]
			hello [hola]
			holiday [fiesta, vacaciones]
			party [fiesta]
			plaza [plaza]
			priest [padre]
			program [programa, programar]
			sleep [dormir]
			son [hijo]
			sun [sol]
			vacation [vacaciones]

		SPANISH TO ENGLISH
			banana [banana]
			bueno [good]
			computadora [computer]
			doblar [double]
			doble [double]
			dormir [sleep]
			duplicar [double]
			fiesta [celebration, feast, holiday, party]
			hijo [son]
			hola [hello]
			mano [hand]
			ordenador [computer]
			padre [father, priest]
			plaza [plaza]
			programa [program]
			programar [program]
			sol [sun]
			vacaciones [holiday, vacation]

**********************/