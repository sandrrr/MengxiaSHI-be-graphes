package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {
	
	private Node sommet;
	private boolean marque;
	protected double cout;
	private Arc predecesseur;
	
	public Label(Node sommet) {
		this.sommet = sommet;
		this.marque = false;
		this.cout = Double.POSITIVE_INFINITY;
		this.predecesseur = null;
	}
	
	public Node getSommet() {
		return this.sommet;
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
	public int compareTo(Label autre) {
		if (this.cout < autre.cout) {
			return -1;
		} else if (this.cout > autre.cout) {
			return 1;
		} else {
			return 0;
		}
	}

    public boolean equals(Label other) {
		return (this.sommet.getId() == other.getSommet().getId());
    }

	@Override
	public String toString() {
		return this.sommet + " - " + this.cout;
	}
}
