// User: J2-07-22
// Date: 08/23/2022

public class Modes
{
   public static void main(String[] args)
   {
      int[] tally = {0,0,10,5,10,0,7,1,0,6,0,10,3,0,0,1};
      //test with other values in the tally
      System.out.println(toString(tally));
      int[] modes = calculateModes(tally);
      System.out.println(toString(modes));
      int sum = 0;
      for (int k = 0; k < tally.length; k++)
         sum += tally[k];
      System.out.println("kth \tindex"); 
      for(int k = 1; k <= sum; k++)
         System.out.println(k + "\t\t" + kthDataValue(tally, k));
   }
     
  /**
   * precondition: tally.length > 0
   * postcondition: returns an int array that contains the modes(s);
   *                the array's length equals the number of modes.
   */
   public static int[] calculateModes(int[] tally)
   {
      int modeValue = findMax(tally); // what the actual mode is of the tally array   
      int modeFrequency = 0; // how many times a mode is in a array
      int empty[] = new int [0];
            
      for (int a = 0; a < tally.length; a++){
         if (tally[a] == modeValue){ // finding out how many times the mode occurs in the data set
            modeFrequency += 1;
         }
         
         
         if (modeFrequency == tally.length){ // if the array is all one number
            return empty;
         }      
      }
      
      int[] modeArray = new int[modeFrequency]; // this is gonna be the array for the modes themselves
      
      for (int a = 0, b = 0; a < tally.length; a++){ 
         if (tally[a] == modeValue){
            modeArray[b] = a; // putting the mode indices into the modeArray
            b++;
         }
      }
      
      int nonZeroFrequency = 0; // storing the number of nonzeros in the array
      
      for (int i = 0; i < tally.length; i++){ // storing how many zeros are in tally
         if (tally[i] != 0){
            nonZeroFrequency += 1;
         }
      }   
      
      if (modeFrequency == nonZeroFrequency){ // if the nummber of zeros is greater than or equal to the number of modes
         return empty;
      }
         
      return modeArray; // returning the values of the mode
   }
     
  /**  
   * precondition:  tally.length > 0
   * 	             0 < k <= total number of values in data collection
   * postcondition: returns the kth value in the data collection
   *                represented by tally
   */
   public static int kthDataValue(int[] tally, int k)
   {
      int totalNumbers = 0; // the size of the array
      
      for (int a = 0; a < tally.length; a++){
         totalNumbers += tally[a]; // running through the array while adding each frequency to the totalNumbers
      }
      
      int[] array = new int[totalNumbers]; // kthDataValue Array
      
      for (int indexOftally = 0, indexOfArray = 0; indexOftally < tally.length; indexOftally++){
         if (tally[indexOftally] > 1){ // seeing if the value needs more than one time to loop
            for (int frequency = 1; frequency < tally[indexOftally] + 1; frequency++){
               array[indexOfArray] = indexOftally; // looping through the array while assigning the value based on the frequency
               indexOfArray++;
            }
         }
         
         else if (tally[indexOftally] == 1){
            array[indexOfArray] = indexOftally; // just prints the value once
            indexOfArray++;      
         }
      }
      
      int kValue = array[k - 1];
      
      return kValue;  //returning the index of the array
   }
     
  /**
   * precondition:  nums.length > 0
   * postcondition: returns the maximal value in nums
   */
   public static int findMax(int[] nums)
   {
      int pos = 0;
      for(int k = 1; k < nums.length; k++)
         if(nums[k] > nums[pos])
            pos = k;
      return nums[pos];
   }
   
   public static String toString(int[] arg)
   {
      if(arg.length == 0)
         return("[]");
      String s = "[";
      for(int k = 0; k < arg.length - 1; k++)
         s += arg[k] + ",";
      s += arg[arg.length - 1] + "]";
      return s;
   }  
}

/****************  SAMPLE RUN  **********************

 [0,0,10,5,10,0,7,1,0,6,0,10,3,0,0,1]
 [2,4,11]
 kth 	index
 1		2
 2		2
 3		2
 4		2
 5		2
 6		2
 7		2
 8		2
 9		2
 10		2
 11		3
 12		3
 13		3
 14		3
 15		3
 16		4
 17		4
 18		4
 19		4
 20		4
 21		4
 22		4
 23		4
 24		4
 25		4
 26		6
 27		6
 28		6
 29		6
 30		6
 31		6
 32		6
 33		7
 34		9
 35		9
 36		9
 37		9
 38		9
 39		9
 40		11
 41		11
 42		11
 43		11
 44		11
 45		11
 46		11
 47		11
 48		11
 49		11
 50		12
 51		12
 52		12
 53		15
 
****************************************/
