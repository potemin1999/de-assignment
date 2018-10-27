package com.ilya.de.ui;

import com.ilya.de.math.graph.GraphProvider;
import com.ilya.de.math.graph.Point;
import com.ilya.de.math.function.Graph;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@NoArgsConstructor
public class GraphController {

    @Getter
    private Canvas graphView;
    private GraphicsContext graphicsContext;

    private List<GraphProvider> graphProviders = new ArrayList<>(16);
    private List<Color> graphColors = new ArrayList<>(16);

    @Getter
    @Setter
    private Point center = new Point(0, 0);

    @Getter
    @Setter
    private double zoom = 1;

    private final double pixelsPerCell = 100;

    private double clickLastX;
    private double clickLastY;

    private double canvasWidth = 0;
    private double canvasHeight = 0;

    public void addGraphProvider(GraphProvider graphProvider, Color color) {
        graphProviders.add(graphProvider);
        graphColors.add(color);
    }

    public void onCanvasResized(double width, double height) {
        canvasWidth = width;
        canvasHeight = height;
        graphView.setWidth(width);
        graphView.setHeight(height);
        drawGraph();
    }

    public void removeGraphProvider(GraphProvider graphProvider) {
        int index = graphProviders.indexOf(graphProvider);
        if (index == -1) return;
        graphProviders.remove(index);
        graphColors.remove(index);
    }

    public boolean removeGraphProviderByName(String name) {
        for (GraphProvider provider : graphProviders) {
            if (name.equals(provider.getName())) {
                graphProviders.remove(provider);
                return true;
            }
        }
        return false;
    }

    public void setGraphView(Canvas graphView) {
        this.graphView = graphView;
        this.graphView.setFocusTraversable(true);
        this.graphicsContext = graphView.getGraphicsContext2D();
        this.graphView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleClick);
        this.graphView.addEventHandler(MouseEvent.MOUSE_PRESSED, this::handleMousePressed);
        this.graphView.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleDrag);
        this.graphView.setOnKeyPressed(this::handleKeyPressed);
        this.canvasWidth = graphView.getWidth();
        this.canvasHeight = graphView.getHeight();
    }

    public void drawGraph() {
        if (graphView == null) return;
        drawGraph(graphicsContext);
    }

    private void drawGraph(GraphicsContext context) {
        context.setFill(Color.WHITE);
        context.fillRect(0, 0, graphView.getWidth(), graphView.getHeight());
        context.setStroke(Color.BLACK);
        context.setLineWidth(2.0);
        drawCoordinates(context);
        Iterator<GraphProvider> providerIterator = graphProviders.iterator();
        Iterator<Color> colorIterator = graphColors.iterator();
        while (providerIterator.hasNext()) {
            GraphProvider provider = providerIterator.next();
            Color color = colorIterator.next();
            drawGraphPoints(context, provider.getGraph(), color);
        }
    }

    private void drawCoordinates(GraphicsContext context) {
        context.setLineWidth(1.0);
        drawCoordinateAxisAndGrid(context);
    }

    private void drawCoordinateAxisAndGrid(GraphicsContext context) {
        context.setFill(Color.BLACK);
        double leftMostCoordinate = Math.floor(convertToGraphX(0));
        double rightMostCoordinate = Math.ceil(convertToGraphX(canvasWidth));
        double topMostCoordinate = Math.ceil(convertToGraphY(0));
        double bottomMostCoordinate = Math.floor(convertToGraphY(canvasHeight));
        double zeroX = convertFromGraphX(0);
        double zeroY = convertFromGraphY(0);
        for (double i = leftMostCoordinate; i < rightMostCoordinate + 1; i++) {
            double startX = convertFromGraphX(i - 1);
            double endX = convertFromGraphX(i);
            context.setLineWidth(0.5);
            context.strokeLine(endX, 0, endX, canvasHeight);
            context.setLineWidth(2.0);
            context.strokeLine(startX, zeroY, endX, zeroY);
            context.setLineWidth(1.0);
            context.strokeLine(endX, zeroY + 2, endX, zeroY - 2);
            context.fillText(((int) i) + "", endX + 4 / zoom, zeroY + 12 / zoom);
        }
        for (double j = bottomMostCoordinate; j < topMostCoordinate + 1; j++) {
            double startY = convertToGraphY(j - 1);
            double endY = convertFromGraphY(j);
            context.setLineWidth(0.5);
            context.strokeLine(0, endY, canvasWidth, endY);
            context.setLineWidth(2.0);
            context.strokeLine(zeroX, startY, zeroX, endY);
            context.setLineWidth(1.0);
            context.strokeLine(zeroX + 2, endY, zeroX - 2, endY);
            context.fillText("" + ((int) j), zeroX + 4 / zoom, endY + 12 / zoom);
        }
        context.setLineWidth(2.0);
    }

    private void drawGraphPoints(GraphicsContext context, Graph graph, Color color) {
        List<Point> points = graph.getPoints();
        Iterator<Point> iterator = points.iterator();
        if (!iterator.hasNext()) return;
        context.setStroke(color);
        Point startPoint = iterator.next();
        double[] startPointCoordinates = {convertFromGraphX(startPoint.getX()), convertFromGraphY(startPoint.getY())};
        while (iterator.hasNext()) {
            Point endPoint = iterator.next();
            double[] endPointCoordinates = {convertFromGraphX(endPoint.getX()), convertFromGraphY(endPoint.getY())};
            context.strokeLine(startPointCoordinates[0], startPointCoordinates[1],
                    endPointCoordinates[0], endPointCoordinates[1]);
            startPointCoordinates = endPointCoordinates;
        }
    }

    private void handleMousePressed(MouseEvent event) {
        clickLastX = event.getX();
        clickLastY = event.getY();
        graphView.requestFocus();
    }

    @SuppressWarnings({"EmptyMethod", "unused"})
    private void handleClick(MouseEvent event) {
        //TODO: implement
    }

    private void handleDrag(MouseEvent event) {
        double dx = event.getX() - clickLastX;
        double dy = event.getY() - clickLastY;
        clickLastX = event.getX();
        clickLastY = event.getY();
        double scaledDX = dx / (pixelsPerCell / zoom);
        double scaledDY = dy / (pixelsPerCell / zoom);
        center.setX(center.getX() - scaledDX);
        center.setY(center.getY() + scaledDY);
        drawGraph();
    }

    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.W) {
            zoom *= 0.8;
        }
        if (event.getCode() == KeyCode.S) {
            zoom *= 1.2;
        }
        graphicsContext.setFont(new Font(graphicsContext.getFont().getName(), 10 / zoom));
        drawGraph();
    }

    private double convertToGraphX(double canvasX) {
        return center.getX() + (canvasX - canvasWidth * 0.5) * (zoom / pixelsPerCell);
    }

    private double convertToGraphY(double canvasY) {
        return center.getY() + (canvasHeight * 0.5 - canvasY) * (zoom / pixelsPerCell);
    }

    private double convertFromGraphX(double graphX) {
        return canvasWidth * 0.5 + (graphX - center.getX()) / (zoom / pixelsPerCell);
    }

    private double convertFromGraphY(double graphY) {
        return canvasHeight * 0.5 + (center.getY() - graphY) / (zoom / pixelsPerCell);
    }

}
