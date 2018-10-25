package com.ilya.de.math.evaluator;

import com.ilya.de.math.graph.Point;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class FunctionEvaluator extends AbstractEvaluator {

    @Setter
    private double continuityLimit = Double.MAX_VALUE;
    private boolean useDiscontinuityCheck = true;

    public void useDiscontinuityCheck(boolean is) {
        useDiscontinuityCheck = is;
    }

    public void setStep(double step) {
        super.setStep(step);
        continuityLimit = 1000 * step;
    }

    @Override
    public List<Point> evaluate() {
        ArrayList<Point> points = new ArrayList<>((int) Math.ceil((maxX - minX) / step));
        Point startP = new Point(minX, function.func(minX, 0));
        points.add(startP);
        double prevY = startP.getY();
        for (double x = minX + step; x <= maxX; x += step) {
            Point p = new Point(x, function.func(x, 0));
            if (useDiscontinuityCheck && Math.abs(p.getY() - prevY) > continuityLimit) {
                Point p2 = new Point(x - step * 0.5, function.func(x - step * 0.5, 0));
                double diffY = p2.getY() - p.getY();
                if (Double.isNaN(diffY) || Math.abs(diffY) > continuityLimit) {
                    points.add(new Point(x - step * 0.5, Double.NaN));
                } else {
                    points.add(p2);
                }
            }
            points.add(p);
            prevY = p.getY();
        }
        return points;
    }

}
