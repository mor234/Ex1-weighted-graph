package ex1.src;


import ex1.src.node_info;
import ex1.src.weighted_graph;

import java.io.Serializable;
import java.util.*;

/**
 * This class represents an undirectional weighted graph.
 * It should support a large number of nodes (over 10^6, with average degree of 10).
 * Implemented ususing a hash map of nodes, each one contains a hash map ot the edges connected to it.
 * @author mor234
 */
public class WGraph_DS implements weighted_graph, Serializable {


    private HashMap<Integer, node_info> graph;
    private int mc = 0;
    private int numOfEdges = 0;

    /**
     * constructor
     */
    public WGraph_DS() {

        graph = new HashMap<Integer, node_info>();
        mc++;
    }

    /**
     * copy constructor
     * update mc only once for initializing the graph
     *
     * @param graphToCopy
     */
    public WGraph_DS(WGraph_DS graphToCopy) {
        mc++;
        graph = new HashMap<Integer, node_info>();
        if (graphToCopy != null) {
            numOfEdges = graphToCopy.numOfEdges;
            Iterator<node_info> nodesIterator = graphToCopy.graph.values().iterator();
            //copy each node using Node_info copy constructor
            while (nodesIterator.hasNext()) {
                node_info node = nodesIterator.next();
                this.graph.put(node.getKey(), new Node_info((Node_info) node));
            }
        }
    }

    /**
     * Inside class, this class represent a node in a graph.
     * each node contains key, string information, tag and a Hash map of the edges connected to it.
     * the key in the hash map is the key of the connected node, and the values are the weights of the edges
     */
    class Node_info implements node_info, Serializable {
        private int key;
        private String stringInfo;
        private double tag;
        private HashMap<Integer, Double> neighbors;

        /**
         * default constructor
         **/

        public Node_info() {
            this.key = -1;
            this.stringInfo = "";
            this.tag = -1;
            this.neighbors = new HashMap<Integer, Double>();

        }


        /**
         * copy constructor
         **/
        public Node_info(Node_info ni) {
            if (ni != null) {
                this.key = ni.getKey();
                this.stringInfo = ni.getStringInfo();
                this.tag = ni.getTag();
                this.neighbors = new HashMap<Integer, Double>();
                if (ni.neighbors != null) {
                    Iterator<Integer> neighborsIterator = ni.neighbors.keySet().iterator();
                    while (neighborsIterator.hasNext()) {
                        int keyNeighbors = neighborsIterator.next();
                        this.neighbors.put(keyNeighbors, ni.neighbors.get(keyNeighbors));
                    }
                }
            } else {
                this.key = -1;
                this.stringInfo = "";
                this.tag = -1;
                this.neighbors = new HashMap<Integer, Double>();
            }

        }

        /**
         * basic constructor for using in the class WGraph_DS
         **/

        public Node_info(int key) {
            this();
            this.key = key;
        }

        /**
         * check if two nodeInfo are equal, heavily based on the Automatically generated function,
         * compare key, tag, stringInfo and edges connected the the node
         * to be used as part of equales of class WGraph_DS
         *
         * @param o
         * @return true if equal, false if not
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node_info nodeInfo = (Node_info) o;
            boolean ans = getKey() == nodeInfo.getKey() &&
                    Double.compare(nodeInfo.getTag(), getTag()) == 0 &&
                    getStringInfo().equals(nodeInfo.getStringInfo());
            if (!ans)
                return false;
            Iterator<Integer> neighborsIterator = getNeighbors().keySet().iterator();
            //compare the neighbors,for each neighbor in one object, get the same neighbor in the other object
            // and check they have the same weight
            while (neighborsIterator.hasNext()) {
                int thisNodeKey = neighborsIterator.next();
                Double oWeight = nodeInfo.getNeighbors().get(thisNodeKey);
                if (oWeight == null)
                    return false;
                if (Double.compare(getNeighbors().get(thisNodeKey), oWeight) != 0)
                    return false;
            }
            return true;

        }

        /**
         * Automatically generated hash code
         **/
        @Override
        public int hashCode() {
            return Objects.hash(getKey(), getStringInfo(), getTag(), getNeighbors());
        }

        /**
         * Return the hash map of the edges connected to this node
         * the key- is the key of the neighbor this edge is connecting to
         * the value- is the weight of th edge
         * * @return
         */
        private HashMap<Integer, Double> getNeighbors() {
            return neighbors;
        }

        /**
         * Return the key (id) associated with this node.
         * Note: each node_data should have a unique key.
         *
         * @return
         */
        @Override
        public int getKey() {
            return key;
        }

        /**
         * return the remark (meta data) associated with this node.
         *
         * @return
         */
        @Override
        public String getStringInfo() {
            return stringInfo;
        }

        /**
         * Allows changing the remark (meta data) associated with this node.
         *
         * @param s
         */
        @Override
        public void setStringInfo(String s) {
            this.stringInfo = new String(s);

        }

        /**
         * Temporal data (aka distance, color, or state)
         * which can be used be algorithms
         *
         * @return
         */
        @Override
        public double getTag() {
            return tag;
        }

        /**
         * Allow setting the "tag" value for temporal marking an node - common
         * practice for marking by algorithms.
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            tag = t;

        }
         /*
         //to string function for nodeInfo, used for debugging
        @Override
        public String toString()
        {
          return "node id: "+ key +" tag: "+ tag;
        }*/

    }//end of Node_info class

    /**
     * return the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        return this.graph.get(key);
    }

    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        //there isn't an edge between a node to itself
        if (node1 == node2)
            return false;
        Node_info node = (Node_info) this.getNode(node1);
        if (node == null)
            return false;
        return node.getNeighbors().get(node2) != null;
    }

    /**
     * return the weight if the edge (node1, node1). In case
     * there is no such edge - should return -1
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {

        if (node1 == node2) {
            if (graph.containsKey(node1))
                return 0;
        }
        if (hasEdge(node1, node2)) {
            Node_info node = (Node_info) this.getNode(node1);
            return node.getNeighbors().get(node2);
        } else return -1; //there is no such edge

    }

    /**
     * add a new node to the graph with the given key.
     * Note: this method should run in O(1) time.
     * Note2: if there is already a node with such a key -> no action should be performed.
     *
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (!graph.containsKey(key)) {
            mc++;//update changes count
            graph.put(key, new Node_info(key));
        }

    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method should run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     * Note3: if one of the nodes doesn't exist- the method doesn't do anything.
     *
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        //if the weight is not negative
        if (w >= 0) {
            /*can't be any edge between the node to itself*/
            if (node1 == node2)
                return;
            Node_info nodeInfo1 = (Node_info) this.getNode(node1);
            Node_info nodeInfo2 = (Node_info) this.getNode(node2);
            /*if one of the nodes doesn't exist in the graph- don't do any thing*/
            if (nodeInfo1 == null || nodeInfo2 == null) {
                return;
            }

            mc++;//update changes count

            /*if the edge exist- update the edge*/
            if (hasEdge(node1, node2)) {
                nodeInfo1.getNeighbors().replace(node2, w);
                nodeInfo2.getNeighbors().replace(node1, w);
            }
            /*if the edge doesn't exist- create it*/
            else {
                numOfEdges++;
                nodeInfo1.getNeighbors().put(node2, w);
                nodeInfo2.getNeighbors().put(node1, w);
            }
        }
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: this method should run in O(1) tim
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return this.graph.values();
    }

    /**
     * This method checks if too WGraph_DS are equal
     * used automatically generated equals as a base
     * the mc doesn't need to be the same for the graphs to be equals,
     *
     * @param o
     * @return true if the graphs are equals, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        if (numOfEdges != wGraph_ds.numOfEdges || nodeSize() != wGraph_ds.nodeSize())
            return false;
        Iterator<Integer> nodesIterator = graph.keySet().iterator();

        /*for each node in one graph check if the other graph has a node with the same key,
        and check is this nodes are equals,
        using the method equals of class Node_info*/
        while (nodesIterator.hasNext()) {
            int nodeKey = nodesIterator.next();
            node_info nodeThisGraph = graph.get(nodeKey);
            node_info nodeOGraph = wGraph_ds.graph.get(nodeKey);
            if (!nodeOGraph.equals(nodeThisGraph))
                return false;
        }
        return true;
    }

    /**
     * An Automatically generated hash code
     **/

    @Override
    public int hashCode() {
        return Objects.hash(graph, mc, numOfEdges);
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * Note: this method can run in O(k) time, k - being the degree of node_id.
     *
     * @param node_id
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        Collection<node_info> neighborsOfNode = new ArrayList<node_info>();
        /*if the node doesn't exist- return empty collection (it also doesn't have neighbors)*/
        if (graph.get(node_id) != null) {

            Iterator<Integer> IteratorNeighbors = ((Node_info) getNode(node_id)).getNeighbors().keySet().iterator();
            while (IteratorNeighbors.hasNext()) {
                neighborsOfNode.add(graph.get(IteratorNeighbors.next()));
            }
        }

        return neighborsOfNode;
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        Node_info node = (Node_info) getNode(key);
        if (node != null) {
            Iterator<Integer> neighborsKeys = node.getNeighbors().keySet().iterator();
            //remove all the edges connected to the node in the graph
            while (neighborsKeys.hasNext()) {
                removeEdge(key, neighborsKeys.next());
                neighborsKeys = node.getNeighbors().keySet().iterator();
            }
            //after removing the edges, remove the node
            graph.remove(key);
            mc++;//update changes count
        }

        return node;
    }

    /**
     * Delete the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            Node_info nodeInfo1 = (Node_info) this.graph.get(node1);
            Node_info nodeInfo2 = (Node_info) this.graph.get(node2);
            nodeInfo1.getNeighbors().remove(node2);
            nodeInfo2.getNeighbors().remove(node1);
            mc++;//update changes count
            numOfEdges--;//update edges count
        }


    }

    /**
     * return the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return graph.size();
    }

    /**
     * return the number of edges (undirectional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return numOfEdges;
    }


    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     *
     * @return
     */
    @Override
    public int getMC() {
        return mc;
    }

    /**
     * ToString function- convert the graph to String
     * mainly used for debugging
     * @return String representing the graph
     */
    @Override

    public String toString() {
        String graphStr = "";
        Iterator<node_info> i = graph.values().iterator();
        graphStr += "nodes: " + nodeSize() + " edges: " + numOfEdges;
        while (i.hasNext()) {
            Node_info n = (Node_info) i.next();
            graphStr += "\n node id: " + n.getKey();
            if (n.stringInfo != "")
                graphStr += " " + n.stringInfo + " ";
            HashMap<Integer, Double> neighbors = n.getNeighbors();

            if (!neighbors.isEmpty()) {
                Iterator<Integer> keysNeighbors = neighbors.keySet().iterator();
                graphStr += "\n";
                graphStr += "the " + neighbors.size() + " neighbors are: ";
                while (keysNeighbors.hasNext()) {
                    int keyN = keysNeighbors.next();
                    graphStr += "id: " + keyN + " w: " + neighbors.get(keyN) + " ";
                }
            }
        }
        return graphStr;
    }


}

