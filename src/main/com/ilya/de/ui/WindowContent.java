package com.ilya.de.ui;

import com.ilya.de.math.graph.GraphProvider;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class WindowContent {

    public interface OnGraphOptionChangeListener {
        void onGraphOptionChanged(GraphProvider provider, Color color, boolean oldValue, boolean newValue);
    }

    public interface OnDataInputChangeListener {
        void onDataChanged(double oldValue, double newValue);
    }

    private final Pane parent;
    @Getter
    private final VBox toolbar;
    @Getter
    private final Canvas graphView;
    private Text title;
    private FlowPane toolbarDescription;

    @Getter
    private double toolbarWidth;

    private TextField solutionFuncField;
    private TextField sourceFuncField;

    private Pane graphsContainer;

    public WindowContent(double initialWidth, double initialHeight) {
        toolbarWidth = 320;
        parent = new HBox();
        parent.setFocusTraversable(true);
        toolbar = new VBox();
        toolbar.setFocusTraversable(true);
        toolbar.setMinWidth(toolbarWidth);
        fillToolbar(toolbar);
        graphView = new Canvas(initialWidth, initialHeight);
        parent.getChildren().add(toolbar);
        parent.getChildren().add(graphView);
    }

    private void fillToolbar(Pane toolbar) {
        title = new Text();
        title.setText("Toolbar");
        toolbar.getChildren().add(title);
        fillFunctions(toolbar);
        fillGraphs(toolbar);
        fillData(toolbar);
    }

    private void fillFunctions(Pane toolbar) {
        VBox functionsBox = new VBox();
        functionsBox.getChildren().add(createSubTitle("FUNCTIONS"));
        HBox solutionFuncBox = new HBox();
        solutionFuncBox.setAlignment(Pos.CENTER);
        Text solutionFuncDescription = new Text("y(x)=     ");
        solutionFuncDescription.setFont(Font.font("monospaced"));
        solutionFuncField = new TextField();
        solutionFuncField.setPrefWidth(toolbarWidth);
        solutionFuncBox.getChildren().add(solutionFuncDescription);
        solutionFuncBox.getChildren().add(solutionFuncField);
        functionsBox.getChildren().add(solutionFuncBox);
        HBox sourceFuncBox = new HBox();
        sourceFuncBox.setAlignment(Pos.CENTER);
        Text sourceFuncDescription = new Text("y'=f(x,y)=");
        sourceFuncDescription.setFont(Font.font("monospaced"));
        sourceFuncField = new TextField();
        sourceFuncField.setPrefWidth(toolbarWidth);
        sourceFuncBox.getChildren().add(sourceFuncDescription);
        sourceFuncBox.getChildren().add(sourceFuncField);
        functionsBox.getChildren().add(sourceFuncBox);
        toolbar.getChildren().add(functionsBox);
    }

    private void fillGraphs(Pane toolbar) {
        VBox graphsBox = new VBox();
        graphsBox.getChildren().add(createSubTitle("GRAPHS"));
        toolbar.getChildren().add(graphsBox);
        graphsContainer = graphsBox;
    }

    private void fillData(Pane toolbar) {
        VBox dataBox = new VBox();
        dataBox.getChildren().add(createSubTitle("DATA"));
        toolbar.getChildren().add(dataBox);
    }

    public Pane createSubTitle(String title) {
        HBox titleContainer = new HBox();
        titleContainer.setMinWidth(toolbarWidth);
        titleContainer.setAlignment(Pos.CENTER);
        Text titleText = new Text(title);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleContainer.getChildren().add(titleText);
        return titleContainer;
    }

    public void addDataInput(String name, double initialValue, double minValue, double maxValue, double step,
                             OnDataInputChangeListener listener) {
    }

    public void addGraphOption(String text, Color color, GraphProvider provider,
                               OnGraphOptionChangeListener listener, boolean defaultValue) {
        HBox content = new HBox();
        CheckBox checkBox = new CheckBox();
        if (listener != null) {
            checkBox.selectedProperty().addListener(
                    (observable, oldValue, newValue) -> listener.onGraphOptionChanged(provider, color, oldValue, newValue));
        }
        Text textBox = new Text(text);
        ColoredBoxView colorView = new ColoredBoxView(color);
        colorView.setWidth(16);
        colorView.setHeight(16);
        colorView.draw();
        content.getChildren().add(checkBox);
        content.getChildren().add(colorView);
        content.getChildren().add(textBox);
        graphsContainer.getChildren().add(content);
        checkBox.setSelected(defaultValue);
    }

    public Parent getParent() {
        return parent;
    }

    public void onWindowResize(double width, double height) {
        parent.requestLayout();
    }

    @RequiredArgsConstructor
    private class ColoredBoxView extends Canvas {

        final Color boxColor;

        public void draw() {
            GraphicsContext context = getGraphicsContext2D();
            context.setFill(boxColor);
            context.fillRect(0, 0, getWidth(), getHeight());
        }
    }

}
