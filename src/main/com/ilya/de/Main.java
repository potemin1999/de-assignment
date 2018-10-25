package com.ilya.de;

import com.ilya.de.math.evaluator.EulerEvaluator;
import com.ilya.de.math.evaluator.FunctionEvaluator;
import com.ilya.de.math.evaluator.ImprovedEulerEvaluator;
import com.ilya.de.math.function.Function2;
import com.ilya.de.math.graph.*;
import com.ilya.de.math.function.Function2Generator;
import com.ilya.de.math.graph.Point;
import com.ilya.de.ui.GraphController;
import com.ilya.de.ui.GraphWindow;
import com.ilya.de.ui.WindowContent;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        double width = 800.0;
        double height = 800.0;
        WindowContent windowContent = new WindowContent();
        GraphController controller = new GraphController();
        controller.setGraphView(windowContent.getGraphView());
        controller.setCenter(new Point(0, 0));
        GraphWindow window = new GraphWindow();
        window.setStage(primaryStage);
        window.setTitle("Graph Window");
        window.setSize(width, height);
        window.setRootView(windowContent.getParent());
        window.show();
        double step = 0.01;
        Function2 solution = Function2Generator.gen("1/(0-4.498309818-x) + exp(x)");
        Function2 func = Function2Generator.gen("(1-2*y)*exp(x) + y*y + exp(2*x)");

        GraphProvider eulerGraphProvider = new FunctionGraphProviderWithY0(
                new EulerEvaluator(), 2, -5, 0, step, func);
        GraphProvider improvedEulerGraphProvider = new FunctionGraphProviderWithY0(
                new ImprovedEulerEvaluator(), 2, -5, 0, step, func);
        GraphProvider exactSolution = new FunctionGraphProvider(
                new FunctionEvaluator(), -5, 0, step, solution);

        controller.addGraphProvider(eulerGraphProvider, Color.RED);
        controller.addGraphProvider(improvedEulerGraphProvider, Color.BLACK);
        controller.addGraphProvider(exactSolution, Color.GREEN);
        controller.drawGraph();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
