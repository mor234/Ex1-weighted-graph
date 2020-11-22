# weighted graph 
This is an implementation of a not-directional (positive) weighted graph in java.  
Implemented using 3 class, was done as a student assignment.  
Also has 2 junit tests for each of the   
The graph is implemented as a hash map of nodes containing ArrayLists ot their neighbors.  
The 3 class implements the corresponding interfaces: node_info, weighted_graph,     weighted_graph_algorithms (written by boaz benmoshe).  
 


The classes are:

Graph_DS: A class representing a graph, implemented using HashMap,  
allows access to variables in O(1).  
The class allows basic operations on a graph, such as:   
adding a node to the graph, connecting two nodes, removing a node, etc.  

NodeData:  
A class representing a node in a graph.  
Each node contains an a Hash map containing the keys of its neighbors.  

Graph_Algo: A class represent "regular" Graph Theory algorithms using a Graph_DS variable. The class implement the following algorithms: 
clone()- copy of the graph-
 initialize-
 -isConnected()- check if the graph is connected, implemented using the bfs algorithm.
-int shortestPathDist(int src, int dest)- return what the shortest path distance between two nodes, implemented using the bfs algorithm.
List<Node> shortestPath(int src, int dest)- what the shortest path between two nodes, implemented using the bfs algorithm.

