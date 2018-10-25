package com.ilya.de.math.graph;

import com.ilya.de.math.evaluator.RungeKuttaEvaluator;
import com.ilya.de.math.function.Function2;
import com.ilya.de.math.function.Graph;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Deprecated
public class RungeKuttaGraphProvider implements GraphProvider {

    private final RungeKuttaEvaluator evaluator;
    private final Graph[] graps;

    public RungeKuttaGraphProvider(double y0, double x0, double X, double step, Function2 function) {
        graps = new Graph[1];
        evaluator = new RungeKuttaEvaluator();
        evaluator.setY0(y0);
        evaluator.setInterval(x0, X);
        evaluator.setStep(step);
        evaluator.setFunction(function);
    }

    @Override
    public Graph getGraph() {
        throw new NotImplementedException();
    }

    @Override
    public String getName() {
        return "deprecated";
    }

}
