# Weighted graph 
## This is an implementation of a not-directional (positive) weighted graph in java.

$ git clone https://github.com/mor234/Ex1-weighted-graph.git

was done as a student assignment. 
the project contains the following files: 
### Interfaces:
- weighted_graph.java
- weighted_graph_algorithms.java
- node_info.java
### Implementations:
- WGraph_DS.java
- WGraph_Algo.java
### Comparator: 
- NodesComparator.java (used for a part of an algorithm in class WGraph_Algo) 

### JUNIT tests:
- WGraph_DSTest.java
- WGraph_AlgoTest.java


### The graph is implemented as a hash map of nodes, each node contains the information about him and a Hash map of the node neighbors and the weight of the edge connecting them.

The class implements the corresponding interfaces: node_info, weighted_graph, weighted_graph_algorithms (written by boaz benmoshe). 

**WGraph_DS:**
A class representing a weighted graph, implemented using HashMap,    
allows access to variables in O(1).    
Each node in the graph is a Node_info, implemented using inside class Node_info which implements the interface node_info.       

The class allows basic operations on a graph in 0(1), such as:   
- addNode(int key): adding a node to the graph
- getNode(int key) 
- connect(int node1, int node2, double w):connecting two nodes
- hasEdge(int node1, int node2)  
etc.

the following methods has different time comlexity:
Collection<node_info> getV(int node_id): getting a collection of the node neighbors in  O(k) time, k - being the degree of node_id. happenes because goes through all the keys of the neibores in the hash map of the neighbors inside the no
removing a node, etc.  



    
     This method return a pointer (shallow copy) for a
     Collection representing all the nodes in the graph.
      Note: this method should run in O(1) tim
          public int nodeSize() O(1)

    public int()-O(1)

    public int getMC() O(1)
     public void removeEdge(int node1, int node2)-O(1)
   
    
    public Collection<node_info> getV();- This method return a pointer (shallow copy) for a
     Collection representing all the nodes in the graph.

 public Collection<node_info> getV(int node_id);-O(k) time,This method returns a Collection containing all the
     nodes connected to node_id k - being the degree of node_id.




}

NodeData:  
A class representing a node in a graph.  
Each node contains an a Hash map containing the keys of its neighbors.  

Graph_Algo: A class represent "regular" Graph Theory algorithms using a Graph_DS variable. The class implement the following algorithms: 
clone()- copy of the graph-
 initialize-
 -isConnected()- check if the graph is connected, implemented using the bfs algorithm.
-int shortestPathDist(int src, int dest)- return what the shortest path distance between two nodes, implemented using the bfs algorithm.
List<Node> shortestPath(int src, int dest)- what the shortest path between two nodes, implemented using the bfs algorithm.

