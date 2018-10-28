package com.ilya.de.ui;

import com.ilya.de.math.graph.GraphProvider;
import com.ilya.de.math.graph.Point;
import com.ilya.de.math.graph.Graph;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
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
    private double zoomX = 1;
    private double zoomY = 1;

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
        this.graphView.addEventHandler(ScrollEvent.SCROLL, this::handleScroll);
        this.graphView.setOnKeyPressed(this::handleKeyPressed);
        this.canvasWidth = graphView.getWidth();
        this.canvasHeight = graphView.getHeight();
    }

    public void drawGraph() {
        drawGraph(true);
    }

    public void drawGraph(boolean recomputeGraph) {
        if (graphView == null) return;
        drawGraph(graphicsContext, recomputeGraph);
    }

    private void drawGraph(GraphicsContext context, boolean recomputeGraphs) {
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
            drawGraphPoints(context, provider.getGraph(recomputeGraphs), color);
        }
    }

    private void drawCoordinates(GraphicsContext context) {
        context.setLineWidth(1.0);
        drawCoordinateAxisAndGrid(context);
    }

    private double getStep(double zoom) {
        double base = 10;
        return Math.pow(base, Math.round(Math.log(zoom) / Math.log(base)));
    }

    private DecimalFormat format = new DecimalFormat();

    private String getCoordinateWithStep(double coord, double step) {
        return format.format(coord);
    }

    private void drawCoordinateAxisAndGrid(GraphicsContext context) {
        context.setFill(Color.BLACK);
        final double textXOffset = 4;
        final double textYOffset = 16;
        double xStep = getStep(zoomX);
        double yStep = getStep(zoomY);
        double leftMostCoordinate = convertToGraphX(0);
        double startXCoordinate = xStep * (Math.floor(leftMostCoordinate / xStep));
        double rightMostCoordinate = convertToGraphX(canvasWidth);
        double endXCoordinate = xStep * (Math.ceil(rightMostCoordinate / xStep));
        double topMostCoordinate = convertToGraphY(0);
        double endYCoordinate = yStep * (Math.ceil(topMostCoordinate / yStep));
        double bottomMostCoordinate = convertToGraphY(canvasHeight);
        double startYCoordinate = yStep * (Math.floor(bottomMostCoordinate / yStep));
        double zeroX = convertFromGraphX(0);
        double zeroY = convertFromGraphY(0);
        context.setLineWidth(2.0);
        context.strokeLine(convertFromGraphX(leftMostCoordinate), zeroY,
                convertFromGraphX(rightMostCoordinate), zeroY);
        context.strokeLine(zeroX, convertFromGraphY(topMostCoordinate),
                zeroX, convertFromGraphY(bottomMostCoordinate));
        for (double i = startXCoordinate; i < endXCoordinate + 1; i += xStep) {
            double endX = convertFromGraphX(i);
            context.setLineWidth(0.5);
            context.strokeLine(endX, 0, endX, canvasHeight);
            context.setLineWidth(1.0);
            context.strokeLine(endX, zeroY + 2, endX, zeroY - 2);
            {
                String coordStr = getCoordinateWithStep(i, xStep);
                double textX = endX + textXOffset;
                double textY = Math.min(Math.max(16, zeroY + textYOffset), canvasHeight - 28);
                context.fillText(coordStr, textX, textY);
            }
        }
        for (double j = startYCoordinate; j < endYCoordinate + 1; j += yStep) {
            double endY = convertFromGraphY(j);
            context.setLineWidth(0.5);
            context.strokeLine(0, endY, canvasWidth, endY);
            context.setLineWidth(1.0);
            context.strokeLine(zeroX + 2, endY, zeroX - 2, endY);
            {
                String coordStr = getCoordinateWithStep(j, yStep);
                double textX = Math.min(Math.max(2, zeroX + textXOffset), canvasWidth - 30);
                double textY = endY + textYOffset;
                context.fillText(coordStr, textX, textY);
            }
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

    private void handleScroll(ScrollEvent event) {
        double dZoom = event.getDeltaY() < 0 ? 1.2 : 0.8;
        zoom *= dZoom;
        zoomX *= dZoom;
        zoomY *= dZoom;
        drawGraph(false);
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
        double scaledDX = dx / (pixelsPerCell / zoomX);
        double scaledDY = dy / (pixelsPerCell / zoomY);
        center.setX(center.getX() - scaledDX);
        center.setY(center.getY() + scaledDY);
        drawGraph(false);
    }

    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.W) {
            zoom *= 0.8;
            zoomX *= 0.8;
            zoomY *= 0.8;
        }
        if (event.getCode() == KeyCode.S) {
            zoom *= 1.2;
            zoomX *= 1.2;
            zoomY *= 1.2;
        }
        graphicsContext.setFont(new Font(graphicsContext.getFont().getName(), 10 / zoom));
        drawGraph(false);
    }

    private double convertToGraphX(double canvasX) {
        return center.getX() + (canvasX - canvasWidth * 0.5) * (zoomX / pixelsPerCell);
    }

    private double convertToGraphY(double canvasY) {
        return center.getY() + (canvasHeight * 0.5 - canvasY) * (zoomY / pixelsPerCell);
    }

    private double convertFromGraphX(double graphX) {
        return canvasWidth * 0.5 + (graphX - center.getX()) / (zoomX / pixelsPerCell);
    }

    private double convertFromGraphY(double graphY) {
        return canvasHeight * 0.5 + (center.getY() - graphY) / (zoomY / pixelsPerCell);
    }

}
