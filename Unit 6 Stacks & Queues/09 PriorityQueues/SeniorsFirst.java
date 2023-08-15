// Name: J2-07-23
// Date: 02/01/2023

import java.util.*;
import java.io.*;
public class SeniorsFirst
{
   public static final int CUSTOMERS_PER_MINUTE = 2;  
              
   public static void main(String[] args)
   {     
      PrintWriter outfile = setUpFile();      
      
      System.out.println("Seniors First Simulation! ");
      Scanner kb = new Scanner(System.in);
      System.out.print("How many cashiers? ");
      int number_of_cashiers = kb.nextInt();
      System.out.print("How long, in minutes, should the simulation run? ");
      int time = kb.nextInt();
      
      waitTimes(time, number_of_cashiers, outfile);  //run the simulation
   } 
    
   public static PrintWriter setUpFile()
   {
      PrintWriter outfile = null; 
      try
      {
         outfile = new PrintWriter(new FileWriter("customerWaitTimes.txt"));
      }
      catch(IOException e)
      {
         System.out.println("File not created");
         System.exit(0);
      }
      return outfile;
   }
  
   public static void outfileCashiersAndQueues(PrintWriter outfile, int min, ArrayList<PriorityQueue<Customer>> cashier)
   { 
      outfile.println("minute " + min + ": ");
      for( Queue<Customer> q : cashier )
      {
         outfile.print("          ");
         for( Customer c : q )
            outfile.print( c.toString()+" ");
         outfile.println();
      }
      outfile.println("*****");
   }
  
   public static double calculateAverage(int totalMinutes, int customers)
   {
      return (int)(1.0 * totalMinutes/customers * 10)/10.0;
   }
   
   public static void waitTimes(int time, int number_of_cashiers, PrintWriter outfile)
   {
      int customers = 0;
      int customersCheckedOut = 0;
      String[] classes = new String[]{"Senior", "Junior", "Sophomor", "Freshman"};
      int[] served = new int[]{0,0,0,0};
      int[] longestWait = new int[]{0,0,0,0};
      int[] totalWait = new int[]{0,0,0,0};
   
      ArrayList<PriorityQueue<Customer>> cashiers = new ArrayList<>();
      for(int i=0; i<number_of_cashiers; i++)
         cashiers.add(new PriorityQueue<Customer>());
     /***************************************
           Write your code for the simulation.
           call outfileCashiersAndQueues() to write the queues to the file.  
      **********************************/       
      for (int i = 0; i < time; i++){
         for(int p = 0; p < CUSTOMERS_PER_MINUTE; p++){ //adding new Customers
            int minSize = Integer.MAX_VALUE;
            PriorityQueue<Customer> newCustomer = new PriorityQueue<Customer>();
            for(PriorityQueue<Customer> q: cashiers){ //finding the smallest queue
               int queueSize = size(q);
               if(minSize > queueSize){
                  minSize = queueSize;
                  newCustomer = q;
               }
            }
            newCustomer.add(new Customer(i));
            customers++;
         }
         
         outfileCashiersAndQueues(outfile, i, cashiers);
         
         for(PriorityQueue<Customer> q: cashiers){ //decrementing time
            Customer c = q.peek();
            if(!q.isEmpty())
               c.decrement();
            if(!q.isEmpty() && c.getTimeSpent() == 0){
               int priority = c.getPriority();
               int thisCustomersTime = i + 1 - c.getArrivalTime();
               if(longestWait[priority] < thisCustomersTime) //figuring out the longest wait time
                  longestWait[priority] = thisCustomersTime;
               totalWait[priority] += thisCustomersTime; //adding to the total wait time
               q.remove();
               customersCheckedOut++;
               served[priority]++;
               outfile.println("customers served: " + customersCheckedOut + ", Customer from minute " + c + " had total wait time: " + thisCustomersTime + ", total minutes (for all customers): " + totalWait[priority]);
            }
         }
      }
   
      /*  report the results to the screen in table form, like this:
         Customer		Total		Longest		Average Wait
         Senior			23			10	 	 	4.434782608695652
         Junior			18			40			7.666666666666667
         Sophomor			14			28			13.285714285714286
         Freshman			1			2	 	 	2.0
         */
      System.out.println("Customer		Total		Longest		Average Wait" );
      System.out.println(classes[0] + "\t\t\t" + totalWait[0] + "\t\t\t" + longestWait[0] + "\t \t \t" + calculateAverage(totalWait[0], served[0]));
      System.out.println(classes[1] + "\t\t\t" + totalWait[1] + "\t\t\t" + longestWait[1] + "\t \t \t" + calculateAverage(totalWait[1], served[1]));
      System.out.println(classes[2] + "\t\t\t" + totalWait[2] + "\t\t\t" + longestWait[2] + "\t \t \t" + calculateAverage(totalWait[2], served[2]));
      System.out.println(classes[3] + "\t\t\t" + totalWait[3] + "\t\t\t" + longestWait[3] + "\t \t \t" + calculateAverage(totalWait[3], served[3]));
      outfile.close();
   }
   
   public static int size(Queue<Customer> q){
      int size = 0;
      for(Customer c: q)
         size++;
      return size;
   }
   
   /*  copy your Customer class and modify it for priority queues  */
   static class Customer implements Comparable<Customer>
   {
      private int arrivedAt;
      private int timeSpentAtCashier;
      private int priority;
         // 0 = Senior
         // 1 = Junior
         // 2 = Sophomore
         // 3 = Freshman
      
      public Customer(int a){
         arrivedAt = a;
         timeSpentAtCashier = (int)((Math.random() * 5) + 2);
         priority = (int)((Math.random() * 4));
      }
      
      public int getArrivalTime(){
         return arrivedAt;      
      }
   
      public int getTimeSpent(){
         return timeSpentAtCashier;      
      }
      
      public int getPriority(){
         return priority;      
      }
      
      public void setArrivedAt(int a){
         arrivedAt = a;      
      }
   
      public void setTimeSpent(int t){
         timeSpentAtCashier = t;      
      }
      
      public void setPriority(int p){
         priority = p;
      }
      
      public void decrement(){
         timeSpentAtCashier--;
      }
      
      public String toString(){
         String schoolClass = (priority == 0) ? "Se" : (priority == 1 ? "Ju" : (priority == 2) ? "So" : "Fr");
         return arrivedAt + "-" + schoolClass + ":" + timeSpentAtCashier;
      }
      
      @Override
      public int compareTo(Customer c){
         return this.priority < c.priority) ? -1 : (this.priority == c.priority ? 0 : 1);
      }
   }
}

/******************************************************
to the screen:
   Seniors First Simulation! 
   How many cashiers? 4
   How long, in minutes, should the simulation run? 60
   Customer		Total		Longest		Average Wait
   Senior			23			10			4.434782608695652
   Junior			18			40			7.666666666666667
   Sophomor			14			28			13.285714285714286
   Freshman			1			2			2.0


to the file:

minute 0: 
          3-Fr 
          1-Fr 
          
          
minute 1: 
          2-Fr 
          
          1-Ju 
          1-So 
minute 2: 
          5-Ju 2-Fr 
          2-Se
          
minute 3: 
          4-Ju 2-Fr 
          1-Se 
          1-Ju 
          4-So 
minute 4: 
          3-Ju 2-Fr 
          2-Fr 
          3-Fr 
          3-So 
minute 5: 
          2-Ju 2-Fr 
          5-Ju 2-Fr 
          1-Se 3-Fr 
          2-So 
minute 6: 
          1-Ju 2-Fr 2-Ju 
          4-Ju 2-Fr 
          3-Fr 
          1-So 3-So 
minute 7: 
          2-Ju 2-Fr 
          3-Ju 2-Fr 6-Fr 
          5-Ju 3-Fr 
          3-So 
minute 8: 
          1-Ju 2-Fr 6-So 
          2-Ju 2-Fr 6-Fr 
          4-Ju 3-Fr 
          2-So 5-So 
minute 9: 
          6-So 2-Fr 
          1-Ju 2-Fr 6-Fr 
          3-Ju 3-Fr 3-Fr 
          5-Se 5-So 2-So 
minute 10: 
          5-Ju 2-Fr 6-So 2-Fr 
          2-Fr 6-Fr 
          2-Ju 3-Fr 3-Fr 
          4-Se 5-So 2-So 
minute 11: 
          4-Ju 2-Fr 6-So 2-Fr 
          4-So 2-So 2-Fr 6-Fr 
          1-Ju 3-Fr 3-Fr 
          3-Se 5-So 2-So 
minute 12: 
          3-Ju 2-Fr 6-So 2-Fr 
          3-So 2-So 2-Fr 6-Fr 
          3-Fr 4-Fr 3-Fr 
          2-Se 4-Ju 2-So 5-So 
minute 13: 
          2-Ju 2-So 6-So 2-Fr 2-Fr 
          2-So 2-So 2-Fr 6-Fr 
          5-Ju 3-Fr 3-Fr 4-Fr 
          1-Se 4-Ju 2-So 5-So 
minute 14: 
          1-Ju 2-So 6-So 2-Fr 2-Fr 
          1-So 2-So 2-Fr 6-Fr 3-So 
          4-Ju 4-Ju 3-Fr 4-Fr 3-Fr 
          4-Ju 5-So 2-So 
minute 15: 
          6-So 2-So 2-Fr 2-Fr 
          2-So 3-So 2-Fr 6-Fr 
          3-Ju 4-Ju 3-Fr 4-Fr 3-Fr 
          1-Se 4-Ju 2-So 3-So 5-So 
minute 16: 
          5-So 2-So 2-Fr 2-Fr 2-So 
          1-So 3-So 2-Fr 6-Fr 5-So 
          2-Ju 4-Ju 3-Fr 4-Fr 3-Fr 
          4-Ju 5-So 2-So 3-So 
minute 17: 
          4-So 2-So 2-Fr 2-Fr 2-So 4-Fr 
          3-So 5-So 2-Fr 6-Fr 
          1-Ju 4-Ju 3-Fr 4-Fr 3-Fr 
          3-Ju 2-Ju 2-So 3-So 5-So 
minute 18: 
          3-So 2-So 2-Fr 2-Fr 2-So 4-Fr 
          5-Ju 3-So 2-So 6-Fr 5-So 2-Fr 
          4-Ju 3-Fr 3-Fr 4-Fr 
          2-Ju 2-Ju 2-So 3-So 5-So 
minute 19: 
          2-So 2-So 2-Fr 2-Fr 2-So 4-Fr 
          4-Ju 3-So 2-So 6-Fr 5-So 2-Fr 
          3-Ju 2-So 3-Fr 4-Fr 3-Fr 6-Fr 
          1-Ju 2-Ju 2-So 3-So 5-So 
minute 20: 
          1-So 2-So 4-So 2-Fr 2-So 4-Fr 2-Fr 
          3-Ju 3-So 2-So 6-Fr 5-So 2-Fr 
          2-Ju 2-So 3-Fr 4-Fr 3-Fr 6-Fr 
          4-Se 2-Ju 1-Ju 3-So 5-So 2-So 
minute 21: 
          2-So 2-So 4-So 2-Fr 2-Fr 4-Fr 
          2-Ju 3-So 4-Ju 6-Fr 5-So 2-Fr 2-So 
          1-Ju 2-So 3-Fr 4-Fr 3-Fr 6-Fr 4-Fr 
          3-Se 2-Ju 1-Ju 3-So 5-So 2-So 
minute 22: 
          1-So 2-So 4-So 2-Fr 2-Fr 4-Fr 5-So 
          1-Ju 3-So 4-Ju 6-Fr 5-So 2-Fr 2-So 
          2-So 3-Fr 3-Fr 4-Fr 4-Fr 6-Fr 
          2-Se 2-Ju 2-Se 3-So 5-So 2-So 1-Ju 
minute 23: 
          2-So 2-Fr 4-So 2-Fr 3-Fr 4-Fr 5-So 
          4-Ju 3-So 2-So 6-Fr 5-So 2-Fr 
          1-So 3-Fr 3-So 4-Fr 4-Fr 6-Fr 3-Fr 
          1-Se 2-Ju 2-Se 3-So 5-So 2-So 1-Ju 
minute 24: 
          4-Ju 2-So 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 
          3-Ju 3-So 2-So 6-Fr 5-So 2-Fr 6-Fr 
          3-So 3-Fr 3-Fr 4-Fr 4-Fr 6-Fr 
          2-Se 2-Ju 1-Ju 3-So 5-So 2-So 
minute 25: 
          3-Ju 2-So 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 
          2-Ju 3-So 2-So 6-Fr 5-So 2-Fr 6-Fr 
          2-So 3-Fr 4-So 4-Fr 4-Fr 6-Fr 3-Fr 
          1-Se 2-Ju 4-Se 3-So 5-So 2-So 1-Ju 
minute 26: 
          2-Ju 2-So 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 
          2-Se 2-Ju 2-So 3-So 5-So 2-Fr 6-Fr 6-Fr 
          1-So 5-So 4-So 3-Fr 4-Fr 6-Fr 3-Fr 4-Fr 
          4-Se 2-Ju 1-Ju 3-So 5-So 2-So 
minute 27: 
          1-Ju 2-So 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 
          1-Se 2-Ju 2-So 3-So 5-So 2-Fr 6-Fr 6-Fr 
          4-So 5-So 3-Fr 3-Fr 4-Fr 6-Fr 4-Fr 
          3-Se 2-Ju 6-Se 3-So 5-So 2-So 1-Ju 4-So 
minute 28: 
          2-So 2-Fr 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 
          2-Ju 3-So 2-So 6-Fr 5-So 2-Fr 6-Fr 
          3-So 5-So 3-Fr 4-So 4-Fr 6-Fr 4-Fr 3-Fr 
          2-Se 2-Ju 6-Se 3-So 5-So 2-So 1-Ju 4-So 
minute 29: 
          3-Ju 2-So 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 2-Fr 
          1-Ju 3-So 2-So 5-So 5-So 2-Fr 6-Fr 6-Fr 
          2-So 5-So 3-Fr 4-So 4-Fr 6-Fr 4-Fr 3-Fr 
          1-Se 2-Ju 6-Se 3-So 5-So 2-So 1-Ju 4-So 
minute 30: 
          2-Ju 2-So 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 2-Fr 
          3-So 5-So 2-So 5-So 5-Fr 2-Fr 6-Fr 6-Fr 
          2-Se 2-So 3-Fr 5-So 4-Fr 6-Fr 4-Fr 3-Fr 4-So 
          6-Se 2-Ju 1-Ju 3-So 5-So 2-So 4-So 
minute 31: 
          1-Ju 2-So 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 2-Fr 
          2-So 5-So 2-So 5-So 5-Fr 2-Fr 6-Fr 6-Fr 6-Fr 
          1-Se 2-So 3-Fr 5-So 4-Fr 6-Fr 4-Fr 3-Fr 4-So 
          5-Se 2-Ju 1-Ju 3-So 5-So 2-So 4-So 4-So 
minute 32: 
          2-So 2-Fr 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 4-Fr 
          1-So 5-So 2-So 5-So 5-Fr 2-Fr 6-Fr 6-Fr 6-Fr 
          2-So 5-So 3-Fr 4-So 4-Fr 6-Fr 4-Fr 3-Fr 
          4-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 
minute 33: 
          3-Se 2-So 4-So 2-Fr 2-Fr 4-Fr 5-So 2-Fr 4-Fr 3-Fr 
          5-So 5-So 2-So 6-Fr 5-Fr 2-Fr 6-Fr 6-Fr 
          1-So 5-So 3-Fr 4-So 4-Fr 6-Fr 4-Fr 3-Fr 6-Fr 
          3-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 
minute 34: 
          2-Se 2-So 4-So 2-Fr 2-Fr 4-Fr 5-So 2-Fr 4-Fr 3-Fr 
          4-So 5-So 2-So 6-Fr 5-Fr 2-Fr 6-Fr 6-Fr 2-Fr 3-Fr 
          5-So 4-So 3-Fr 3-Fr 4-Fr 6-Fr 4-Fr 6-Fr 
          2-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 
minute 35: 
          1-Se 2-So 4-So 2-Fr 2-Fr 4-Fr 5-So 2-Fr 4-Fr 3-Fr 
          3-So 5-So 2-So 6-Fr 5-Fr 2-Fr 6-Fr 6-Fr 2-Fr 3-Fr 
          1-Se 3-Se 3-Fr 4-So 5-So 6-Fr 4-Fr 6-Fr 3-Fr 4-Fr 
          1-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 
minute 36: 
          2-So 2-So 4-So 2-Fr 2-Fr 4-Fr 5-So 2-Fr 4-Fr 3-Fr 
          2-So 5-So 2-So 6-Fr 5-Fr 2-Fr 6-Fr 6-Fr 2-Fr 3-Fr 
          3-Se 5-So 3-Fr 4-So 4-Fr 6-Fr 4-Fr 6-Fr 3-Fr 
          6-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 
minute 37: 
          1-So 2-So 4-So 2-Fr 2-Fr 4-Fr 5-So 2-Fr 4-Fr 3-Fr 
          1-So 5-So 2-So 6-Fr 5-Fr 2-Fr 6-Fr 6-Fr 2-Fr 3-Fr 
          2-Se 5-Ju 3-Fr 4-So 5-So 6-Fr 4-Fr 6-Fr 3-Fr 4-Fr 
          5-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 6-Fr 
minute 38: 
          4-So 2-So 5-So 2-Fr 6-So 4-Fr 2-Fr 2-Fr 4-Fr 3-Fr 
          2-So 5-So 2-Fr 6-Fr 2-So 5-Fr 6-Fr 6-Fr 2-Fr 3-Fr 
          1-Se 5-Ju 3-Fr 4-So 5-So 6-Fr 4-Fr 6-Fr 3-Fr 4-Fr 
          4-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 6-Fr 
minute 39: 
          3-Ju 4-So 5-So 2-Fr 2-So 4-Fr 2-Fr 2-Fr 4-Fr 3-Fr 6-So 
          3-Se 2-So 2-Fr 6-Fr 5-So 5-Fr 6-Fr 6-Fr 2-Fr 3-Fr 2-So 
          5-Ju 5-So 3-Fr 4-So 4-Fr 6-Fr 4-Fr 6-Fr 3-Fr 
          3-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 6-Fr 
minute 40: 
          2-Ju 4-So 5-So 2-Fr 2-So 4-Fr 2-Fr 2-Fr 4-Fr 3-Fr 6-So 
          2-Se 2-So 2-Fr 6-Fr 5-So 5-Fr 6-Fr 6-Fr 2-Fr 3-Fr 2-So 
          4-Ju 5-So 3-Fr 4-So 2-So 6-Fr 4-Fr 6-Fr 3-Fr 4-Fr 3-So 
          2-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 6-Fr 
minute 41: 
          1-Ju 4-So 5-So 2-Fr 2-So 3-So 2-Fr 2-Fr 4-Fr 3-Fr 6-So 4-Fr 
          1-Se 2-So 2-Fr 6-Fr 5-So 5-Fr 6-Fr 6-Fr 2-Fr 3-Fr 2-So 
          3-Ju 5-So 3-Fr 4-So 2-So 6-Fr 4-Fr 6-Fr 3-Fr 4-Fr 3-So 
          1-Se 2-Ju 1-Ju 2-Ju 4-Ju 2-So 4-So 4-So 3-So 6-Fr 5-So 
minute 42: 
          4-So 2-So 5-So 2-Fr 6-So 3-So 2-Fr 2-Fr 4-Fr 3-Fr 4-Fr 
          2-So 5-So 2-So 6-Fr 2-So 2-Fr 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 
          2-Ju 5-So 4-So 4-So 2-So 3-Fr 4-Fr 6-Fr 3-Fr 4-Fr 3-So 6-Fr 
          1-Ju 2-Ju 2-So 2-Ju 4-Ju 5-So 4-So 4-So 3-So 6-Fr 
minute 43: 
          3-So 2-So 5-So 2-Fr 6-So 3-So 2-Fr 2-Fr 4-Fr 3-Fr 4-Fr 6-Fr 
          1-So 5-So 2-So 6-Fr 2-So 2-Fr 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 
          1-Ju 5-So 4-So 4-So 2-So 3-Fr 4-Fr 6-Fr 3-Fr 4-Fr 3-So 6-Fr 
          4-Se 1-Ju 2-So 2-Ju 2-Ju 5-So 4-So 4-So 3-So 6-Fr 4-Ju 
minute 44: 
          2-So 2-So 5-So 2-Fr 6-So 3-So 2-Fr 2-Fr 4-Fr 3-Fr 4-Fr 6-Fr 
          5-Ju 5-So 1-So 6-Fr 2-So 2-So 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 
          5-So 4-So 4-So 3-Fr 2-So 3-Fr 4-Fr 6-Fr 6-Fr 4-Fr 3-So 
          3-Se 1-Ju 3-Se 2-Ju 2-Ju 2-So 4-So 4-So 3-So 6-Fr 4-Ju 5-So 
minute 45: 
          1-So 2-So 5-So 2-Fr 6-So 3-So 2-Fr 2-Fr 4-Fr 3-Fr 4-Fr 6-Fr 4-Fr 
          4-Ju 5-So 1-So 6-Fr 2-So 2-So 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 
          3-Se 4-So 5-So 3-Fr 2-So 4-So 4-Fr 6-Fr 6-Fr 4-Fr 3-So 3-Fr 
          2-Se 1-Ju 3-Se 2-Ju 2-Ju 2-So 4-So 4-So 3-So 6-Fr 4-Ju 5-So 
minute 46: 
          5-So 2-So 3-So 2-Fr 6-So 6-Fr 2-Fr 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 
          4-Se 5-So 4-Ju 6-Fr 2-So 1-So 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 2-So 
          2-Se 4-So 5-So 3-Fr 2-So 4-So 4-Fr 6-Fr 6-Fr 4-Fr 3-So 3-Fr 2-Fr 
          1-Se 1-Ju 3-Se 2-Ju 2-Ju 2-So 4-So 4-So 3-So 6-Fr 4-Ju 5-So 
minute 47: 
          3-Se 2-So 5-So 2-Fr 6-So 3-So 2-Fr 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 
          3-Se 5-So 4-Ju 6-Fr 2-So 1-So 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 2-So 
          1-Se 4-So 5-So 3-Fr 2-So 4-So 4-Fr 6-Fr 6-Fr 4-Fr 3-So 3-Fr 2-Fr 
          3-Se 1-Ju 2-So 2-Ju 2-Ju 5-So 4-So 4-So 3-So 6-Fr 4-Ju 6-So 
minute 48: 
          2-Se 2-So 5-So 2-Fr 6-So 3-So 5-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 2-Fr 
          2-Se 5-So 4-Ju 6-Fr 2-So 1-So 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 2-So 
          5-So 4-So 4-So 3-Fr 2-So 3-Fr 4-Fr 6-Fr 6-Fr 4-Fr 3-So 2-Fr 
          2-Se 1-Ju 2-So 2-Ju 2-Ju 5-So 4-So 4-So 3-So 6-Fr 4-Ju 6-So 5-Fr 
minute 49: 
          1-Se 2-So 5-So 2-Fr 6-So 3-So 5-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 2-Fr 
          1-Se 5-So 4-Ju 6-Fr 2-So 1-So 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 2-So 6-Fr 
          4-So 4-So 4-So 3-Fr 2-So 3-Fr 4-Fr 6-Fr 6-Fr 4-Fr 3-So 2-Fr 5-Fr 
          1-Se 1-Ju 2-So 2-Ju 2-Ju 5-So 4-So 4-So 3-So 6-Fr 4-Ju 6-So 5-Fr 
minute 50: 
          5-So 2-So 3-So 2-Fr 6-So 2-Fr 5-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 
          4-Ju 5-So 1-So 6-Fr 2-So 2-So 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 
          3-So 4-So 4-So 3-Fr 2-So 3-Fr 4-Fr 6-Fr 6-Fr 4-Fr 3-So 2-Fr 5-Fr 3-Fr 
          1-Ju 2-Ju 2-So 2-Ju 4-Ju 5-So 4-So 4-So 3-So 6-Fr 4-Fr 6-So 5-Fr 
minute 51: 
          4-So 2-So 3-So 2-Fr 6-So 2-Fr 5-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 
          3-Ju 5-So 1-So 6-Fr 2-So 2-So 4-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 
          2-So 4-So 4-So 3-Fr 2-So 3-Fr 4-Fr 6-Fr 6-Fr 4-Fr 3-So 2-Fr 5-Fr 3-Fr 
          2-Ju 2-Ju 2-So 3-So 4-Ju 5-So 4-So 4-So 5-Fr 6-Fr 4-Fr 6-So 
minute 52: 
          3-So 2-So 3-So 2-Fr 6-So 2-Fr 5-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 
          2-Ju 5-So 1-So 6-Fr 2-So 2-So 4-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 
          1-So 4-So 4-So 3-Fr 2-So 3-Fr 4-Fr 6-Fr 6-Fr 4-Fr 3-So 2-Fr 5-Fr 3-Fr 
          2-Se 2-Ju 2-Ju 3-So 4-Ju 5-So 2-So 4-So 5-Fr 6-Fr 4-Fr 6-So 3-So 4-So 
minute 53: 
          3-Se 2-So 3-So 2-Fr 6-So 2-Fr 3-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 5-So 
          1-Ju 5-So 4-Ju 6-Fr 2-So 2-So 1-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 4-So 
          4-So 2-So 4-So 3-Fr 3-So 3-Fr 4-Fr 6-Fr 6-Fr 4-Fr 3-Fr 2-Fr 5-Fr 
          1-Se 2-Ju 2-Ju 3-So 4-Ju 5-So 2-So 4-So 5-Fr 6-Fr 4-Fr 6-So 3-So 4-So 
minute 54: 
          2-Se 2-So 3-So 2-Fr 6-So 2-Fr 3-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 5-So 
          4-Ju 5-So 1-So 6-Fr 2-So 2-So 4-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 
          2-Ju 2-So 4-So 3-Fr 3-So 3-Fr 4-So 6-Fr 6-Fr 4-Fr 3-Fr 2-Fr 5-Fr 4-Fr 5-So 
          2-Ju 2-Ju 2-So 3-So 4-Ju 5-So 4-So 4-So 5-Fr 6-Fr 4-Fr 6-So 3-So 
minute 55: 
          1-Se 2-So 3-So 2-Fr 6-So 2-Fr 3-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 5-So 
          3-Ju 5-So 1-So 6-Fr 2-So 2-So 4-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 4-Fr 
          1-Ju 2-So 4-So 3-Fr 3-So 3-Fr 4-So 6-Fr 6-Fr 4-Fr 3-Fr 2-Fr 5-Fr 4-Fr 5-So 
          5-Se 2-Ju 2-Ju 3-So 4-Ju 5-So 2-So 4-So 5-Fr 6-Fr 4-Fr 6-So 3-So 4-So 
minute 56: 
          3-So 2-So 3-So 2-So 6-So 2-Fr 5-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 2-Fr 
          2-Ju 5-So 1-So 6-Fr 2-So 2-So 4-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 4-Fr 
          4-So 2-So 4-So 3-Fr 3-So 3-Fr 5-So 6-Fr 6-Fr 4-Fr 3-Fr 2-Fr 5-Fr 4-Fr 
          4-Se 2-Ju 2-Ju 3-So 4-Ju 5-So 4-Ju 4-So 5-Fr 6-Fr 4-Fr 6-So 3-So 4-So 2-So 
minute 57: 
          2-Se 3-So 3-So 2-So 6-So 2-Fr 5-So 2-So 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 2-Fr 2-Fr 
          1-Ju 5-So 1-So 6-Fr 2-So 2-So 4-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 4-Fr 
          3-So 2-So 4-So 3-Fr 3-So 3-Fr 5-So 6-Fr 6-Fr 4-Fr 3-Fr 2-Fr 5-Fr 4-Fr 5-So 
          3-Se 2-Ju 2-Ju 3-So 4-Ju 5-So 4-Ju 4-So 5-Fr 6-Fr 4-Fr 6-So 3-So 4-So 2-So 
minute 58: 
          1-Se 3-So 3-So 2-So 6-So 2-Fr 5-So 2-So 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 2-Fr 2-Fr 
          3-Se 1-Ju 1-So 5-So 2-So 2-So 4-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 4-Fr 6-Fr 
          4-Ju 3-So 4-So 2-So 3-So 3-Fr 5-So 3-Fr 6-Fr 4-Fr 3-Fr 2-Fr 5-Fr 4-Fr 5-So 6-Fr 
          2-Se 2-Ju 2-Ju 3-So 4-Ju 5-So 4-Ju 4-So 5-Fr 6-Fr 4-Fr 6-So 3-So 4-So 2-So 
minute 59: 
          2-Se 3-So 3-So 2-So 6-So 2-Fr 5-So 2-So 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 2-Fr 2-Fr 
          2-Se 1-Ju 1-So 5-So 2-So 2-So 4-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 4-Fr 6-Fr 
          3-Ju 3-So 4-So 2-So 3-So 3-Fr 5-So 3-Fr 6-Fr 4-Fr 3-Fr 2-Fr 5-Fr 4-Fr 5-So 6-Fr 
          1-Se 2-Ju 2-Ju 3-So 4-Ju 5-So 4-Ju 4-So 5-Fr 6-Fr 4-Fr 6-So 3-So 4-So 2-So 5-Fr 

****************************************************/