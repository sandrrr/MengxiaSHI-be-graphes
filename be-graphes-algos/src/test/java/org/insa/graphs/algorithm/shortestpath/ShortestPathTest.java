package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Point;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public abstract class ShortestPathTest {
	
	private static Graph map1, map2, map3, map4;
    private static ArcInspector aiTime, aiLength;
    
    protected abstract ShortestPathAlgorithm createSolution(ShortestPathData data);
	
	@BeforeClass
	public static void initAll() throws IOException {
		aiTime = ArcInspectorFactory.getAllFilters().get(0);
		aiLength = ArcInspectorFactory.getAllFilters().get(2);
		
		String map1Path = "/Users/mshi/Desktop/3MIC2/MengxiaSHI-be-graphes/maps/carre.mapgr";
		String map2Path = "/Users/mshi/Desktop/3MIC2/MengxiaSHI-be-graphes/maps/carre-dense.mapgr";
		String map3Path = "/Users/mshi/Desktop/3MIC2/MengxiaSHI-be-graphes/maps/belgium.mapgr";
		String map4Path = "/Users/mshi/Desktop/3MIC2/MengxiaSHI-be-graphes/maps/denmark.mapgr";
		
		GraphReader reader1 = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map1Path))));
		GraphReader reader2 = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map2Path))));
		GraphReader reader3 = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map3Path))));
		GraphReader reader4 = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map4Path))));
		
		map1 = reader1.read();
		map2 = reader2.read();
		map3 = reader3.read();
		map4 = reader4.read();

		reader1.close();
		reader2.close();
		reader3.close();
		reader4.close();
	}
	
	@Test
	public void testValid() {
		ShortestPathSolution validPath = (createSolution(new ShortestPathData(map4, map4.get(709851), map4.get(1150792), aiTime))).doRun();
		ShortestPathSolution invalidPath = (createSolution(new ShortestPathData(map4, map4.get(709851), map4.get(386112), aiTime))).doRun();
		assertTrue(validPath.getPath().isValid());
		assertEquals(invalidPath.getStatus(), Status.INFEASIBLE);
	}
	
	@Test
	public void testOptimalityOracle() {
		for (int i = 0; i < 24; i++) {
			for (int j = i + 1; j < 25; j++) {
				System.out.println("start point: " + i + ", end point: " + j);
				ShortestPathSolution pathLengthShortestPath = (createSolution(new ShortestPathData(map1, map1.get(i), map1.get(j), aiLength))).doRun();
				ShortestPathSolution pathLengthBellmanFord = (new BellmanFordAlgorithm(new ShortestPathData(map1, map1.get(i), map1.get(j), aiLength))).doRun();
				ShortestPathSolution pathTimeShortestPath = (createSolution(new ShortestPathData(map1, map1.get(i), map1.get(j), aiTime))).doRun();
				ShortestPathSolution pathTimeBellmanFord = (new BellmanFordAlgorithm(new ShortestPathData(map1, map1.get(i), map1.get(j), aiTime))).doRun();
				assertEquals(pathLengthShortestPath.getPath().getLength(), pathLengthBellmanFord.getPath().getLength(), 4);
				assertEquals(pathTimeShortestPath.getPath().getMinimumTravelTime(), pathTimeBellmanFord.getPath().getMinimumTravelTime(), 1);
 			}
		}
		System.out.println("start point: 0, end point: 0");
		ShortestPathSolution pathLengthShortestPath = (createSolution(new ShortestPathData(map1, map1.get(0), map1.get(0), aiLength))).doRun();
		ShortestPathSolution pathTimeShortestPath = (createSolution(new ShortestPathData(map1, map1.get(0), map1.get(0), aiTime))).doRun();
		assertEquals(pathLengthShortestPath.getPath().getLength(), 0, 0);
		assertEquals(pathTimeShortestPath.getPath().getMinimumTravelTime(), 0, 0);
	}
	
	@Test
	public void testOptimalityEstimatedCost() {
		int i=0, inc=1000, j=inc, cmpt=0, max=10;
		double coutDistance, coutTemps, vitesse = map3.getGraphInformation().getMaximumSpeed();
		ShortestPathSolution pathLengthShortestPath, pathTimeShortestPath;
		while (cmpt < max) {
			pathLengthShortestPath = (createSolution(new ShortestPathData(map3, map3.get(i), map3.get(j), aiLength))).run();
			pathTimeShortestPath = (createSolution(new ShortestPathData(map3, map3.get(i), map3.get(j), aiTime))).run();
			coutDistance = Point.distance(map3.get(i).getPoint(), map3.get(j).getPoint());
			coutTemps = coutDistance / vitesse * 3.6;
			assertTrue(pathLengthShortestPath.getPath().getLength() >= coutDistance);
			assertTrue(pathTimeShortestPath.getPath().getMinimumTravelTime() >= coutTemps);
			cmpt++;
			j += inc;
		}
	}
	
	@Test
	public void testOptimalitySubPath() {
		int i=0, inc=1000, j=inc, cmpt=0, max=5;
		ShortestPathSolution pathLengthShortestPath, pathTimeShortestPath, pathLengthShortestSubPath, pathTimeShortestSubPath;
		while (cmpt < max) {
			pathLengthShortestPath = (createSolution(new ShortestPathData(map3, map3.get(i), map3.get(j), aiLength))).run();
			pathTimeShortestPath = (createSolution(new ShortestPathData(map3, map3.get(i), map3.get(j), aiTime))).run();
			pathLengthShortestSubPath = (createSolution(new ShortestPathData(map3, map3.get(pathLengthShortestPath.getPath().getArcs().get(10).getDestination().getId()), map3.get(j), aiLength))).run();
			pathTimeShortestSubPath = (createSolution(new ShortestPathData(map3, map3.get(pathTimeShortestPath.getPath().getArcs().get(10).getDestination().getId()), map3.get(j), aiTime))).run();
			for (Arc arc : pathLengthShortestSubPath.getPath().getArcs()) {
				assertTrue(pathLengthShortestPath.getPath().getArcs().contains(arc));
			}
			for (Arc arc : pathTimeShortestSubPath.getPath().getArcs()) {
				assertTrue(pathTimeShortestPath.getPath().getArcs().contains(arc));
			}
			cmpt++;
			j += inc;
		}
	}
	
	@Test
	public void testPerformance() throws IOException {
		int i=0, inc=3000, j=inc, nbTest=map2.size()/inc-1;
		ShortestPathSolution pathLengthShortestPath, pathTimeShortestPath;
		BufferedWriter[] bw = {
			new BufferedWriter(new FileWriter("carre-dense_distance_" + nbTest + "_" + this.getClass().getSimpleName().replace("Test", "") + "_data.txt")),
			new BufferedWriter(new FileWriter("carre-dense_temps_" + nbTest + "_" + this.getClass().getSimpleName().replace("Test", "") + "_data.txt"))
		};
		bw[0].write("carre-dense" + "\n" + 0 + "\n" + nbTest + "\n");
		bw[1].write("carre-dense" + "\n" + 1 + "\n" + nbTest + "\n");
		System.out.println("Nombre de tests : " + nbTest);
		while (j < map2.size()) {
			pathLengthShortestPath = (createSolution(new ShortestPathData(map2, map2.get(i), map2.get(j), aiLength))).run();
			pathTimeShortestPath = (createSolution(new ShortestPathData(map2, map2.get(i), map2.get(j), aiTime))).run();
		    bw[0].write(i + 
		    		"\t" + j + 
		    		"\t" + Point.distance(map2.get(i).getPoint(), map2.get(j).getPoint()) +
		    		"\t" + pathLengthShortestPath.getSolvingTime().toNanos() +
		    		"\t" + pathLengthShortestPath.getNbArcs() + 
		    		"\t" + pathLengthShortestPath.getNbExplores() +
		    		"\t" + pathLengthShortestPath.getNbMarques() +
		    		"\t" + pathLengthShortestPath.getMaxTas() + "\n");
		    bw[1].write(i + 
		    		"\t" + j + 
		    		"\t" + Point.distance(map2.get(i).getPoint(), map2.get(j).getPoint()) +
		    		"\t" + pathTimeShortestPath.getSolvingTime().toNanos() +
		    		"\t" + pathTimeShortestPath.getNbArcs() + 
		    		"\t" + pathTimeShortestPath.getNbExplores() +
		    		"\t" + pathTimeShortestPath.getNbMarques() +
		    		"\t" + pathTimeShortestPath.getMaxTas() + "\n");
			System.out.println(j/inc + "/" + nbTest);
			j += inc;
		}
		bw[0].close();
		bw[1].close();
	}
	
	
}
