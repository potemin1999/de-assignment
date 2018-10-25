package com.ilya.de.math.evaluator;

import com.ilya.de.math.function.Function2;
import com.ilya.de.math.graph.GraphProvider;
import com.ilya.de.math.graph.Point;
import com.ilya.de.math.function.Graph;
import javafx.scene.paint.Color;
import lombok.Setter;

import java.util.List;

public abstract class AbstractEvaluator implements GraphProvider, Evaluator {

    private boolean evaluated = false;
    private List<Point> points;
    double minX = 0;
    double maxX = 1;
    @Setter
    protected double step = 1;
    @Setter
    protected Function2 function;
    private Graph cached;

    public void setInterval(double minX, double maxX) {
        this.minX = minX;
        this.maxX = maxX;
    }

    @Override
    public Graph getGraph() {
        if (!evaluated) {
            points = evaluate();
            cached = new Graph(points);
            evaluated = true;
        }
        return cached;
    }

    public abstract List<Point> evaluate();

}
