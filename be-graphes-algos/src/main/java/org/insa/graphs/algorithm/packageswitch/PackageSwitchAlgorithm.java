package org.insa.graphs.algorithm.packageswitch;

import org.insa.graphs.algorithm.AbstractAlgorithm;
import org.insa.graphs.model.Node;

public abstract class PackageSwitchAlgorithm extends AbstractAlgorithm<PackageSwitchObserver> {

    /**
     * Create a new PackageSwitchAlgorithm with the given data.
     * 
     * @param data
     */
    protected PackageSwitchAlgorithm(PackageSwitchData data) {
        super(data);
    }

    @Override
    public PackageSwitchSolution run() {
        return (PackageSwitchSolution) super.run();
    }

    @Override
    protected abstract PackageSwitchSolution doRun();

    @Override
    public PackageSwitchData getInputData() {
        return (PackageSwitchData) super.getInputData();
    }

    /**
     * Notify all observers that the origin has been processed.
     *
     * @param node Origin.
     */
    public void notifyOriginProcessed(Node node) {
        for (PackageSwitchObserver obs: getObservers()) {
            obs.notifyOriginProcessed(node);
        }
    }

    /**
     * Notify all observers that a node has been reached for the first time.
     *
     * @param node Node that has been reached.
     */
    public void notifyNodeReached(Node node, int mapIndex) {
        for (PackageSwitchObserver obs: getObservers()) {
            obs.notifyNodeReached(node, mapIndex);
        }
    }

    /**
     * Notify all observers that the destination has been reached.
     *
     * @param node Destination.
     */
    public void notifyDestinationReached(Node node) {
        for (PackageSwitchObserver obs: getObservers()) {
            obs.notifyDestinationReached(node);
        }
    }
}
