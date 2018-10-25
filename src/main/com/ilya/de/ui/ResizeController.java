package com.ilya.de.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class ResizeController {

    @Getter
    private final GraphWindow graphWindow;
    @Getter
    private final WindowContent windowContent;
    @Getter
    @Setter
    private GraphWindow.OnResizeListener graphResizeListener;
    @Getter
    @Setter
    private GraphWindow.OnResizeListener toolbarResizeListener;

    public void doContentResize(double width, double height) {
        double toolbarWidth = windowContent.getToolbarWidth();
        double toolbarHeight = height;
        double graphWidth = width - toolbarWidth;
        double graphHeight = height;
        windowContent.getGraphView().resize(graphWidth,graphHeight);
        if (graphResizeListener!=null){
            graphResizeListener.onResize(graphWidth,graphHeight);
        }
        windowContent.getToolbar().resize(toolbarWidth,toolbarHeight);
        if (toolbarResizeListener!=null){
            toolbarResizeListener.onResize(toolbarWidth,toolbarHeight);
        }
    }

}
