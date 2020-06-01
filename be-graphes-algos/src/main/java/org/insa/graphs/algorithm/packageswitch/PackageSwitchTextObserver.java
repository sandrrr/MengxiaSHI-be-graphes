package org.insa.graphs.algorithm.packageswitch;

import org.insa.graphs.model.Node;

import java.io.PrintStream;

public class PackageSwitchTextObserver implements PackageSwitchObserver {

    private final PrintStream stream;

    public PackageSwitchTextObserver(PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public void notifyOriginProcessed(Node node) {
        // TODO Auto-generated method stub
    }

    @Override
    public void notifyNodeReached(Node node, int mapIndex) {
        stream.println("Node " + node.getId() + " reached.");
    }

    @Override
    public void notifyDestinationReached(Node node) {
        // TODO Auto-generated method stub
    }
}
