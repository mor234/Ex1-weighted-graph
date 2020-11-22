package ex1.tests;


import ex1.src.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {
    static weighted_graph_algorithms g;

    /**
     * initialize the static variable to be the small graph to do the tests on
     */
    @BeforeAll
    static void initializeGraph() {
        g = new WGraph_Algo();
        g.init(smallGraph());
    }

    /**
     * create weighted_graph for tests
     *
     * @return the tests graph with node 4 disconnected from the rest of the nodes
     */

    weighted_graph notConnectedGraph0_4() {
        weighted_graph g1 = smallGraph();
        g1.removeEdge(3, 4);
        g1.removeEdge(5, 4);
        return g1;
    }

    /**
     * create weighted_graph for tests
     *
     * @return a graph to do the testes on
     */
    static weighted_graph smallGraph() {
        weighted_graph sG = new WGraph_DS();
        //create node 0-8
        for (int i = 0; i <= 8; i++) {
            sG.addNode(i);
        }
        sG.connect(0, 1, 4);
        sG.connect(0, 7, 8);

        sG.connect(1, 2, 8);
        sG.connect(1, 7, 11);

        sG.connect(7, 8, 7);
        sG.connect(7, 6, 1);

        sG.connect(6, 5, 2);
        sG.connect(6, 8, 6);

        sG.connect(8, 2, 2);

        sG.connect(2, 5, 4);
        sG.connect(2, 3, 7);

        sG.connect(5, 3, 14);

        sG.connect(3, 4, 9);
        sG.connect(4, 5, 10);
//         System.out.println(sG);
        /*
nodes: 9 edges: 14
 node id: 0
 the 2 neighbors are: id: 1 w: 4.0 id: 7 w: 8.0
 node id: 1
 the 3 neighbors are: id: 0 w: 4.0 id: 2 w: 8.0 id: 7 w: 11.0
 node id: 2
 the 4 neighbors are: id: 1 w: 8.0 id: 3 w: 7.0 id: 5 w: 4.0 id: 8 w: 2.0
 node id: 3
 the 3 neighbors are: id: 2 w: 7.0 id: 4 w: 9.0 id: 5 w: 14.0
 node id: 4
 the 2 neighbors are: id: 3 w: 9.0 id: 5 w: 10.0
 node id: 5
 the 4 neighbors are: id: 2 w: 4.0 id: 3 w: 14.0 id: 4 w: 10.0 id: 6 w: 2.0
 node id: 6
 the 3 neighbors are: id: 5 w: 2.0 id: 7 w: 1.0 id: 8 w: 6.0
 node id: 7
 the 4 neighbors are: id: 0 w: 8.0 id: 1 w: 11.0 id: 6 w: 1.0 id: 8 w: 7.0
 node id: 8
 the 3 neighbors are: id: 2 w: 2.0 id: 6 w: 6.0 id: 7 w: 7.0
         */


        return sG;

    }


    @Test
    void init() {
        weighted_graph_algorithms g1 = new WGraph_Algo();
        weighted_graph wg = WGraph_DSTest.graph_creator(10, 30, 2);
        g1.init(wg);
        assertEquals(wg, g1.getGraph(), "equals not working, not working before before change in graph");
        wg.removeNode(1);
        assertNull(g1.getGraph().getNode(1), "return a node after removed");
        assertEquals(wg, g1.getGraph(), "not both graphs changed at the same. time not a shallow copy");
    }


    @Test
    void copy() {
        weighted_graph_algorithms g1 = new WGraph_Algo();
        weighted_graph_algorithms g2 = new WGraph_Algo();
        weighted_graph wg = WGraph_DSTest.graph_creator(10, 30, 2);
        g1.init(wg);
        g2.init(g1.copy());//g2 suppose to contain a deep copy of the graph of g1
        assertEquals(g2.getGraph(), g1.getGraph(), "equals not working, not working before before change in graph");
        g1.getGraph().removeNode(1);
        assertNotEquals(g2.getGraph(), g1.getGraph(), "only one graph suppose to change- not suppose to be equal!");

    }

    @Test
    void isConnected() {
        assertTrue(g.isConnected(), "the graph suppose to be connected");
        weighted_graph_algorithms g1 = new WGraph_Algo();
        weighted_graph tmpG = notConnectedGraph0_4();
        g1.init(tmpG);
        assertFalse(g1.isConnected(), "the graph is not suppose to be connected");
        g1.init(new WGraph_DS());//init with empty graph
        assertTrue(g1.isConnected(), "Empty graph suppose to counts as connected");


    }

    @Test
        //check on general example graph
    void shortestPathDist1() {

        assertEquals(g.shortestPathDist(0, 4), 21);
        assertEquals(g.shortestPathDist(0, 8), 14);
        assertEquals(g.shortestPathDist(0, 2), 12);
        assertEquals(g.shortestPathDist(0, 5), 11);
    }

    @Test
        //check on a very simple graph that return the shortest path, and not the first arrived solution
    void shortestPathDist2() {
        weighted_graph_algorithms gA = new WGraph_Algo();
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0, 1, 5);
        g.connect(0, 3, 1);
        g.connect(1, 2, 1);
        g.connect(3, 2, 10);
        gA.init(g);
        assertEquals(gA.shortestPathDist(0, 2), 6, "not works when the first arrived solution is not the smallest one");


    }

    //check on general example graph, when the node to find distance to is not connected
    @Test
    void shortestPathDist3() {
        weighted_graph_algorithms g1 = new WGraph_Algo();
        weighted_graph tmpG = notConnectedGraph0_4();
        g1.init(tmpG);
        assertEquals(g1.shortestPathDist(0, 4), -1, "the nodes are not connected. suppose to return -1");

    }

    //check on general example graph the distance to itself
    @Test
    void shortestPathDist4() {
        assertEquals(g.shortestPathDist(2, 2), 0, "the distance to itself suppose to be 0");
    }

    @Test
        //check on a general graph that returned the right path
    void shortestPath1() {

        Collection<node_info> path = g.shortestPath(0, 4);
        Iterator<node_info> iterator = path.iterator();
        assertEquals(iterator.next().getKey(), 0, "didn't give the correct first node in the path");
        assertEquals(iterator.next().getKey(), 7, "didn't give the correct second node in the path");
        assertEquals(iterator.next().getKey(), 6, "didn't give the correct third node in the path");
        assertEquals(iterator.next().getKey(), 5, "didn't give the correct forth node in the path");
        assertEquals(iterator.next().getKey(), 4, "didn't give the correct fifth node in the path");
        assertEquals(path.size(), 5, "the size of the path isn't right");

    }

    @Test
        //check return the right path to itself
    void shortestPath2() {

        Collection<node_info> path = g.shortestPath(0, 0);
        Iterator<node_info> iterator = path.iterator();
        assertEquals(iterator.next().getKey(), 0, "didn't give the correct first node in the path");
        assertEquals(path.size(), 1, "the size of the path isn't right");

    }

    @Test
        //check return empty collection when path doesn't exist
    void shortestPath3() {

        weighted_graph_algorithms g1 = new WGraph_Algo();
        weighted_graph tmpG = notConnectedGraph0_4();
        g1.init(tmpG);
        assertNull(g1.shortestPath(0, 4), "didn't return null when weren't connected");

    }

    @Test
        //copied from example test, with a few changes
    void save_load() {
        weighted_graph g0 = WGraph_DSTest.graph_creator(10, 30, 1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        String str = "g0.obj";

        if (!ag0.save(str))
            fail();
        weighted_graph g1 = WGraph_DSTest.graph_creator(10, 30, 1);
        assertEquals(ag0.getGraph(), g1, "equals doesn't return true for 2 equal graphs ");
        g0.removeNode(0);
        assertNotEquals(ag0.getGraph(), g1, "equals doesn't return false for graphs after change in one of them ");
        if (!ag0.load(str))
            fail();
        assertEquals(ag0.getGraph(), g1, "didn't load the old graph correctly");
    }

    @Test
    void runTimeTest1() {
        long start = new Date().getTime();
        WGraph_DSTest.graph_creator(1000000, 1000000, 1);
        long end = new Date().getTime();
        double dt = (end - start) / 1000.0;
       //System.out.println(dt);
        assertTrue(dt <5, "the build takes " + dt + " seconds, longer than 5 seconds");

    }


}

