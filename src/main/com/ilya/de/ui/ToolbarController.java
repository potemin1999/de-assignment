package com.ilya.de.ui;

import com.ilya.de.math.graph.GraphProvider;
import javafx.scene.paint.Color;

public class ToolbarController {

    private final GraphController graphController;
    private final WindowContent windowContent;


    public ToolbarController(GraphController graphController, WindowContent windowContent) {
        this.graphController = graphController;
        this.windowContent = windowContent;

    }

    public void onGraphOptionChanged(GraphProvider provider, Color color,
                                     Boolean oldValue, Boolean newValue) {
        if (oldValue == newValue) return;
        if (newValue) {
            graphController.addGraphProvider(provider, color);
        } else {
            graphController.removeGraphProvider(provider);
        }
        graphController.drawGraph();
    }

    public void onToolbarResize(double width, double height) {

    }

}
