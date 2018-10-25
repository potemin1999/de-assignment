package com.ilya.de.math.evaluator;

import com.ilya.de.math.function.Function2;
import com.ilya.de.math.function.Graph;
import com.ilya.de.math.graph.GraphProvider;
import com.ilya.de.math.graph.Point;

import java.util.List;

public interface Evaluator extends GraphProvider {

    void setInterval(double minX, double maxX);

    void setStep(double step);

    void setFunction(Function2 function);

    List<Point> evaluate();

}
