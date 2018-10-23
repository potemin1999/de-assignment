package com.ilya.de.ui;

import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.FlowPane;
import lombok.Getter;

public class WindowContent {

    private final FlowPane parent;
    private final FlowPane toolbar;
    @Getter
    private final Canvas graphView;

    public WindowContent() {
        parent = new FlowPane();
        parent.setFocusTraversable(true);
        parent.setOrientation(Orientation.HORIZONTAL);
        toolbar = new FlowPane();
        toolbar.setFocusTraversable(true);
        toolbar.setOrientation(Orientation.VERTICAL);
        graphView = new Canvas(800, 800);
        parent.getChildren().add(toolbar);
        parent.getChildren().add(graphView);
    }

    public Parent getParent() {
        return parent;
    }

}
