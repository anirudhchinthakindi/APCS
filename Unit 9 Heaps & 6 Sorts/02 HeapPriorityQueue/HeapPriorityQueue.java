// Name: J2-07-23
// Date: 05/09/23
 
import java.util.*;

/* implement the API for java.util.PriorityQueue
 *     a min-heap of objects in an ArrayList<E> in a resource class
 * test this class with HeapPriorityQueue_Driver.java.
 * test this class with LunchRoom.java.
 * add(E) and remove()  must work in O(log n) time
 */
public class HeapPriorityQueue<E extends Comparable<E>> 
{
   private ArrayList<E> myHeap;
   
   public HeapPriorityQueue()
   {
      myHeap = new ArrayList<E>();
      myHeap.add(null);
   }
   
   public ArrayList<E> getHeap()   //for Codepost
   {
      return myHeap;
   }
   
   public int lastIndex()
   {
      return myHeap.size() - 1;
   }
   
   public boolean isEmpty()
   {
      return (myHeap.size() < 2);
   }
   
   public boolean add(E obj)
   {
      myHeap.add(obj);
      heapUp(lastIndex());
      return true;
   }
   
   public E remove()
   {
      if(this.isEmpty()){
         return null;
      }
      int size = myHeap.size();
      E ret = peek();
      
      if(myHeap.size() == 2){
         myHeap.remove(1);
      }
      else{
         swap(1, lastIndex());
         myHeap.remove(lastIndex());
         heapDown(1, myHeap.size());
      }
      return ret;
   }
   
   public E peek()
   {
      return this.isEmpty() ? null : myHeap.get(1);
   }
   
   //  it's a min-heap of objects in an ArrayList<E> in a resource class
   public void heapUp(int k)
   {
      E half = myHeap.get(k/2);
      E val = myHeap.get(k);
      if(k/2 >= 1)
         if(half.compareTo(val) > 0){
            swap(k, k/2);
            heapUp(k/2);
         }
   }
   
   private void swap(int a, int b)
   {
      E temp = myHeap.get(a);
      myHeap.set(a, myHeap.get(b));
      myHeap.set(b, temp);
   }
   
  //  it's a min-heap of objects in an ArrayList<E> in a resource class
   public void heapDown(int k, int lastIndex)
   {
      int left = 2 * k;
      int right = left + 1;
      int max;
      E maxVal;
      E leftVal;
      E rightVal;
      
      if(k > lastIndex)
         return;
      else if((left > lastIndex) && (right > lastIndex))
         return;
      else
         if(right < lastIndex){
            leftVal = myHeap.get(left);
            rightVal = myHeap.get(right);
            if(leftVal.compareTo(rightVal) < 0)
               max = left;
            else
               max = right;
            maxVal = myHeap.get(max);
            if(myHeap.get(k).compareTo(maxVal) > 0 ){
               swap(k, max);
               heapDown(max, lastIndex);
            }
         }
   }
   
   public String toString()
   {
      return myHeap.toString();
   }  
}
