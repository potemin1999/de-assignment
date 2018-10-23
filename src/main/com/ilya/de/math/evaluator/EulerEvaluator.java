package com.ilya.de.math.evaluator;

import com.ilya.de.math.graph.Point;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class EulerEvaluator extends Evaluator {

    @Setter
    protected double y0 = 0;

    protected List<Point> evaluate() {
        ArrayList<Point> points = new ArrayList<>((int) Math.ceil((maxX - minX) / step));
        double currentX = minX;
        double lastY = y0;
        points.add(new Point(currentX, lastY));
        while (currentX <= maxX) {
            lastY = lastY + step * function.func(currentX, lastY);
            currentX += step;
            points.add(new Point(currentX, lastY));
        }
        return points;
    }

}
