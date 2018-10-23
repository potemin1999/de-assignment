package com.ilya.de;

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
        Function2 func = Function2Generator.gen("(1-2*y)*exp(x) + y*y + exp(2*x)");
        GraphProvider provider = new MultipleGraphProvider()
                .add(new EulerGraphProvider(2, -5, 0, step, func))
                //.add(new RungeKuttaGraphProvider(2, -5, 0, step, funcDer))
                //.add(new FunctionGraphProvider(-5, 0, step, funcExact))
                .colors(new Color[]{
                        //Color.BLACK,
                        Color.BLACK,
                        //Color.RED
                });
        controller.setGraphProvider(provider);
        controller.drawGraph();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
