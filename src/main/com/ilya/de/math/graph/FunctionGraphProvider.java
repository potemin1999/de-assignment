package com.ilya.de.math.graph;

import com.ilya.de.math.evaluator.FunctionEvaluator;
import com.ilya.de.math.function.Function2;
import com.ilya.de.math.function.Graph;

public class FunctionGraphProvider implements GraphProvider {

    private final FunctionEvaluator evaluator;

    public FunctionGraphProvider(double startX, double endX, double step, Function2 function) {
        evaluator = new FunctionEvaluator();
        evaluator.setFunction(function);
        evaluator.setInterval(startX, endX);
        evaluator.setStep(step);
    }

    @Override
    public Graph[] getGraphs() {
        return evaluator.getGraphs();
    }

}
