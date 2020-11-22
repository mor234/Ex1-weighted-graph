package ex1.src;

import java.io.Serializable;
import java.util.Comparator;

/**
 * this class represent a comparator
 * which compare between 2 node_info according to their tags,
 * to be used in class WGraph_Algo (algorithms on weighted graphs)
 */

public class NodesComparator implements Comparator<node_info> {
    /**
     * compare according to the tag
     **/

    @Override
    public int compare(node_info n1, node_info n2) {
        return Double.compare(n1.getTag(), n2.getTag());
    }
}
