package com.ilya.de.ui;

import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import lombok.Getter;

public class WindowContent {

    private final Pane parent;
    @Getter
    private final VBox toolbar;
    @Getter
    private final Canvas graphView;
    private Text title;
    private FlowPane toolbarDescription;

    @Getter
    private double toolbarWidth;

    public WindowContent(double initialWidth,double initialHeight) {
        toolbarWidth = 200;
        parent = new HBox();
        parent.setFocusTraversable(true);
        toolbar = new VBox();
        toolbar.setFocusTraversable(true);
        toolbar.setMinWidth(toolbarWidth);
        fillToolbar(toolbar);
        graphView = new Canvas(initialWidth,initialHeight);
        parent.getChildren().add(toolbar);
        parent.getChildren().add(graphView);
    }

    private void fillToolbar(Pane toolbar){
        title = new Text();
        title.setText("Toolbar");
        toolbarDescription = new FlowPane();
        toolbarDescription.setOrientation(Orientation.VERTICAL);
        toolbar.getChildren().add(title);
        toolbar.getChildren().add(toolbarDescription);
    }

    public Parent getParent() {
        return parent;
    }

    public void onWindowResize(double width,double height){
        parent.requestLayout();
    }

}
