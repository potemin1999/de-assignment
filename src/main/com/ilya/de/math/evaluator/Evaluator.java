package com.ilya.de.math.evaluator;

import com.ilya.de.math.function.Function2;
import com.ilya.de.math.graph.Point;

import java.util.List;

public interface Evaluator {

    /**
     * @param minX x0
     * @param maxX X
     */
    void setInterval(double minX, double maxX);

    /**
     * @param step x coordinate step
     */
    void setStep(double step);

    /**
     * @param function to compute
     */
    void setFunction(Function2 function);

    /**
     * @return function points, according to the interval and step
     */
    List<Point> evaluate();

}
