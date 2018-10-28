package com.ilya.de;

import com.ilya.de.math.evaluator.*;
import com.ilya.de.math.function.Function2;
import com.ilya.de.math.graph.*;
import com.ilya.de.math.function.Function2Generator;
import com.ilya.de.ui.*;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    GraphController controller;

    EulerEvaluator eulerEvaluator = new EulerEvaluator();
    ImprovedEulerEvaluator improvedEulerEvaluator = new ImprovedEulerEvaluator();
    RungeKuttaEvaluator rungeKuttaEvaluator = new RungeKuttaEvaluator();
    FunctionEvaluator solutionEvaluator = new FunctionEvaluator();
    Evaluator eulerErrorEvaluator =
            new FunctionDifferenceEvaluator(solutionEvaluator,eulerEvaluator);
    Evaluator improvedEulerErrorEvaluator =
            new FunctionDifferenceEvaluator(solutionEvaluator,improvedEulerEvaluator);
    Evaluator rungeKuttaErrorEvaluator =
            new FunctionDifferenceEvaluator(solutionEvaluator,rungeKuttaEvaluator);

    Function2 sourceFunction;
    Function2 solutionFunction;
    double x0 = -5;
    double y0 = 2;
    double X = 0;
    double step = 0.005;
    String solutionFunctionStr = "1/(0-4.498309818-x) + exp(x)";
    String sourceFunctionStr = "(1-2*y)*exp(x) + y*y + exp(2*x)";

    @Override
    public void start(Stage primaryStage) {
        double width = 1000.0;
        double height = 680.0;
        WindowContent windowContent = new WindowContent(width, height);
        controller = new GraphController();
        controller.setGraphView(windowContent.getGraphView());
        controller.setCenter(new Point(0, 0));
        GraphWindow window = new GraphWindow();
        window.setStage(primaryStage);
        window.setTitle("Graph Window");
        window.setSize(width, height);
        window.setRootView(windowContent.getParent());
        ResizeController resizeController = new ResizeController(window, windowContent);
        ToolbarController toolbarController = new ToolbarController(controller, windowContent);
        window.addOnResizeListener(resizeController::doContentResize);
        window.addOnResizeListener(windowContent::onWindowResize);
        resizeController.setGraphResizeListener(controller::onCanvasResized);
        resizeController.setToolbarResizeListener(toolbarController::onToolbarResize);
        windowContent.setSourceChangeListener(this::onSourceFunctionChanged);
        windowContent.setSolutionChangeListener(this::onSolutionFunctionChanged);
        windowContent.getSolutionFuncField().setText(solutionFunctionStr);
        windowContent.getSourceFuncField().setText(sourceFunctionStr);

        solutionFunction = Function2Generator.gen(solutionFunctionStr);
        sourceFunction = Function2Generator.gen(sourceFunctionStr);

        eulerEvaluator.setSyncEvaluator(solutionEvaluator);
        improvedEulerEvaluator.setSyncEvaluator(solutionEvaluator);
        rungeKuttaEvaluator.setSyncEvaluator(solutionEvaluator);

        GraphProvider eulerGraphProvider = new FunctionGraphProviderWithY0(EulerEvaluator.NAME,
                eulerEvaluator, y0, x0, X, step, sourceFunction);
        GraphProvider eulerErrorGraphProvider = new FunctionGraphProvider(EulerEvaluator.NAME+" Error",
                eulerErrorEvaluator, x0,X,step,null);
        GraphProvider improvedEulerGraphProvider = new FunctionGraphProviderWithY0(ImprovedEulerEvaluator.NAME,
                improvedEulerEvaluator, y0,x0,X, step, sourceFunction);
        GraphProvider improvedEulerErrorGraphProvider = new FunctionGraphProvider(ImprovedEulerEvaluator.NAME+" Error",
                improvedEulerErrorEvaluator,x0,X,step,null);
        GraphProvider rungeKuttaGraphProvider = new FunctionGraphProviderWithY0(RungeKuttaEvaluator.NAME,
                rungeKuttaEvaluator, 2, -5, 0, step, sourceFunction);
        GraphProvider rungeKuttaErrorGraphProvider = new FunctionGraphProvider(RungeKuttaEvaluator.NAME + " Error",
                rungeKuttaErrorEvaluator,x0,X,step,null);

        GraphProvider exactSolution = new FunctionGraphProvider(
                solutionEvaluator, -5, 0, step, solutionFunction);

        windowContent.addGraphOption(EulerEvaluator.NAME + " Method", Color.GREEN, eulerGraphProvider,
                toolbarController::onGraphOptionChanged, true);
        windowContent.addGraphOption(EulerEvaluator.NAME+" Method Error",Color.VIOLET,eulerErrorGraphProvider,
                toolbarController::onGraphOptionChanged,false);
        windowContent.addGraphOption(ImprovedEulerEvaluator.NAME + " Method", Color.RED, improvedEulerGraphProvider,
                toolbarController::onGraphOptionChanged, true);
        windowContent.addGraphOption(ImprovedEulerEvaluator.NAME+" Method Error",Color.YELLOW,
                improvedEulerErrorGraphProvider,toolbarController::onGraphOptionChanged,false);
        windowContent.addGraphOption(RungeKuttaEvaluator.NAME + " Method", Color.BLUE, rungeKuttaGraphProvider,
                toolbarController::onGraphOptionChanged, true);
        windowContent.addGraphOption(RungeKuttaEvaluator.NAME+" Method Error",Color.CYAN, rungeKuttaErrorGraphProvider,
                toolbarController::onGraphOptionChanged, false);
        windowContent.addGraphOption("Solution", Color.BLACK, exactSolution,
                toolbarController::onGraphOptionChanged, true);

        windowContent.addDataInput("x0",x0,
                -Double.MAX_VALUE,Double.MAX_VALUE, 1,this::onX0Changed);
        windowContent.addDataInput("X",X,
                -Double.MAX_VALUE,Double.MAX_VALUE,1,this::onXChanged);
        windowContent.addDataInput("y0",y0,
                -Double.MAX_VALUE,Double.MAX_VALUE,1,this::onY0Changed);
        windowContent.addDataInput("step",step,
                0.0001,1,0.01,this::onStepChanged);

        window.show();
        controller.drawGraph();
    }

    public void onSolutionFunctionChanged(String funcStr){
        solutionFunction = Function2Generator.gen(funcStr);
        solutionEvaluator.setFunction(solutionFunction);
        Evaluator[] evaluators = new Evaluator[]{
                eulerEvaluator,
                improvedEulerEvaluator,
                rungeKuttaEvaluator,};
        for (Evaluator evaluator : evaluators){
            if (evaluator instanceof AbstractSyncEvaluator) {
                ((AbstractSyncEvaluator) evaluator).setSyncEvaluator(solutionEvaluator);
            }
        }
        controller.drawGraph();
    }

    public void onSourceFunctionChanged(String funcStr){
        sourceFunction = Function2Generator.gen(funcStr);
        eulerEvaluator.setFunction(sourceFunction);
        improvedEulerEvaluator.setFunction(sourceFunction);
        rungeKuttaEvaluator.setFunction(sourceFunction);
        controller.drawGraph();
    }

    public void onStepChanged(double newValue){
        step = newValue;
        updateEvaluatorData();
    }

    public void onY0Changed(double newValue){
        y0 = newValue;
        updateEvaluatorData();
    }

    public void onX0Changed(double newValue){
        x0 = newValue;
        updateEvaluatorData();
    }

    public void onXChanged(double newValue){
        X = newValue;
        updateEvaluatorData();
    }

    public void updateEvaluatorData(){
        Evaluator[] evaluators = new Evaluator[]{
                solutionEvaluator,
                eulerEvaluator,
                eulerErrorEvaluator,
                improvedEulerEvaluator,
                improvedEulerErrorEvaluator,
                rungeKuttaEvaluator,
                rungeKuttaErrorEvaluator};
        for (Evaluator evaluator : evaluators){
            evaluator.setStep(step);
            evaluator.setInterval(x0,X);
            if (evaluator instanceof Y0AcceptingEvaluator){
                ((Y0AcceptingEvaluator) evaluator).setY0(y0);
            }
        }
        controller.drawGraph();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
