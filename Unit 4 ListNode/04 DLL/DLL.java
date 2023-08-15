// Name: J2-07-2022
// Date: 12/11/2022

//  DoubleLinkedList, circular, with a dummy head node
//  implements some of the List and LinkedList interfaces: 
//	 	  size(), add(i, o), remove(i);  addFirst(o), addLast(o); 
//  This class also overrides toString().
//  the list is zero-indexed.
//  Uses DLNode.

class DLL  
{
   private int size;
   private DLNode head; //points to a dummy node--very useful--don't mess with it
   public DLL()  
   {
      //make it circular
      head = new DLNode(null, head, head);
      size = 0;
   } 
   
   /* two accessor methods  */
   public int size()
   {
      return size;
   }
   public DLNode getHead()
   {
      return head;
   }
   
   /* appends obj to end of list; increases size;
   	  @return true  */
   public boolean add(Object obj)
   {
      addLast(obj);
      return true;
   }
   
   /* inserts obj at position index (the list is zero-indexed).  
      increments size. 
      no need for a special case when size == 0.
	   */
   public void add(int index, Object obj) throws IndexOutOfBoundsException  //this the way the real LinkedList is coded
   {
      if( index > size || index < 0 )
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      /* enter your code below  */
      if(size == 0){
         DLNode size0 = new DLNode(obj, head, head);
         head.setNext(size0);
         head.setPrev(size0);
      }
      else{
         DLNode p = head;
         for(int i = 0; i < index; i++)
            p = p.getNext();
         DLNode newThing = new DLNode(obj, p, p.getNext());
         p.getNext().setPrev(newThing);
         p.setNext(newThing);
      }
      size++;
   }
   
    /* return obj at position index (zero-indexed). 
    */
   public Object get(int index) throws IndexOutOfBoundsException
   { 
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      /* enter your code below  */
      DLNode p = head;
      for(int i=0; i <= index; i++)
         p = p.getNext();
      return p.getValue();
   }
   
   /* replaces obj at position index (zero-indexed). 
        returns the obj that was replaced.
        */
   public Object set(int index, Object obj) throws IndexOutOfBoundsException
   {
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      /* enter your code below  */
      DLNode p = head;
      for (int i=0; i <= index; i++)
         p = p.getNext();
      Object value = p.getValue();
      p.setValue(obj);
      return value;
   }
   
   /*  removes the node from position index (zero-indexed).  decrements size.
       @return the object in the node that was removed. 
        */
   public Object remove(int index) throws IndexOutOfBoundsException
   {
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      /* enter your code below  */
      DLNode p = head;
      for(int i=0; i <= index; i++)
         p = p.getNext();
      Object value = p.getValue();
      p.getPrev().setNext(p.getNext());
      p.getNext().setPrev(p.getPrev());
      size--;
      return value;
   }
   
  	/* inserts obj to front of list, increases size.
	    */ 
   public void addFirst(Object obj)
   {
      if (size == 0){
         DLNode size0 = new DLNode(obj, head, head);
         head.setNext(size0);
         head.setPrev(size0);
      }
      else{
         DLNode first = new DLNode(obj, head, head.getNext());
         head.setNext(first);
         first.getNext().setPrev(first);
      }
      size++;
   }
   
   /* appends obj to end of list, increases size.
       */
   public void addLast(Object obj)
   {
      if (size == 0){
         DLNode size0 = new DLNode(obj, head, head);
         head.setNext(size0);
         head.setPrev(size0);
      }
      else{
         DLNode last = new DLNode(obj, head.getPrev(), head);
         head.getPrev().setNext(last);
         head.setPrev(last);
      }
      size++;
   }
   
   /* returns the first element in this list  
      */
   public Object getFirst()
   {
      return head.getNext().getValue();
   }
   
   /* returns the last element in this list  
     */
   public Object getLast()
   {
      return head.getPrev().getValue();
   }
   
   /* returns and removes the first element in this list, or
      returns null if the list is empty  
      */
   public Object removeFirst()
   {
      if (size == 0) 
         return null;
      size--;
      Object first = head.getNext().getValue();
      head.getNext().getNext().setPrev(head);
      head.setNext(head.getNext().getNext());
      return first;
   }
   
   /* returns and removes the last element in this list, or
      returns null if the list is empty  
      */
   public Object removeLast()
   {
      if (head.getPrev() == head) 
         return null;
      Object last = head.getPrev().getValue();
      head.getPrev().getPrev().setNext(head);
      head.setPrev(head.getPrev().getPrev());
      size--;
      return last;
   }
   
   /*  returns a String with the values in the list in a 
       friendly format, for example   [Apple, Banana, Cucumber]
       The values are enclosed in [], separated by one comma and one space.
    */
   public String toString()
   {
      if (size == 0)
         return "[]";
      String s = "";
      for(DLNode pointer = head.getNext(); pointer != head; pointer = pointer.getNext()){
         s += pointer.getValue();
         if(pointer.getNext() != head)
            s += ", ";
      }
      return "[" + s + "]";
   }
}