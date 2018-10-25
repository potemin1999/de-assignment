package com.ilya.de.math.graph;

import com.ilya.de.math.evaluator.EulerEvaluator;
import com.ilya.de.math.function.Function2;
import com.ilya.de.math.function.Graph;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Deprecated
public class EulerGraphProvider implements GraphProvider {

    private final EulerEvaluator evaluator;

    @SuppressWarnings("SameParameterValue")
    public EulerGraphProvider(double y0, double x0, double X, double step, Function2 function) {
        evaluator = new EulerEvaluator();
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
