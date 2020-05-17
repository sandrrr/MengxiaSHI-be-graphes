package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

public class LabelStar extends Label {
	
	private double coutStar;
	
	public LabelStar (Node sommet, ShortestPathData data) {
		super(sommet);
		if (data.getMode() == AbstractInputData.Mode.LENGTH) {
			this.coutStar = Point.distance(sommet.getPoint(), data.getDestination().getPoint());
		} else {
			int vitesse = data.getMaximumSpeed();
			this.coutStar = Point.distance(sommet.getPoint(), data.getDestination().getPoint()) / vitesse * 3.6;
		}
	}
	
	public double getTotalCost() {
		return this.coutStar + this.cout;
	}

	@Override
	public int compareTo(Label autre) {
		LabelStar autreStar = (LabelStar) autre;
		if (this.getTotalCost() < autreStar.getTotalCost()) {
			return -1;
		} else if (this.getTotalCost() > autreStar.getTotalCost()) {
			return 1;
		} else {
			return 0;
		}
	}
}
