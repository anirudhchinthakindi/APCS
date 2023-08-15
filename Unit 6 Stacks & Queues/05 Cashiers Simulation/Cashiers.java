// Name: J2-07-23
// Date: 01/24/2023

import java.util.*;
import java.io.*;
public class Cashiers
{
   public static final int CUSTOMERS_PER_MINUTE = 2;  
              
   public static void main(String[] args)
   {     
      PrintWriter outfile = setUpFile();      
      
      System.out.println("Cashiers and Customers Simulation! ");
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
  
   public static void outfileCashiersAndQueues(PrintWriter outfile, int min, ArrayList<Queue<Customer>> cashier)
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
      int thisCustomersTime = 0;
      int totalMinutes = 0;
      int longestWaitTime = 0;
      int longestQueue = 0;
   
      ArrayList<Queue<Customer>> cashiers = new ArrayList<>();
      for(int i=0; i<number_of_cashiers; i++)
         cashiers.add(new LinkedList<Customer>());
     /***************************************
           Write your code for the simulation.
           call outfileCashiersAndQueues() to write the queues to the file.  
      **********************************/
      for (int i = 0; i < time; i++){
         for(int p = 0; p < CUSTOMERS_PER_MINUTE; p++){ //adding new Customers
            int minSize = Integer.MAX_VALUE;
            Queue<Customer> minCustomer = new LinkedList<Customer>();
            for(Queue<Customer> q: cashiers){ //finding the largest and smallest queue
               int queueSize = size(q);
               if(queueSize > longestQueue)
                  longestQueue = queueSize;
               if(queueSize < minSize){
                  minSize = queueSize;
                  minCustomer = q;
               }
            }
            minCustomer.add(new Customer(i));
            customers++;
         }
         
         outfileCashiersAndQueues(outfile, i, cashiers);
         
         for(Queue<Customer> q: cashiers){ //decrementing time
            if(!q.isEmpty()){
               Customer c = q.peek();
               c.decrement();
               if(c.getTimeSpent() == 0){
                  thisCustomersTime = i - c.getArrivalTime() + 1;
                  if(longestWaitTime < thisCustomersTime) //figuring out the longest wait time
                     longestWaitTime = thisCustomersTime;
                  totalMinutes += thisCustomersTime;
                  q.remove();
                  customersCheckedOut++;
                  outfile.println("customers served: " + customersCheckedOut + ", Customer from minute " + c.getArrivalTime() + " had total wait time: " + thisCustomersTime + ", total minutes (for all customers): " + totalMinutes);
               }
            }
         }   
      }
      
      /*   report the data to the screen     */
      System.out.println("Number of cashiers = "+ number_of_cashiers);
      System.out.println("Customers joining each minute = "+ CUSTOMERS_PER_MINUTE);
      System.out.println("Customers who joined queues = " + customers);
      System.out.println("Customers who were checked out = " + customersCheckedOut);
      System.out.println("Average wait time of those who were checked out = " + calculateAverage(totalMinutes, customersCheckedOut));
      System.out.println("Longest wait time of those who were checked out = " + longestWaitTime);
      System.out.println("Longest queue of customers = " + longestQueue);
      outfile.close();	
   }
   
   public static int size(Queue<Customer> q){
      int ret = 0;
      for(Customer c: q)
         ret++;
      return ret;
   }
   
   static class Customer      
   {
      private int arrivedAt;
      private int timeSpentAtCashier;
      
      public Customer(int a){
         arrivedAt = a;
         timeSpentAtCashier = (int)((Math.random() * 5) + 2);
      }
      
      public int getArrivalTime(){
         return arrivedAt;      
      }
   
      public int getTimeSpent(){
         return timeSpentAtCashier;      
      }
      
      public void setArrivedAt(int a){
         arrivedAt = a;      
      }
   
      public void setTimeSpent(int t){
         timeSpentAtCashier = t;      
      }
      
      public void decrement(){
         timeSpentAtCashier--;
      }
      
      public String toString(){
         return arrivedAt + ":" + timeSpentAtCashier;
      }
   }
}

/******************************************************
to the screen:
    Cashiers and Customers Simulation! 
    How many cashiers? 3
    How long, in minutes, should the simulation run? 10
    Number of cashiers = 3
    Customers joining each minute = 2
    Customers who joined queues = 20
    Customers who were checked out = 8
    Average wait time of those who were checked out = 5.0
    Longest wait time of those who were checked out = 6
    Longest queue of customers = 5


to the file:

minute 0: 
          2 
          4 
          
   minute 1: 
          1 3 
          3 
          4 
   minute 2: 
          3 
          2 2 
          3 2 
   minute 3: 
          2 2 5 
          1 2 
          2 2 
   minute 4: 
          1 2 5 
          2 4 
          1 2 2 
   minute 5: 
          2 5 2 
          1 4 6 
          2 2 
   minute 6: 
          1 5 2 3 
          4 6 
          1 2 6 
   minute 7: 
          5 2 3 
          3 6 5 5 
          2 6 
   minute 8: 
          4 2 3 6 
          2 6 5 5 
          1 6 3 
   minute 9: 
          3 2 3 6 4 
          1 6 5 5 
          6 3 2 

****************************************************/