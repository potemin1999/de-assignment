package com.ilya.de.ui;

import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
public class GraphWindow {

    interface OnResizeListener {
        void onResize(double newWidth, double newHeight);
    }

    @Getter
    @Setter
    private Stage stage;

    @Getter
    @Setter
    private Parent rootView;

    private double width = 640.0;
    private double height = 480.0;

    private final List<OnResizeListener> onResizeListenerList;

    public GraphWindow() {
        onResizeListenerList = new LinkedList<>();
    }

    public void setTitle(String title) {
        assert stage != null;
        stage.setTitle(title);
    }

    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void show() {
        assert stage != null;
        assert rootView != null;
        stage.setScene(new Scene(rootView, width, height));
        stage.widthProperty().addListener(this::onStageWidthUpdated);
        stage.heightProperty().addListener(this::onStageHeightUpdated);
        stage.show();
    }

    private void onStageWidthUpdated(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {
        this.width = newWidth.doubleValue();
        notifyOnResize();
    }

    private void onStageHeightUpdated(ObservableValue<? extends Number> observable, Number oldHeight, Number newHeight) {
        this.height = newHeight.doubleValue();
        notifyOnResize();
    }

    private void notifyOnResize() {
        for (OnResizeListener listener : onResizeListenerList) {
            listener.onResize(width, height);
        }
    }

    public void addOnResizeListener(OnResizeListener listener) {
        onResizeListenerList.add(listener);
    }

    public void removeOnResizeListener(OnResizeListener listener) {
        onResizeListenerList.remove(listener);
    }


}
