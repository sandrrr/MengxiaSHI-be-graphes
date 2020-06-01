package org.insa.graphs.gui.observers;

import org.insa.graphs.algorithm.packageswitch.PackageSwitchObserver;
import org.insa.graphs.gui.drawing.Drawing;
import org.insa.graphs.gui.drawing.overlays.PointSetOverlay;
import org.insa.graphs.model.Node;

import java.awt.*;
import java.util.ArrayList;

public class PackageSwitchGraphicObserver implements PackageSwitchObserver {

    // Drawing and Graph drawing
    protected Drawing drawing;
    protected ArrayList<PointSetOverlay> psOverlays = new ArrayList<>();

    public PackageSwitchGraphicObserver(Drawing drawing) {
        this.drawing = drawing;
        psOverlays.add(drawing.createPointSetOverlay(1, Color.GREEN));
        psOverlays.add(drawing.createPointSetOverlay(1, Color.RED));
        psOverlays.add(drawing.createPointSetOverlay(1, Color.magenta));
        psOverlays.add(drawing.createPointSetOverlay(1, Color.CYAN));
    }

    @Override
    public void notifyOriginProcessed(Node node) {
        // drawing.drawMarker(node.getPoint(), Color.RED);
    }

    @Override
    public void notifyNodeReached(Node node, int mapIndex) {
        psOverlays.get(mapIndex).addPoint(node.getPoint());
    }

    @Override
    public void notifyDestinationReached(Node node) {
        drawing.drawMarker(node.getPoint(), Color.BLACK, Color.BLACK, Drawing.AlphaMode.TRANSPARENT);
    }
}
