package com.ilya.de.math.evaluator;

import com.ilya.de.math.graph.Point;

import java.util.ArrayList;
import java.util.List;

public class FunctionEvaluator extends Evaluator {

    @Override
    protected List<Point> evaluate() {
        ArrayList<Point> points = new ArrayList<>((int) Math.ceil((maxX - minX) / step));
        for (double x = minX; x <= maxX; x += step) {
            points.add(new Point(x, function.func(x, 0)));
        }
        return points;
    }

}
