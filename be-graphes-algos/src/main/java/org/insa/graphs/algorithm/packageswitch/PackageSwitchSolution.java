package org.insa.graphs.algorithm.packageswitch;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class PackageSwitchSolution extends AbstractSolution {

    // Optimal solution.
    private final Path path1;
    private final Path path2;
    private final Node meetingPoint;

    protected PackageSwitchSolution(PackageSwitchData data, Status status) {
        super(data, status);
        this.path1 = null;
        this.path2 = null;
        this.meetingPoint = null;
    }

    protected PackageSwitchSolution(PackageSwitchData data, Status status, Path path1, Path path2, Node meetingPoint) {
        super(data, status);
        this.path1 = path1;
        this.path2 = path2;
        this.meetingPoint = meetingPoint;
    }

    /**
     * @return The path of this solution, if any.
     */
    public Path getPath1() {
        return path1;
    }

    /**
     * @return The path of this solution, if any.
     */
    public Path getPath2() {
        return path2;
    }

    /**
     * @return The path of this solution, if any.
     */
    public Node getMeetingPoint() {
        return meetingPoint;
    }

}
