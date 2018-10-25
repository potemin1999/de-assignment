package com.ilya.de.math.evaluator;

import com.ilya.de.math.graph.Point;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class RungeKuttaEvaluator extends AbstractEvaluator implements Y0AcceptingEvaluator {

    @Setter
    protected double y0 = 0;

    @Override
    public List<Point> evaluate() {
        ArrayList<Point> points = new ArrayList<>((int) Math.ceil((maxX - minX) / step));
        double currentX = minX;
        double lastY = y0;
        points.add(new Point(currentX, lastY));
        while (currentX <= maxX) {
            double x = currentX;
            double y = lastY;
            double k1 = step * function.func(x, y);
            double k2 = step * function.func(x + step * 0.5, y + k1 * 0.5);
            double k3 = step * function.func(x + step * 0.5, y + k2 * 0.5);
            double k4 = step * function.func(x + step, y + k3);
            lastY = lastY + (1.0 / 6.0) * (k1 + 2 * k2 + 2 * k3 + k4);
            currentX += step;
            points.add(new Point(currentX, lastY));
        }
        return points;
    }

}
