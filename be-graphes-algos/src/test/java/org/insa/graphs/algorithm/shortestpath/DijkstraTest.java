package org.insa.graphs.algorithm.shortestpath;

public class DijkstraTest extends ShortestPathTest {
	
	protected ShortestPathAlgorithm createSolution(ShortestPathData data) {
		return new DijkstraAlgorithm(data);
	}
	
}
