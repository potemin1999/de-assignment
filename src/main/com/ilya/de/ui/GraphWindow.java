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

    // those, who wants to receive resize event, should implement this
    public interface OnResizeListener {

        /**
         * called when resize occurs
         *
         * @param newWidth  width
         * @param newHeight height
         */
        void onResize(double newWidth, double newHeight);
    }

    @Getter
    @Setter
    private Stage stage;

    @Getter
    @Setter
    private Parent rootView;

    @Getter
    private double width = 640.0;
    @Getter
    private double height = 480.0;

    // resize listeners list
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
        stage.toFront();
    }

    private void onStageWidthUpdated(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {
        this.width = newWidth.doubleValue();
        notifyOnResize();
    }

    private void onStageHeightUpdated(ObservableValue<? extends Number> observable, Number oldHeight, Number newHeight) {
        this.height = newHeight.doubleValue();
        notifyOnResize();
    }

    /**
     * call listeners
     */
    private void notifyOnResize() {
        for (OnResizeListener listener : onResizeListenerList) {
            listener.onResize(width, height);
        }
    }

    /**
     * add new OnResizeListener
     *
     * @param listener to add
     */
    public void addOnResizeListener(OnResizeListener listener) {
        onResizeListenerList.add(listener);
    }

    /**
     * removes OnResizeListener
     *
     * @param listener to remove
     */
    public void removeOnResizeListener(OnResizeListener listener) {
        onResizeListenerList.remove(listener);
    }


}
