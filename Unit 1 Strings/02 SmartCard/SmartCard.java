//Name: J2-07-22
//Date: 08/25/2022

import java.text.DecimalFormat;

public class SmartCard 
{
   public final static DecimalFormat df = new DecimalFormat("$0.00");
   public final static double minimumFare = 0.5;
   /* enter the private fields */

   private static double balance = 0; // initial balance
   private static boolean boarded = false; // checking if they're boarded or not
   private static Station stat; // the station where the passenger is currently at
   
   /* the one-arg constructor  */

   public SmartCard(double d){ // a constructor initializes all privaate fields
      balance = d;
      boarded = false;
      stat = null;
   }

   /* write the instance methods  */
   
   public static double getBalance(){ // returning the balance of the passenger
      return balance;
   }
      
   public static String getFormattedBalance(){ // returning the balance in proper phonetic format
      return df.format(balance);
   }
   
   public static boolean getIsBoarded(){ // whether the passenger has boarded already
      return boarded;
   }
   
   public Station getBoardedAt(){ // which station the passenger boarded
      return stat;
   }
   
   public void board(Station s){
      if (boarded == true){
         System.out.println("Error: already boarded?!"); // the passenger has boarded another train without exiting
      }
      
      else if (balance < minimumFare){
         System.out.println("Insufficient funds to board. Please add more money."); // if the passsenger has no more money in their account
      }
      
      else{ // if everything goes well
         boarded = true;
         stat = s;
      }
   }

   double payment = 0.50; // the cost for travelling within a zone

   public double cost(Station s){
      int zoneChanges = s.getZone() - stat.getZone(); // seeing how many zones they travelled by dividing the current zone by the original zone
      payment += 0.75 * zoneChanges; // adding the zone changes to the base cost
      return payment; // returning the final cost
   }

   public void exit(Station s){
      if (!boarded){
         System.out.println("Error: Did not board?!");
      }
      
      else
         if (payment > balance){
            System.out.println("Insufficient funds to exit. Please add more money.");
         }
         
         else{
            balance -= payment;
            boarded = false;
            System.out.println("From " + stat.getZone() + " to " + s.getZone() + " costs " + df.format(cost(s)) + ". Smartcard has " + df.format(balance));
         }
   }

   public void addMoney(double d){
      balance += d;
      System.out.println(df.format(d) + " added. Your new balance is " + df.format(balance));
   }
}
   
// ***********  start a new class.  The new class does NOT have public or private.  ***/
class Station
{
   private String name;
   private int zone;
   
   public Station(){ // default constructor
      name = "";
      zone = 0;
   }
   
   public Station(String a, int b){ // constructor w/ parameters
      name = a;
      zone = b;
   }

   public String getName(){ // getter function
      return name;
   }
   
   public int getZone(){ // getter function
      return zone;
   }
}