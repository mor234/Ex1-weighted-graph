package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WGraph_DSTest {


    @Test
    void getNode() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        assertNull(g.getNode(5), "suppose to return null if the node doesnt exist in the graph");
        assertEquals(g.getNode(2).getKey(), 2, "didn't get the right node");

    }

    @Test
    void hasEdge() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.connect(2, 4, 5.2);
        g.connect(0, 1, 3.5);
        g.connect(2, 1, -1);//not supposed to be created, negative weight
        g.connect(4, 3, 2);

        assertEquals(g.getEdge(0, 1), g.getEdge(1, 0), "the edge suppose to be the same value from both sides");
        assertTrue(g.hasEdge(2, 4), "don't have edge");
        //different order not supposed to matter
        assertTrue(g.hasEdge(4, 2), "don't have edge if look for in different order");
        //was not created
        assertFalse(g.hasEdge(1, 5), "has edge when wasn't created");
        assertFalse(g.hasEdge(2, 1), "created edge with negative weight");
        assertFalse(g.hasEdge(2, 2), "has edge to itself");
        assertFalse(g.hasEdge(8, 6), "has edge to not existing nodes");
        g.removeEdge(2, 4);
        assertFalse(g.hasEdge(2, 4), "has edge after deleted");


    }

    @Test
    void connect() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.connect(0, 1, 3.5);
        assertEquals(g.getEdge(0, 1), 3.5, "wrong weight");
        g.connect(0, 1, 2);//supposed to update the weight
        assertEquals(g.getEdge(0, 1), 2, "didn't update weight");
        assertEquals(g.getEdge(0, 2), -1, "the edge doesn't exist and didn't return -1");
        g.addNode(2);
        g.connect(2, 1, -1);//not supposed to be created, negative weight
        assertEquals(g.getEdge(2, 1), -1, "the edge not supposed to be created and didn't return -1");
        assertEquals(g.getEdge(3, 7), -1, "the nodes don't exist and didn't return -1");

    }

    @Test
    void addNode() {
        weighted_graph g = new WGraph_DS();

        g.addNode(0);
        g.addNode(1);
        g.addNode(3);
        g.addNode(4);
        g.addNode(1);
        g.addNode(4);
        g.addNode(-5);

        assertEquals(g.nodeSize(), 5, "not added nodes correctly");
        assertEquals(g.getNode(1).getKey(), 1, "the node was created with the wrong key");

    }


    @Test
    void getV() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.removeNode(4);

        boolean node0 = false, node1 = false, node2 = false, node3 = false, node4 = false;
        Collection<node_info> v = g.getV();
        Iterator<node_info> iterator = v.iterator();
        int size = 0;
        while (iterator.hasNext()) {
            node_info tmp = iterator.next();

            if (tmp.getKey() == 0)
                node0 = true;
            if (tmp.getKey() == 1)
                node1 = true;
            if (tmp.getKey() == 2)
                node2 = true;
            if (tmp.getKey() == 3)
                node3 = true;
            if (tmp.getKey() == 4)
                node4 = true;
            size++;


        }
        assertTrue(node0 && node1 && node2 && node3, "not all nodes exist in the collection");
        assertEquals(size, 4, "the size of the collection is wrong");
        assertFalse(node4, "a node wasn't deleted from the collection");


    }

    @Test
    void getVNeighbors() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);

        g.connect(0, 1, 1.5);
        g.connect(0, 2, 0.7);
        g.connect(0, 3, 7);
        g.connect(0, 4, 7);
        g.removeEdge(0, 4);

        //check which are the neighbors of 0
        boolean node0 = false, node1 = false, node2 = false, node3 = false, node4 = false;
        Collection<node_info> v = g.getV(0);
        Iterator<node_info> iterator = v.iterator();
        int size = 0;

        while (iterator.hasNext()) {
            node_info tmp = iterator.next();

            if (tmp.getKey() == 0)
                node0 = true;
            if (tmp.getKey() == 1)
                node1 = true;
            if (tmp.getKey() == 2)
                node2 = true;
            if (tmp.getKey() == 3)
                node3 = true;
            if (tmp.getKey() == 4)
                node4 = true;
            size++;


        }
        assertTrue(node1 && node2 && node3, "not all nodes exist in the collection of neighbors");
        assertEquals(size, 3, "the size of the collection of neighbors is wrong");
        assertFalse(node4, "a node wasn't deleted from the collection of neighbors");
        assertFalse(node0, "a node wasn't deleted from the collection");

        //check 0 is also a neighbor of 1

        v = g.getV(1);
        iterator = v.iterator();
        assertEquals(v.size(), 1, "not added neighbor");
        assertEquals(iterator.next().getKey(), 0, "not added the right neighbor");
    }

    @Test
    void removeNode() {
        weighted_graph g = new WGraph_DS();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.connect(1, 2, 1.5);
        g.connect(1, 3, 6);
        g.connect(4, 3, 2);
        g.connect(2, 3, 4.2);

        assertEquals(g.nodeSize(), 4, " the node size is before delete is wrong");


        node_info node = g.removeNode(1);


        assertEquals(node.getKey(), 1, "didn't return the deleted node");
        assertNull(g.getNode(1), "if not null, didn't delete");
        assertEquals(g.nodeSize(), 3, " the node size is wrong after delete");
        assertEquals(g.edgeSize(), 2, "didn't update edge size after delete");
        assertFalse(g.hasEdge(2, 1), "didn't delete edge");

        //delete a non existing node
        node = g.removeNode(1);
        assertNull(node, "deleted a non existing node, suppose to return null");

        assertEquals(g.nodeSize(), 3, " the node not suppose to change, deleted a non existing node");
        assertEquals(g.edgeSize(), 2, "the edge size not suppose to change, deleted a non existing node");

    }

    @Test
    void removeEdge() {
        weighted_graph g = new WGraph_DS();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(1, 2, 1.5);
        g.connect(1, 3, 6);

        g.removeEdge(1, 2);
        assertEquals(g.edgeSize(), 1, "not updated num of edges");
        assertFalse(g.hasEdge(1, 2), "didn't delete edge 1-2");
        assertFalse(g.hasEdge(2, 1), "didn't delete edge from the other direction (2-1)");
        assertEquals(g.getEdge(1, 2), -1, "after removing return -1 when tries to get it");


        g.removeEdge(1, 2);
        assertEquals(g.edgeSize(), 1, "num of edges not suppose to change, delete a deleted edge ");
        assertEquals(g.nodeSize(), 3, "nodeSize not suppose to change when deleting edges");


    }

    @Test
    void edgeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(0, 1, 1);
        g.connect(0, 2, 2);
        g.connect(1, 2, 2.7);
        g.connect(1, 1, 1);//not suppose to connect
        g.connect(0, 2, 7);//num of edges not suppose to change
        System.out.println(g);

        assertEquals(g.edgeSize(), 3, "didn't count edges correctly ");

    }

    @Test
    void getMC() {
        weighted_graph g = graph_creator(10, 30, 2);
        int mcAfterCreating = g.getMC();
        g.connect(7, 8, 2.5);
        int mcConnectEdge = g.getMC();
        g.removeNode(1);
        int mcRemoveNode = g.getMC();
        g.removeEdge(7, 8);
        int mcRemoveEdge = g.getMC();

        assertTrue(mcAfterCreating < mcConnectEdge, "not updated mc after connecting edge");
        assertTrue(mcConnectEdge < mcRemoveNode, "not updated mc after removing node");
        assertTrue(mcRemoveNode < mcRemoveEdge, "not updated mc after removing edge");
        g.removeNode(1);
        g.removeEdge(7, 8);
        g.getNode(3);
        g.edgeSize();
        assertEquals(mcRemoveEdge, g.getMC(), "mc suppose to remain the same, the graph hasn't changed");

    }

    @Test
    void graph_creatorTest() {
        weighted_graph g = graph_creator(10, 30, 2);
        // System.out.println(g.toString());
        assertEquals(g.nodeSize(), 10, "not enough nodes were created");
        assertEquals(g.edgeSize(), 30, "not enough edges were created");

    }

    ///////////////////////////////////
    /**
     * Generate a random graph with v_size nodes and e_size edges -givan as part of example test
     *
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */
    private static Random _rnd = null;

    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(seed);
        for (int i = 0; i < v_size; i++) {
            g.addNode(i);
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(g);
        while (g.edgeSize() < e_size) {
            int a = nextRnd(0, v_size);
            int b = nextRnd(0, v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i, j, w);
        }
        return g;
    }

    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0 + min, (double) max);
        int ans = (int) v;
        return ans;
    }

    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max - min;
        double ans = d * dx + min;
        return ans;
    }

    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     *
     * @param g
     * @return
     */
    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for (int i = 0; i < size; i++) {
            ans[i] = nodes[i].getKey();
        }
        Arrays.sort(ans);
        return ans;
    }

}