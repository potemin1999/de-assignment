package com.ilya.de.math.evaluator;

import com.ilya.de.math.graph.Point;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractSyncEvaluator extends AbstractEvaluator {

    protected Evaluator syncEvaluator;
    boolean updateRequired = false;
    List<Point> points;

    public void setSyncEvaluator(Evaluator syncEvaluator) {
        this.syncEvaluator = syncEvaluator;
        updateRequired = true;
    }

    protected double tryToSynchronize(double currentX) {
        if (syncEvaluator == null) return Double.NaN;
        if (updateRequired) {
            points = syncEvaluator.evaluate();
            updateRequired = false;
        }
        Iterator<Point> iterator = points.iterator();
        Point p1 = null, p2 = null;
        while (iterator.hasNext()) {
            p2 = iterator.next();
            if (p2.getX() > currentX) {
                break;
            }
            p1 = p2;
        }
        if (p1 == null && p2 == null) return Double.NaN;
        if (p1 == null) return p2.getY();
        return p1.getY() + ((p2.getY() - p1.getY()) /
                (p2.getX() - p1.getX())) * (currentX - p1.getX());
    }

}
