package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	private boolean debug = false;

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        //retrieve the graph
        Graph graph = data.getGraph();
        // case origin = destination
        if (data.getDestination().getId() == data.getOrigin().getId()) {
        	notifyDestinationReached(data.getDestination());
        	return new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, data.getDestination()));
        }
        //initialize
        HashMap<Node, Label> map = new HashMap<>();
        for(Node n : graph.getNodes()) {
        	map.put(n, createLabel(n, data)); //ajoute label dans le map
        }
        BinaryHeap<Label> heap = new BinaryHeap<>();
        Label x = map.get(data.getOrigin()); //recupere 1er label
        notifyOriginProcessed(data.getOrigin());
        x.setCost(0);
        heap.insert(x);
        boolean findDestination = false;
      //iteration
        while(!findDestination && heap.size() > 0) {
        	x = heap.deleteMin();
        	x.setMark(true);
        	for(Arc arc : x.getSommet().getSuccessors()) {
        		if (data.isAllowed(arc)) {
        			Label y = map.get(arc.getDestination());
            		if (!y.getMark()) {
            			double cost = x.getCost() + data.getCost(arc);
            			if (y.getCost() > cost) {
            				y.setCost(cost);
            				y.setFather(arc);
            				int index = heap.indexOf(y);
            				if (index < 0) {
                				notifyNodeReached(y.getSommet());
            					if (y.getSommet() == data.getDestination()) {
            						findDestination = true;
            					}
            					heap.insert(y);
            				} else {
            					heap.update(index);
            				}
            			}
            		}
        		}
        	}
        	//print debug
        	if (debug) {
            	System.out.println("le coût label marqué : " + x.getCost());
            	System.out.println("le nb de successeurs testés : " + x.getSommet().getSuccessors().size());
        	}
        }
        
        ShortestPathSolution solution = null;
        // Destination has no predecessor, the solution is infeasible...
        if (map.get(data.getDestination()).getCost() == Double.POSITIVE_INFINITY) {
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		} else {
            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());
            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
			Arc arc = map.get(data.getDestination()).getFather();
			while (arc != null) {
				arcs.add(arc);
				arc = map.get(arc.getOrigin()).getFather();
			}
            // Reverse the path...
            Collections.reverse(arcs);
            // Create the final solution.
            Path path = new Path(graph, arcs);
            solution = new ShortestPathSolution(data, Status.OPTIMAL, path);
            // Verification final solution
            if (debug) {
                System.out.println(path.isValid());
                System.out.println(path.getLength());
                System.out.println(map.get(data.getDestination()).getCost());
            }
        }
        return solution;
    }
    
    protected Label createLabel(Node n, ShortestPathData data) {
    	return new Label(n);
    }
}
