package org.insa.graphs.algorithm.packageswitch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class MeetingAlgorithm extends PackageSwitchAlgorithm {
    public MeetingAlgorithm(PackageSwitchData data) {
        super(data);
    }

    @Override
    protected PackageSwitchSolution doRun() {
        final PackageSwitchData data = getInputData();
        //retrieve the graph
        Graph graph = data.getGraph();
        //initialize
        ArrayList<HashMap<Node, LabelMeeting>> maps = new ArrayList<>();
        BinaryHeap<LabelMeeting> heap = new BinaryHeap<>();
        LabelMeeting x = null;
        for (int i = 0; i < 4; i++) {
            maps.add(new HashMap<>());
            for(Node n : graph.getNodes()) {
                maps.get(i).put(n, new LabelMeeting(n, i)); //ajoute label dans le map
            }
            x = maps.get(i).get(data.getNode(i)); //recupere 1er label
            notifyOriginProcessed(data.getNode(i));
            x.setCost(0);
            heap.insert(x);
        }
        boolean findDestination = false;
        //iteration
        while(!findDestination && heap.size() > 0) {
            x = heap.deleteMin();
            x.setMark(true);
            findDestination = true;
            for (int i = 0; i < 4; i++) {
                if (i != x.getMapIndex() && !maps.get(i).get(x.getSommet()).getMark()) {
                    findDestination = false;
                    break;
                }
            }
            if (findDestination) continue;
            for(Arc arc : x.getSommet().getSuccessors()) {
                if (data.isAllowed(arc)) {
                    LabelMeeting y = maps.get(x.getMapIndex()).get(arc.getDestination());
                    if (!y.getMark()) {
                        double cost = x.getCost() + data.getCost(arc);
                        if (y.getCost() > cost) {
                            y.setCost(cost);
                            y.setFather(arc);
                            int index = heap.indexOf(y);
                            if (index < 0) {
                                notifyNodeReached(y.getSommet(), y.getMapIndex());
                                heap.insert(y);
                            } else {
                                heap.update(index);
                            }
                        }
                    }
                }
            }
        }

        PackageSwitchSolution solution = null;
        Node meetingPoint = x.getSommet();
        // Destination has no predecessor, the solution is infeasible...
        if (!findDestination) {
            solution = new PackageSwitchSolution(data, Status.INFEASIBLE);
        } else {
            // The destination has been found, notify the observers.
            notifyDestinationReached(meetingPoint);
            // Create the path from the array of predecessors...
            ArrayList<ArrayList<Arc>> arcs = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                arcs.add(new ArrayList<>());
                Arc arc = maps.get(i).get(meetingPoint).getFather();
                while (arc != null) {
                    arcs.get(i).add(arc);
                    arc = maps.get(i).get(arc.getOrigin()).getFather();
                }
            }
            // Reverse the path...
            Collections.reverse(arcs.get(0));
            Collections.reverse(arcs.get(1));
            arcs.get(0).addAll(arcs.get(2));
            arcs.get(1).addAll(arcs.get(3));
            // Create the final solution.
            Path path1 = new Path(graph, arcs.get(0));
            Path path2 = new Path(graph, arcs.get(1));
            solution = new PackageSwitchSolution(data, Status.OPTIMAL, path1, path2, meetingPoint);
        }
        return solution;
    }
}
