# Weighted graph 
## This is an implementation of a not-directional (positive) weighted graph in java.
' ' 'sh
$ git clone https://github.com/mor234/Ex1-weighted-graph.git
' ' '

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
allows access to variables in O(1).  implemants the intrefaces weighted_graph, Serializable
Each node in the graph is a Node_info, implemented using inside class Node_info which implements the interface node_info.       

The class allows basic operations on a graph in 0(1), such as:   
- addNode(int key): adding a node to the graph
- getNode(int key) 
- connect(int node1, int node2, double w): connecting two nodes
- hasEdge(int node1, int node2)  
etc.

the following methods has different time comlexity:
- Collection<node_info> getV(int node_id): getting a collection of the node neighbors in  O(k) time, k - being the degree of node_id. happens because goes through all the keys of the neibores in the hash map of the neighbors inside the no
removing a node, etc.  
- removeNode(int key): o(n), |V|=n, as all the edges should be removed

**WGraph_Algo:**
This class representing an Undirected (positive) Weighted Graph Theory algorithms using a WGraph_DS variable.   
The class include the following algorithms:   
- clone(): deep copy of the graph
- init(graph): shallow copy to initialize the WGraph_DS variable
- isConnected()- check if the graph is connected, implemented using the bfs algorithm.
- double shortestPathDist(int src, int dest): return what the shortest path distance between two nodes. implemented using dijkstra's algorithm
- List<node_info> shortestPath(int src, int dest): what the shortest path between two nodes, implemented using dijkstra's algorithm
- Save(file): save the graph, using Serializable in java
- Load(file): load a graph, using Serializable in java

### Tests:
To check the shortestPathDist, shortestPath, Created the following graph;
## ![alt text] (https://github.com/mor234/Ex1-weighted-graph/blob/main/images/distance%20tree.jpg)


