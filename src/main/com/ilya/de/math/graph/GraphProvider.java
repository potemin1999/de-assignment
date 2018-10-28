package com.ilya.de.math.graph;

public interface GraphProvider {

    /**
     * @param redraw
     * @return
     */
    Graph getGraph(boolean redraw);

    /**
     * ask "say my name", it will
     * @return it's name
     */
    String getName();

}
