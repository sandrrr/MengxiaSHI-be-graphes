package org.insa.graphs.algorithm.packageswitch;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class LabelMeeting implements Comparable<LabelMeeting> {

	private Node sommet;
	private int mapIndex;
	private boolean marque;
	protected double cout;
	private Arc predecesseur;

	public LabelMeeting(Node sommet, int mapIndex) {
		this.sommet = sommet;
		this.mapIndex = mapIndex;
		this.marque = false;
		this.cout = Double.POSITIVE_INFINITY;
		this.predecesseur = null;
	}

	public Node getSommet() {
		return this.sommet;
	}

	public int getMapIndex() {
		return this.mapIndex;
	}

	public boolean getMark() {
		return this.marque;
	}

	public double getCost() {
		return this.cout;
	}

	public Arc getFather() {
		return this.predecesseur;
	}

	public void setMark(boolean marque) {
		this.marque = marque;
	}

	public void setCost(double cout) {
		this.cout = cout;
	}

	public void setFather(Arc predecesseur) {
		this.predecesseur = predecesseur;
	}

	@Override
	public int compareTo(LabelMeeting autre) {
		if (this.cout < autre.cout) {
			return -1;
		} else if (this.cout > autre.cout) {
			return 1;
		} else {
			return 0;
		}
	}

	public boolean equals(LabelMeeting other) {
		return (this.sommet.getId() == other.getSommet().getId());
	}

	@Override
	public String toString() {
		return this.sommet + " - " + this.cout;
	}
}
