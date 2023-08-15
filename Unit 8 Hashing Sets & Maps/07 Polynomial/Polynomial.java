 // Name: J2-07-23
 // Date: 03/15/2023

import java.util.*;

interface PolynomialInterface
{
   public void makeTerm(Integer exp, Integer coef);
   public Map<Integer, Integer> getMap();
   public double evaluateAt(double x);
   
   //precondition: both polynomials are in standard form
   //postcondition: map with zero disappear. If all map disappear (the size is zero), 
   //               add pair (0,0).
   public Polynomial add(Polynomial other);
   
   //precondition: both polynomials are in standard form
   //postcondition: map with zero disappear. If all map disappear (the size is zero), 
   //               add pair (0,0)
   public Polynomial multiply(Polynomial other);
   public String toString();
}

class Polynomial implements PolynomialInterface {
   private Map<Integer, Integer> map = new HashMap<Integer, Integer>();

   @Override
   public void makeTerm(Integer exp, Integer coef) {
      if(coef != 0)
         map.put(exp, coef);
      else
         map.remove(exp);
   }

   @Override
   public Map<Integer, Integer> getMap() {
      return map;
   }

   @Override
   public double evaluateAt(double x) {
      double result = 0.0;
      for(Map.Entry<Integer, Integer> entry : map.entrySet())
         result += entry.getValue() * Math.pow(x, entry.getKey()); // coefficient * x^exp
      return result;
   }

   @Override
   public Polynomial add(Polynomial other){
      Polynomial result = new Polynomial();
      for(Map.Entry<Integer, Integer> entry : map.entrySet()){
         Integer exp = entry.getKey();
         Integer coef = entry.getValue();
         result.makeTerm(exp, coef);
      }
      for(Map.Entry<Integer, Integer> entry : other.getMap().entrySet()){
         Integer exp = entry.getKey();
         Integer coef = entry.getValue();
         Integer current = result.map.get(exp);
         if(current == null)
            result.makeTerm(exp, coef);
         else
            result.makeTerm(exp, current + coef);
      }
      return result;
   }

   @Override
   public Polynomial multiply(Polynomial other){
      Polynomial result = new Polynomial();
      for(Map.Entry<Integer, Integer> entry1: map.entrySet()) {
         Integer exp1 = entry1.getKey();
         Integer coef1 = entry1.getValue();
         for(Map.Entry<Integer, Integer> entry2: other.getMap().entrySet()){
            Integer exp2 = entry2.getKey();
            Integer coef2 = entry2.getValue();
            Integer exp = exp1 + exp2;
            Integer coef = coef1 * coef2;
            Integer current = result.map.get(exp);
            if(current == null)
               result.makeTerm(exp, coef);
            else
               result.makeTerm(exp, current + coef);
         }
      }
      return result;
   }

   @Override
   public String toString(){
      TreeMap<Integer, Integer> sortedTerms = new TreeMap<Integer, Integer>(Collections.reverseOrder());
      sortedTerms.putAll(map);
      String result = "";
      boolean isFirstTerm = true;
      for(Map.Entry<Integer, Integer> entry: sortedTerms.entrySet()) {
         Integer exp = entry.getKey();
         Integer coef = entry.getValue();
         if(coef != 0) {
            if (isFirstTerm){
               isFirstTerm = false;
               if(coef == 1 && exp > 0)
                  result += "";
               else if (coef == -1 && exp > 0)
                  result += "-";
               else
                  result += coef;
            }
            else{
               if (coef > 0) {
                  result += " + ";
                  if (coef != 1 || exp == 0)
                     result += coef;
               } 
               else {
                  result += " - ";
                  if (coef != -1 || exp == 0) 
                     result += -coef;
               }
            }
            if(exp > 1)
               result += "x^" + exp;
            else if (exp == 1)
               result += "x";
         }
      }
      if(isFirstTerm)
         return "0";
      return result;
   }
}