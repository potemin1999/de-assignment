package com.ilya.de.math.graph;

import com.ilya.de.math.evaluator.Evaluator;
import com.ilya.de.math.function.Function2;
import lombok.Getter;
import lombok.Setter;

public class FunctionGraphProvider implements GraphProvider {

    private final Evaluator evaluator;
    private Graph cached;

    @Getter
    @Setter
    private String name;

    public <T extends Evaluator> FunctionGraphProvider(String name, T evaluator, double startX,
                                                       double endX, double step, Function2 function) {
        this(evaluator, startX, endX, step, function);
        this.name = name;
    }

    public <T extends Evaluator> FunctionGraphProvider(T evaluator, double startX, double endX,
                                                       double step, Function2 function) {
        this.evaluator = evaluator;
        evaluator.setFunction(function);
        evaluator.setInterval(startX, endX);
        evaluator.setStep(step);
    }

    @Override
    public Graph getGraph(boolean redraw) {
        if (redraw || cached == null) {
            try {
                cached = new Graph(evaluator.evaluate());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                System.err.println("problem with evaluator:" + evaluator.toString());
            }
        }
        return cached;
    }

}
