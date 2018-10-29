package com.ilya.de.math.evaluator;

import com.ilya.de.math.graph.Point;

import java.util.ArrayList;
import java.util.List;

public class FunctionDifferenceEvaluator extends AbstractEvaluator {

    private Evaluator evaluatorA;
    private Evaluator evaluatorB;

    /**
     * computes difference with f1 and f2 points
     *
     * @param f1 a evaluator
     * @param f2 b evaluator
     */
    public FunctionDifferenceEvaluator(Evaluator f1, Evaluator f2) {
        evaluatorA = f1;
        evaluatorB = f2;
    }

    @Override
    public List<Point> evaluate() {
        List<Point> aPoints = evaluatorA.evaluate();
        List<Point> bPoints = evaluatorB.evaluate();
        int size = Math.min(aPoints.size(), bPoints.size());
        List<Point> points = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Point a = aPoints.get(i);
            Point b = bPoints.get(i);
            points.add(new Point(a.getX(), a.getY() - b.getY()));
        }
        return points;
    }

}
