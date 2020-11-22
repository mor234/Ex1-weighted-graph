package ex1.src;



import java.io.*;
import java.util.*;

/**
 * This class represents an Undirected (positive) Weighted Graph Theory algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected(); implemented using bfs algorithm
 * 3. double shortestPathDist(int src, int dest); implemented using dijkstra's algorithm
 * 4. List<node_data> shortestPath(int src, int dest); implemented using dijkstra's algorithm
 * 5. Save(file);
 * 6. Load(file);
 *
 * @author mor234
 */

public class WGraph_Algo implements weighted_graph_algorithms {

    private WGraph_DS graphA;

    /**
     * default constructor
     **/
    public WGraph_Algo() {
        this.graphA = new WGraph_DS();
    }

    /**
     * copy constructor
     **/
    public WGraph_Algo(weighted_graph_algorithms g) {
        if (g != null) {
            this.graphA = new WGraph_DS((WGraph_DS) g.getGraph());
        } else
            this.graphA = new WGraph_DS();
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     * shallow copy of the given graph
     *
     * @param g- the graph
     */

    @Override
    public void init(weighted_graph g) {
        this.graphA = (WGraph_DS) g;//shallow copy
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return graphA;
    }

    /**
     * Compute a deep copy of this weighted graph.
     *
     * @return
     */
    @Override
    public weighted_graph copy() {
        return new WGraph_DS(this.graphA);
    }

    /**
     * This method sets the tags to -1 and  Strings to ""
     * for all the nodes of the graph variable,
     * to be used in another function
     **/
    private void setTagsAndStrings() {
        Iterator<node_info> nodeIt = graphA.getV().iterator();
        while (nodeIt.hasNext()) {
            node_info node = nodeIt.next();
            node.setTag(-1);
            node.setStringInfo("");
        }
    }

    /**
     * Returns true if and only if (iff) there is a valid path from EVERY node to each
     * other node, using the bfs algorithm. NOTE: assume ubdirectional graph.
     *
     * @return
     */
    @Override

    public boolean isConnected() {
        /*if there aren't any nodes in the graph,
         * it counts like all of the node are connected*/
        if (this.graphA.nodeSize() == 0)
            return true;

        int numOfNodesToGo = this.graphA.nodeSize();

        node_info node = graphA.getV().iterator().next();
        ArrayDeque<node_info> help_queue = new ArrayDeque<node_info>();

        //initialise tags for the algorithm
        setTagsAndStrings();

        //enter the queue the first node from the graph
        help_queue.addLast(node);
        //change tag to 0- sign it's entered the queue already
        node.setTag(0);

        // create variable nodeNeighbor and initialise it to contain the iterator
        // to the collection of the neighbors of the first node
        Iterator<node_info> nodeNeighbor = graphA.getV(node.getKey()).iterator();

        /*
        the loop go through all the nodes that are connected
        to the first node using the Bfs algorithm.
        create a queue and for each node that comes out from the queue
        subtract one from numOfNodesToGo. if equals 0 after finishing the loop,
        it went through all the nodes and the graph is connected
        */
        while (!help_queue.isEmpty()) {
            if (nodeNeighbor.hasNext()) {
                node = (node_info) nodeNeighbor.next();
                if (node.getTag() == -1)//this node hasn't been visited
                {
                    help_queue.addLast(node);
                    //change tag to 0- sign it's entered the queue already
                    node.setTag(0);
                }

            } else {
                help_queue.remove();
                numOfNodesToGo--;
                if (!help_queue.isEmpty())
                    //update the iterator to be the iterator of the neighbors of the first node currently in the queue
                    nodeNeighbor = graphA.getV(help_queue.peekFirst().getKey()).iterator();

            }
        }
        return numOfNodesToGo == 0;
    }


    /**
     * returns the length of the shortest path between src to dest,
     * implemented using Dijkstra's algorithm
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {

        Comparator<node_info> nodesDistanceComparator = new NodesComparator();
        //create priority queue of node_info which priorities according to
        // the tag variable which will contain the the distance from src
        PriorityQueue<node_info> helpQueue = new PriorityQueue<>(nodesDistanceComparator);
        //if the graph is empty
        if (this.graphA.nodeSize() == 0)
            return -1;
        node_info node = graphA.getNode(src);
        //if there the src node isn't in the graph
        if (node == null)
            return -1;
        //if the src node exist in the graph and is the same the dest node
        if (src == dest)
            return 0;

        //initialise tags to -1 , strings to "" for the algorithm,
        // //-1 Symbolizes infinity for the algorithm
        setTagsAndStrings();

        helpQueue.add(node);
        //change tag to 0: sign its distance 0 from src
        node.setTag(0);

        Iterator<node_info> neighborsIterator = graphA.getV(src).iterator();

        int keyCurrNode = src;
        node_info currNode = node;


        while (!helpQueue.isEmpty()) {
            if (neighborsIterator.hasNext()) {
                node = (node_info) neighborsIterator.next();
                int nodeKey = node.getKey();
                double weight = graphA.getEdge(nodeKey, keyCurrNode);
                //if this node hasn't been visited- like infinity in the algorithm
                if (node.getTag() == -1) {
                    node.setTag(currNode.getTag() + weight);
                    helpQueue.add(node);

                } else if (!node.getStringInfo().equals("visited"))//the node is already in the queue, and wasn't visited
                {
                    //if the new distance is smaller, update the tag
                    if (currNode.getTag() + weight < node.getTag())
                        node.setTag(currNode.getTag() + weight);


                }

            } else //finished going through the neighbors of the current first node in the queue
            {
                currNode.setStringInfo("visited");
                helpQueue.remove();
                if (!helpQueue.isEmpty()) {
                    currNode = helpQueue.peek();
                    keyCurrNode = currNode.getKey();
                    //got to the dest node-finish
                    if (keyCurrNode == dest)
                        return graphA.getNode(dest).getTag();

                    //update the iterator to neighbors of the first node in the queue
                    neighborsIterator = graphA.getV(keyCurrNode).iterator();
                }

            }
        }
        //if there is not connection between src node and dest dest.

        return -1;
    }

    /**
     * This method receives two boolean numbers and return whether the
     * two numbers are equal or not by checking
     * if the difference between them is smaller then epsilon
     *
     * @param d1
     * @param d2
     * @return
     */
    private boolean doubleEquals(double d1, double d2) {
        final double Eps = 0.0001;
        return (Math.abs(d1 - d2)) < Eps;
    }


    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * implemented using Dijkstra's algorithm
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {

        /*call to method shortestPathDist. the method return the distance and
        changes the tags in the graph to be the distance from src*/
        double dist = shortestPathDist(src, dest);
        /*if the function returned -1, it means there isn't a connection between src and dest*/
        if (Double.compare(dist, -1) == 0)
            return null;

        LinkedList<node_info> pathList = new LinkedList<node_info>();
        node_info currNode = graphA.getNode(dest);
        pathList.add(currNode);

        if (dist == 0) {
            return pathList;
        }

      /*loop that go through the graph backward, from dest to the src,
       every time finds the node that his distance from src is:
       the current node distance from src minus the weight of the edge connecting them,
       add him to the start of the list and look through this node neighbors and does the same, until reaching the src node;
       */

        Iterator<node_info> neighborsIterator = graphA.getV(dest).iterator();
        while (neighborsIterator.hasNext()) {
            node_info neighborNode = neighborsIterator.next();
            if (neighborNode.getTag() == 0)
                break;
            double edgeWeight = graphA.getEdge(currNode.getKey(), neighborNode.getKey());
            if (doubleEquals(neighborNode.getTag(), dist - edgeWeight)) {
                //add this node to the path
                pathList.addFirst(neighborNode);
                //this node becomes the currNode
                currNode = neighborNode;
                dist = currNode.getTag();
                neighborsIterator = graphA.getV(currNode.getKey()).iterator();
            }
        }
        pathList.addFirst(graphA.getNode(src));//add the first node to the list

        return pathList;
    }


    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        boolean ans = false;
        ObjectOutputStream oos;
        try {
            FileOutputStream fOut = new FileOutputStream(file, true);
            oos = new ObjectOutputStream(fOut);
            oos.writeObject(this.graphA);
            oos.close();
            ans = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans;

    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.graphA = (WGraph_DS) in.readObject();
            in.close();
            fileIn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

