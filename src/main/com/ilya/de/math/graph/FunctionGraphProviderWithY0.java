package com.ilya.de.math.graph;

import com.ilya.de.math.evaluator.Y0AcceptingEvaluator;
import com.ilya.de.math.function.Function2;

public class FunctionGraphProviderWithY0 extends FunctionGraphProvider {

    public <T extends Y0AcceptingEvaluator> FunctionGraphProviderWithY0(String name, T evaluator, double y0, double startX,
                                                                        double endX, double step, Function2 function) {
        super(evaluator, startX, endX, step, function);
        evaluator.setY0(y0);
    }

    public <T extends Y0AcceptingEvaluator> FunctionGraphProviderWithY0(T evaluator, double y0, double startX,
                                                                        double endX, double step, Function2 function) {
        super(evaluator,startX,endX,step,function);
        evaluator.setY0(y0);
    }

}
