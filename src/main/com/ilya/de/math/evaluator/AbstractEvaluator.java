package com.ilya.de.math.evaluator;

import com.ilya.de.math.function.Function2;
import com.ilya.de.math.graph.Point;
import lombok.Setter;

import java.util.List;

public abstract class AbstractEvaluator implements Evaluator {

    double minX = 0;
    double maxX = 1;
    @Setter
    protected double step = 1;
    @Setter
    protected Function2 function;

    public void setInterval(double minX, double maxX) {
        this.minX = minX;
        this.maxX = maxX;
    }

    public abstract List<Point> evaluate();

}
