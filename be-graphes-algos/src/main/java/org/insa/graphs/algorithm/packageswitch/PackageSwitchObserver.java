package org.insa.graphs.algorithm.packageswitch;

import org.insa.graphs.model.Node;

public interface PackageSwitchObserver {

    /**
     * Notify the observer that the origin has been processed.
     *
     * @param node Origin.
     */
    public void notifyOriginProcessed(Node node);

    /**
     * Notify the observer that a node has been reached for the first
     * time.
     *
     * @param node Node that has been reached.
     */
    public void notifyNodeReached(Node node, int mapIndex);

    /**
     * Notify the observer that the destination has been reached.
     *
     * @param node Destination.
     */
    public void notifyDestinationReached(Node node);

}
