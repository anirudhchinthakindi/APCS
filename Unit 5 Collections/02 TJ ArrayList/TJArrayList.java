// Name: J2-07-23
// Date: 01/07/2023

/**
 * Implements the cheat sheet's List interface.  Implements generics.
 * The backing array is an array of (E[]) new Object[10];
 * @override toString()
 */ 

@SuppressWarnings("unchecked")
public class TJArrayList<E>
{
   private int size;							//stores the number of objects
   private E[] myArray;
   @SuppressWarnings("unchecked")
   public TJArrayList()                
   {
      myArray = (E[]) new Object[10]; //default constructor instantiates a raw array with 10 cells
   
      size = 0;
   }
   public int size()
   {
      return size;
   }
   
 	/* appends obj to end of list; increases size;
      must be an O(1) operation when size < array.length, 
         and O(n) when it doubles the length of the array.
	  @return true  */
   @SuppressWarnings("unchecked")
   public boolean add(E obj)
   {
      if(size >= myArray.length){
         E[] arrNew = (E[]) new Object[myArray.length*2];
         for(int i = 0; i <= myArray.length-1; i++)
            arrNew[i] = myArray[i];
         myArray = arrNew;
      }
      myArray[size] = obj;
      size++;
      return true;
   }
   /* inserts obj at position index.  increments size. 
		*/
   public void add(int index, E obj) throws IndexOutOfBoundsException  //this the way the real ArrayList is coded
   {
      if(index > size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      if(size >= myArray.length){
         E[] arrNew = (E[]) new Object[myArray.length*2];
         for(int i = 0; i <= myArray.length-1; i++)
            arrNew[i] = myArray[i];
         myArray = arrNew;
      }
      for(int i = size-1; i >= index; i--)
         myArray[i+1] = myArray[i];
      myArray[index] = obj;
      size++;
   }

   /* return obj at position index.  
		*/
   public E get(int index) throws IndexOutOfBoundsException
   {
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      return myArray[index];
   }
   /**
    * Replaces obj at position index. 
    * @return the object that is being replaced.
    */  
   public E set(int index, E obj) throws IndexOutOfBoundsException  
   { 
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      E replaced = myArray[index];
      myArray[index] = obj;
      return replaced;
   }
 /*  removes the node from position index. shifts elements 
     to the left.   Decrements size.
	  @return the object that used to be at position index.
	 */
   public E remove(int index) throws IndexOutOfBoundsException  
   {
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      size--;
      E removed = myArray[index];
      myArray[index] = myArray[index+1];
      for(int i = index+1; i < myArray.length-1; i++)
         myArray[i] = myArray[i+1];
      return removed; 
   }
	   /*
		   This method compares objects.
         Must use .equals(), not ==
     	*/
   public boolean contains(E obj)
   {
      for(int i = 0; i < myArray.length; i++)
         if(obj.equals(myArray[i])) 
            return true;
      return false;
   }
	 /*returns a String of E objects in the array with 
       square brackets and commas.
     	*/
   public String toString()
   {
      String s = "[";
      for(int i=0; i < size-1; i++)
         s += "" + myArray[i] + ", ";
      s += "" + myArray[size-1] + "]";
      return s;
   }
}