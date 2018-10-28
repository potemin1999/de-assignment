package com.ilya.de.math.evaluator;

import com.ilya.de.math.graph.Point;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ImprovedEulerEvaluator extends AbstractSyncEvaluator implements Y0AcceptingEvaluator {

    public static final String NAME = "Improved Euler";

    @Setter
    protected double y0 = 0;

    public List<Point> evaluate() {
        ArrayList<Point> points = new ArrayList<>((int) Math.ceil((maxX - minX) / step));
        double currentX = minX;
        double lastY = y0;
        double tempY = 0;
        points.add(new Point(currentX, lastY));
        while (currentX <= maxX) {
            if (!Double.isFinite(lastY)){
                lastY = tryToSynchronize(currentX);
            }
            tempY = lastY + step * function.func(currentX, lastY);
            lastY = lastY + step *
                    (function.func(currentX, lastY) + function.func(currentX + step, tempY)) * 0.5;
            currentX += step;
            points.add(new Point(currentX, lastY));

        }
        return points;
    }

}
