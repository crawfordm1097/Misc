import java.util.LinkedList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Implementations of various graph algorithms.
 *
 * @author Mikayla Crawford
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search on the given graph, starting at the
     * start Vertex. The graph can either be directed or undirected.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that the adjacency list returns
     * neighbors
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start, Graph<T> graph) {
      if (start == null || graph == null) throw new IllegalArgumentException("Cannot perform BFS with null arguments.");

      Map<Vertex<T>, List<VertexDistancePair<T>>> ajMap = graph.getAdjacencyList();

      if (ajMap.get(start) == null) throw new IllegalArgumentException("Cannot perform BFS if start vertex isn't in the graph.");

      List<Vertex<T>> list = new LinkedList<Vertex<T>>();
      Queue<Vertex<T>> queue = new LinkedList<Vertex<T>>();
      Set<Vertex<T>> visited = new HashSet<Vertex<T>>(); //Tracks whether the node has been visited

      //Add start vertex
      queue.add(start);
      visited.add(start);
      list.add(start);

      //Loop while the queue isn't empty
      while (!queue.isEmpty()) {
        Vertex<T> temp = queue.poll();
        List<VertexDistancePair<T>> ajList = ajMap.get(temp);

        //Loops through adjacent vertices
        for (int i = 0; i < ajList.size(); i++) {
          Vertex<T> curr = ajList.get(i).getVertex(); //Curr adjacent vertex

          //If curr hasn't been visited
          if (!visited.contains(curr)) {
            queue.add(curr);
            visited.add(curr);
            list.add(curr);
          }
        }
      }

      return list;
    }

    /**
     * Performs a depth first search on the given graph, starting at the start
     * Vertex. The graph can either be directed or undirected.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that the adjacency list returns
     * neighbors
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start, Graph<T> graph) {
      if (start == null || graph == null) throw new IllegalArgumentException("Cannot perform DFS with null arguments.");

      Map<Vertex<T>, List<VertexDistancePair<T>>> ajMap = graph.getAdjacencyList();

      if (ajMap.get(start) == null) throw new IllegalArgumentException("Cannot perform DFS if start vertex isn't in the graph.");

      List<Vertex<T>> visited = new LinkedList<Vertex<T>>();
      visited.add(start);

      return dfsHelper(start, ajMap, visited);
    }

    /**
      * Helper recursive function for a DFS. Other ways to implement the search
      * include using explicit stacks instead of the program stack.
      *
      * @param vertex the vertex to perform the DFS on
      * @param ajMap the map of all adjacency lists
      * @param visited a list of visited vertices
      * @return a List of vertices in the order that the adjacency list returns
      * neighbors
      */
    private static <T> List<Vertex<T>> dfsHelper(Vertex<T> vertex, Map<Vertex<T>, List<VertexDistancePair<T>>> ajMap, List<Vertex<T>> visited) {
      List<VertexDistancePair<T>> ajList = ajMap.get(vertex);

      for (int i = 0; i < ajList.size(); i++) {
        Vertex<T> curr = ajList.get(i).getVertex(); //Curr adjacent vertex

        if (!visited.contains(curr)) {
          visited.add(curr);
          dfsHelper(curr, ajMap, visited);
        }
      }

      return visited;
    }

    /**
     * Finds the shortest distance between the start vertex and all other
     * vertices given a weighted graph where the edges only have positive
     * weights. Works with both directed and undirected graphs.
     *
     * It returns a map of the shortest distances such that the key of each
     * entry is a node in the graph and the value for the key is the shortest
     * distance to that node from start, or Integer.MAX_VALUE (infinity) if no
     * path exists. A vertex to itself has a distance of 0.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex to start at
     * @param graph the Graph to search
     * @param <T> the data type representing the vertices in the graph.
     * @return a map of the shortest distances from start to every other node
     *         in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start, Graph<T> graph) {
      if (start == null || graph == null) throw new IllegalArgumentException("Cannot perform Dijkstras Algorithm with null arguments.");

      Map<Vertex<T>, List<VertexDistancePair<T>>> ajMap = graph.getAdjacencyList();

      if (ajMap.get(start) == null) throw new IllegalArgumentException("Cannot perform Dijkstras Algorithm if start vertex isn't in the graph.");

      Map<Vertex<T>, Integer> paths = new HashMap<Vertex<T>, Integer>(); //Map of all distances
      PriorityQueue<VertexDistancePair<T>> queue = new PriorityQueue<VertexDistancePair<T>>();

      //Add start with distance 0 from itself
      paths.put(start, 0);

      //Add all vertices connected to start
      for (VertexDistancePair<T> pair : ajMap.get(start)) {
        queue.add(pair);
        paths.put(pair.getVertex(), pair.getDistance());
      }

      //Adds all vertices not connected to start (initial infinte distance away)
      for (Vertex<T> vertex : ajMap.keySet()) {
        if (!paths.containsKey(vertex)) paths.put(vertex, Integer.MAX_VALUE);
      }

      //Loops while the queue isn't empty
      while (!queue.isEmpty()) {
        VertexDistancePair<T> currPair = queue.poll(); //Current connection
        Vertex<T> vert = currPair.getVertex();
        int dist = currPair.getDistance();

        //Loops through all connections to current vertex
        for (VertexDistancePair<T> connectPair : ajMap.get(vert)) {
          Vertex<T> connectV = connectPair.getVertex(); //New connected vertex
          int altDist = connectPair.getDistance() + dist; //Possible minimum distance

          //If new path distance is smaller than previous found one
          if (altDist < paths.get(connectV)) {
            paths.put(connectV, altDist); //Add new distance to map
            queue.add(new VertexDistancePair<T>(connectV, altDist)); //Add new path
          }
        }

      }

      return paths;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the minimum
     * spanning tree in the form of a set of Edges. If the graph is
     * disconnected, and therefore there is no valid MST, it returns null. It
     * is intended for an undirected graph and works better for a sparse graph.
     *
     * @throws IllegalArgumentException if graph is null
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return the MST of the graph; null if no valid MST exists.
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
      if (graph == null) throw new IllegalArgumentException("Cannot find MST of null graph with Kruskal's.");
      Set<Edge<T>> mst = new HashSet<Edge<T>>();
      Map<Vertex<T>, DisjointSet> pos = new HashMap<Vertex<T>, DisjointSet>(); //Tracks each vertices' DisjointSet
      PriorityQueue<Edge<T>> edgeQueue = new PriorityQueue<Edge<T>>();
      Map<Vertex<T>, List<VertexDistancePair<T>>> ajList = graph.getAdjacencyList();

      //If the graph is disconnected (less edges than vertices - 1) -> initial catch
      if (graph.getEdgeList().size() < ajList.keySet().size() - 1) return null;

      //Initializes every vertex with a empty set
      for (Vertex<T> vertex : ajList.keySet()) {
        pos.put(vertex, new DisjointSet());
      }

      //Adds all edges to min priority queue
      for (Edge<T> edge : graph.getEdgeList()) {
        edgeQueue.add(edge);
      }

      //Loops while there are still edges left
      while (!edgeQueue.isEmpty()) {
        Edge<T> minEdge = edgeQueue.poll();
        DisjointSet vSet = pos.get(minEdge.getV());
        DisjointSet uSet = pos.get(minEdge.getU());

        //If sets don't have the same parent
        if (vSet.find() != uSet.find()) {
          vSet.union(uSet); //Merge sets
          mst.add(minEdge); //Add edge
        }
      }

      //If the graph is disconnected (less edges in MST than there are edges) -> final catch
      if (mst.size() < ajList.size() - 1) return null;

      return mst;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the minimum
     * spanning tree in the form of a set of Edges. If the graph is
     * disconnected, and therefore there is no valid MST, it returns null. It
     * is intended for an undirected graph and works better for a dense graph.
     *
     * @throws IllegalArgumentException if vertex or graph is null
     * @param start the Vertex to start the MST search at
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return the MST of the graph; null if no valid MST exists.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
      if (start == null || graph == null) throw new IllegalArgumentException("Cannot find MST of null graph with Prim's.");
      Set<Edge<T>> mst = new HashSet<Edge<T>>();
      Set<Vertex<T>> notFound = new HashSet<Vertex<T>>();
      PriorityQueue<Edge<T>> edgeQueue = new PriorityQueue<Edge<T>>();
      Map<Vertex<T>, List<VertexDistancePair<T>>> ajList = graph.getAdjacencyList();

      //If the graph is disconnected (less edges than vertices - 1) -> initial catch
      if (graph.getEdgeList().size() < ajList.keySet().size() - 1) return null;

      //Initliaze all vertices as not found
      for (Vertex<T> vert : ajList.keySet()) {
        notFound.add(vert);
      }

      //Adds all non-loop edges from start to edgeQueue
      for (VertexDistancePair<T> vdp : ajList.get(start)) {
        if (!vdp.getVertex().equals(start)) edgeQueue.add(new Edge(start, vdp.getVertex(), vdp.getDistance(), false));
      }

      notFound.remove(start);

      //Loops while the queue isn't empty
      while (!edgeQueue.isEmpty()) {
        Edge<T> curr = edgeQueue.poll();
        Vertex<T> v = curr.getV();
        Vertex<T> u = curr.getU();

        //Curr edge is valid
        if (notFound.contains(u) ^ notFound.contains(v)) {
          mst.add(curr); //Add to MST

          Vertex<T> newVertex = (notFound.contains(v) ? v : u);
          notFound.remove(newVertex);

          //Loops through all new adjacent vertices
          for (VertexDistancePair<T> pair : ajList.get(newVertex)) {
            edgeQueue.add(new Edge(newVertex, pair.getVertex(), pair.getDistance(), false));
          }
        }
      }

      //If the graph is disconnected (less edges in MST than there are edges) -> final catch
      if (mst.size() < ajList.size() - 1) return null;

      return mst;
    }
}
