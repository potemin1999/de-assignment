package com.ilya.de.ui;

public class ToolbarController {

    private final GraphController graphController;
    private final WindowContent windowContent;

    public ToolbarController(GraphController graphController,WindowContent windowContent){
        this.graphController = graphController;
        this.windowContent = windowContent;

    }

    public void onToolbarResize(double width, double height) {

    }

}
