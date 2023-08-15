// Name: J2/07/22
// Date: 11/14/2022

public class Widget implements Comparable<Widget>
{
   //fields
   private int cubits;
   private int hands;
   
   //constructors
   public Widget(){
      cubits = 0;
      hands = 0;
   }
   public Widget(int c, int h){
      cubits = c;
      hands = h;
   }
   public Widget(Widget w){
      cubits = w.cubits;
      hands = w.hands;
   }
   
   //accessors and modifiers
   public int getCubits(){
      return cubits;
   }
   public int getHands(){
      return hands;
   }
   public void setCubits(int c){
      cubits = c;
   }
   public void setHands(int h){
      hands = h;
   }

   //compareTo and equals
   public int compareTo(Widget w){
      if(cubits < w.cubits)
         return -1;
      else if(cubits > w.cubits)
         return 1;
      else{
         if(hands < w.hands)
            return -1;
         else if(hands > w.hands)
            return 1;
         else
            return 0;
      }
   }
   public boolean equals(Object o){
      if (!(o instanceof Widget))
         return false;
      Widget w = (Widget)o;
      return cubits==w.cubits && hands==w.hands;
   }
   public boolean equals(Widget w){
      return cubits==w.cubits && hands==w.hands;
   }
   
   //toString
   public String toString(){
      return cubits+" cubits "+hands+" hands";
   }
}