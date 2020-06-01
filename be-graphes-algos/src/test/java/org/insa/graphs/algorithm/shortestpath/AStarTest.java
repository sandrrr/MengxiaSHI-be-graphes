package org.insa.graphs.algorithm.shortestpath;

public class AStarTest extends ShortestPathTest {
	
	protected ShortestPathAlgorithm createSolution(ShortestPathData data) {
		return new AStarAlgorithm(data);
	}
	
}
