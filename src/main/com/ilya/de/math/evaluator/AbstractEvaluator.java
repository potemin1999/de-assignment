package com.ilya.de.math.evaluator;

import com.ilya.de.math.function.Function2;
import com.ilya.de.math.graph.Point;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
public abstract class AbstractEvaluator implements Evaluator {

    double minX = 0;
    double maxX = 1;
    protected double step = 1;
    @Setter
    protected Function2 function;

    public void setInterval(double minX, double maxX) {
        this.minX = minX;
        this.maxX = maxX;
    }

    public void setStep(double newStep){
        if (!Double.isFinite(newStep)){
            throw new IllegalArgumentException("step should be finite double, got "+newStep);
        }
        step = newStep;
    }

    public abstract List<Point> evaluate();

}
