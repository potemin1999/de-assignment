package com.ilya.de.math.evaluator;

import com.ilya.de.math.graph.Point;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class EulerEvaluator extends AbstractSyncEvaluator implements Y0AcceptingEvaluator {

    public static final String NAME = "Euler";

    @Setter
    protected double y0 = 0;

    public List<Point> evaluate() {
        ArrayList<Point> points = new ArrayList<>((int) Math.ceil((maxX - minX) / step));
        double currentX = minX;
        double lastY = y0;
        points.add(new Point(currentX, lastY));
        while (currentX < maxX) {
            Point point = new Point(0, 0);
            double currentStep = step;
            if (!Double.isFinite(lastY)) {
                lastY = tryToSynchronize(currentX);
            }
            double funcRes = function.func(currentX, lastY);
            //System.out.println("euler("+currentX+","+lastY+") = "+funcRes);
            lastY = lastY + currentStep * funcRes;
            currentX += currentStep;
            point.setX(currentX);
            point.setY(lastY);
            points.add(new Point(currentX, lastY));
        }
        return points;
    }

}
