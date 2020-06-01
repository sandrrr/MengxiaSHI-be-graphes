package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Path;
import org.insa.graphs.algorithm.AbstractSolution;

public class ShortestPathSolution extends AbstractSolution {

    // Optimal solution.
    private final Path path;
    private int nbArcs, nbExplores, nbMarques, maxTas;
    /**
     * Create a new infeasible shortest-path solution for the given input and
     * status.
     * 
     * @param data Original input data for this solution.
     * @param status Status of the solution (UNKNOWN / INFEASIBLE).
     */
    public ShortestPathSolution(ShortestPathData data, Status status) {
        super(data, status);
        this.path = null;
    }

    /**
     * Create a new shortest-path solution.
     * 
     * @param data Original input data for this solution.
     * @param status Status of the solution (FEASIBLE / OPTIMAL).
     * @param path Path corresponding to the solution.
     */
    public ShortestPathSolution(ShortestPathData data, Status status, Path path) {
        super(data, status);
        this.path = path;
    }
    
    public ShortestPathSolution(ShortestPathData data, Status status, Path path, int nbArcs, int nbExplores, int nbMarques, int maxTas) {
        super(data, status);
        this.path = path;
        this.nbArcs = nbArcs;
        this.nbExplores = nbExplores;
        this.nbMarques = nbMarques;
        this.maxTas = maxTas;
    }

    @Override
    public ShortestPathData getInputData() {
        return (ShortestPathData) super.getInputData();
    }

    /**
     * @return The path of this solution, if any.
     */
    public Path getPath() {
        return path;
    }
    
    public int getNbArcs() {
        return nbArcs;
    }
    
    public int getNbExplores() {
        return nbExplores;
    }
    
    public int getNbMarques() {
        return nbMarques;
    }
    
    public int getMaxTas() {
        return maxTas;
    }

    @Override
    public String toString() {
        String info = null;
        if (!isFeasible()) {
            info = String.format("No path found from node #%d to node #%d",
                    getInputData().getOrigin().getId(), getInputData().getDestination().getId());
        }
        else {
            double cost = 0;
            for (Arc arc: getPath().getArcs()) {
                cost += getInputData().getCost(arc);
            }
            info = String.format("Found a path from node #%d to node #%d",
                    getInputData().getOrigin().getId(), getInputData().getDestination().getId());
            if (getInputData().getMode() == Mode.LENGTH) {
                info = String.format("%s, %.4f kilometers", info, cost / 1000.0);
            }
            else {
                info = String.format("%s, %.4f minutes", info, cost / 60.0);
            }
        }
        info += " in " + getSolvingTime().getSeconds() + " seconds.";
        return info;
    }

}
