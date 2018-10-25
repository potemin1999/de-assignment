package com.ilya.de.math.graph;

import com.ilya.de.math.evaluator.Y0AcceptingEvaluator;
import com.ilya.de.math.function.Function2;
import com.ilya.de.math.function.Graph;

public class FunctionGraphProviderWithY0 implements GraphProvider {

    private final Y0AcceptingEvaluator evaluator;
    private Graph cached;

    public <T extends Y0AcceptingEvaluator> FunctionGraphProviderWithY0(T evaluator, double y0, double startX,
                                                                        double endX, double step, Function2 function) {
        this.evaluator = evaluator;
        evaluator.setFunction(function);
        evaluator.setInterval(startX, endX);
        evaluator.setStep(step);
        evaluator.setY0(y0);
    }

    @Override
    public Graph getGraph() {
        if (cached == null) {
            cached = new Graph(evaluator.evaluate());
        }
        return cached;
    }

}
