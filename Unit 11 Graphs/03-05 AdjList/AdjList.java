// Name: J2-07-23
// Date: 05/30/2023
 
import java.util.*;
import java.io.*;

/* Resource classes and interfaces
 * for use with Graphs3: EdgeList,
 *              Graphs4: DFS-BFS
 *          and Graphs5: EdgeListCities
 */

/**************** Graphs 3: EdgeList *****/
interface VertexInterface
{
   public String getName();
   public HashSet<Vertex> getAdjacencies();
   
   /*
     postcondition: if the set already contains a vertex with the same name, the vertex v is not added
                    because adjacencies is a HashSet, this method should operate in O(1)
   */
   public void addAdjacent(Vertex v);
   /*
     postcondition:  returns as a string one vertex with its adjacencies, without commas.
                     for example, D [C A]
     */
   public String toString(); 
 
} 
 
/*************************************************************/
class Vertex implements VertexInterface, Comparable<Vertex> //2 vertexes are equal if and only if they have the same name
{
   private final String name;
   private HashSet<Vertex> adjacencies;
   
   public Vertex(String vName){
      name = vName;
      adjacencies = new HashSet<Vertex>();
   }
   
   public String getName(){
      return name;
   }
   
   public HashSet<Vertex> getAdjacencies(){
      return adjacencies;
   }
   
   /*
     postcondition: if the set already contains a vertex with the same name, the vertex v is not added
                    because adjacencies is a HashSet, this method should operate in O(1)
   */
   public void addAdjacent(Vertex v){
      adjacencies.add(v);
   }
  
   /*
     postcondition:  returns as a string one vertex with its adjacencies, without commas.
                     for example, D [C A]
     */
   public String toString(){
      String ret = name + " [";
      for(Vertex v : adjacencies)
         ret += v.getName() + " ";
      return ret.trim() + "]";
   }
   
   public int hashCode(){
      return name.hashCode();
   }
   
   public boolean equals(Object o){
      return this.hashCode() == o.hashCode();
   }
   
   public int compareTo(Vertex v){
      return (this.equals(v) ? 0 : name.compareTo(v.getName()));
   }
}   

/*************************************************************/
interface AdjListInterface 
{
   public Set<Vertex> getVertices();
   public Vertex getVertex(String vName);
   public Map<String, Vertex> getVertexMap();  //this is just for codepost testing
   
   /*      
      postcondition: if a Vertex with the name v exists, then the map is unchanged.
                     addVertex should work in O(log n)
   */
   public void addVertex(String vName);
   
   /*
      precondition:  both Vertexes, source and target, are already stored in the graph.
      postcondition:  addEdge should work in O(log n)
   */
   public void addEdge(String source, String target); 
   
   /*
       returns the whole graph as one string, e.g.:
       A [C]
       B [A]
       C [C D]
       D [C A]
     */
   public String toString(); 

}

  
/********************** Graphs 4: DFS and BFS *****/
interface DFS_BFS
{
   public String depthFirstSearch(String name);
   public String breadthFirstSearch(String name);
   /*   extra credit  */
   public String depthFirstRecur(String name);
   public List<Vertex> depthFirstRecurHelper(Vertex v, List<Vertex> reachable);
}

/****************** Graphs 5: Edgelist with Cities *****/
interface EdgeListWithCities
{
   public void readData(String cities, String edges) throws FileNotFoundException;
   public int edgeCount();
   public int vertexCount();
   public boolean isReachable(String source, String target);
   public boolean isStronglyConnected(); //return true if every vertex is reachable from every 
                                          //other vertex, otherwise false 
}


/*************  start the Adjacency-List graph  *********/
public class AdjList implements AdjListInterface//, DFS_BFS, EdgeListWithCities
{
   //we want our map to be ordered alphabetically by vertex name
   private Map<String, Vertex> vertexMap = new TreeMap<String, Vertex>();
  
   /********** Graphs 3: EdgeList  ***********/
   public Set<Vertex> getVertices(){
      Set<Vertex> set = new TreeSet<Vertex>();
      for(String s : vertexMap.keySet())
         set.add(vertexMap.get(s));
      return set;
   }
   
   public Vertex getVertex(String vName){
      return vertexMap.get(vName);
   }
   
   public Map<String, Vertex> getVertexMap(){
      return vertexMap;
   }
   
   /*      
      postcondition: if a Vertex with the name v exists, then the map is unchanged.
                     addVertex should work in O(log n)
   */
   public void addVertex(String vName){
      if(!vertexMap.containsKey(vName))
         vertexMap.put(vName, new Vertex(vName));
   }

   /*
      precondition:  both Vertexes, source and target, are already stored in the graph.
      postcondition:  addEdge should work in O(log n)
   */
   public void addEdge(String source, String target){
      vertexMap.get(source).getAdjacencies().add(vertexMap.get(target));
   }
   
   /*
       returns the whole graph as one string, e.g.:
       A [C]
       B [A]
       C [C D]
       D [C A]
     */
   public String toString(){
      String ret = "";
      for(String s : vertexMap.keySet())
         ret += vertexMap.get(s).toString() + "\n";
      return ret;
   }
   
   /********** Graphs 4: DFS and BFS ***********/ 
   public String depthFirstSearch(String name){
      List<Vertex> reachables = new ArrayList<>();
      Stack<Vertex> s = new Stack<>();
      s.push(vertexMap.get(name));
      while(!s.isEmpty()){
         Vertex ver = s.pop();
         if(!reachables.contains(ver))
            reachables.add(ver);
         depthSearchHelper(ver, s, reachables);
      }
      
      return listToString(reachables);
   }
   
   private void depthSearchHelper(Vertex v, Stack s, List<Vertex> l){
      for(Vertex ver : v.getAdjacencies())
         if(!l.contains(ver))
            s.push(ver);
   }
   
   public String breadthFirstSearch(String name){
      List<Vertex> reachables = new ArrayList<>();
      Queue<Vertex> q = new LinkedList<Vertex>();
      
      q.add(vertexMap.get(name));
            
      while(!q.isEmpty()){
         Vertex ver = q.remove();
         if(!reachables.contains(ver))
            reachables.add(ver);
         breadthSearchHelper(ver, q, reachables);
      }
      
      return listToString(reachables);
   }
   
   private void breadthSearchHelper(Vertex v, Queue q, List<Vertex> l){
      for(Vertex ver : v.getAdjacencies())
         if(!l.contains(ver))
            q.add(ver);
   }
   
   private String listToString(List<Vertex> l){
      String ret = "";
      for(Vertex v : l)
         ret += v.getName() + " ";
      return ret;
   }
   
   /*   extra credit */
   public String depthFirstRecur(String name){
      return listToString(depthFirstRecurHelper(vertexMap.get(name), new ArrayList<>()));
   }
   
   public List<Vertex> depthFirstRecurHelper(Vertex v, List<Vertex> reachable){
      reachable.add(v);
      for(Vertex adjVertex : reverseSet(v.getAdjacencies()))
         if(!reachable.contains(adjVertex))
            depthFirstRecurHelper(adjVertex, reachable);
      return reachable;
   }
   
   private TreeSet<Vertex> reverseSet(HashSet<Vertex> set){
      List<Vertex> l = new ArrayList<>(set);
      TreeSet<Vertex> reverse = new TreeSet<>();
      
      for(int i = l.size() - 1; i >= 0; i--)
         reverse.add(l.get(i));
      
      return reverse;
   }
   
   /********** Graphs 5: EdgeListWithCities  ***********/
   public void readData(String cities, String edges) throws FileNotFoundException {
      Scanner city = new Scanner(new File(cities));
      while (city.hasNext())
         addVertex(city.next());
      
      Scanner edge = new Scanner(new File(edges));
      while(edge.hasNext())
         addEdge(edge.next(), edge.next());
   }
   
   public int edgeCount() {
      int count = 0;
      for(String key : vertexMap.keySet())
         for(Vertex val : vertexMap.get(key).getAdjacencies())
            count++;
      return count;
   }
   
   public int vertexCount() {
      return vertexMap.size();
   }
   
   public boolean isReachable(String source, String target) {
      return depthFirstSearch(source).contains(target);
   }
   
   //A directed graph is "strongly connected" if there is a directed
   //   path between every pair of vertices.
   //@return true if every vertex is reachable from every 
   //   other vertex, otherwise false depthFirstSearch(vertexMap.get(key).getName()).length() < lastIndex
   public boolean isStronglyConnected(){
      boolean connected = true;
      for(String key : vertexMap.keySet())
         for(String val : vertexMap.keySet())
            if(!isReachable(key, val))
               connected = false;
      return connected;
   }
}