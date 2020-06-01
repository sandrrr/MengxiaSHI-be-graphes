package org.insa.graphs.algorithm.packageswitch;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

import java.util.ArrayList;

public class PackageSwitchData extends AbstractInputData {

    // Origin and destination nodes.
    private final ArrayList<Node> nodes = new ArrayList<>();

    public PackageSwitchData(Graph graph, Node origin1, Node origin2, Node destination1, Node destination2, ArcInspector arcFilter) {
        super(graph, arcFilter);
        nodes.add(origin1);
        nodes.add(origin2);
        nodes.add(destination1);
        nodes.add(destination2);
    }

    public Node getNode(int index) { return nodes.get(index); }

    /**
     * @return Origin node for the path.
     */
    public Node getOrigin1() {
        return nodes.get(0);
    }

    /**
     * @return Origin node for the path.
     */
    public Node getOrigin2() {
        return nodes.get(1);
    }

    /**
     * @return Destination node for the path.
     */
    public Node getDestination1() {
        return nodes.get(2);
    }

    /**
     * @return Destination node for the path.
     */
    public Node getDestination2() {
        return nodes.get(3);
    }
}
