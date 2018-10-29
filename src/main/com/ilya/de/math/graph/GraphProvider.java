package com.ilya.de.math.graph;

public interface GraphProvider {

    /**
     * @param redraw if redraw needed
     * @return graph
     */
    Graph getGraph(boolean redraw);

    /**
     * ask "say my name",
     * it will
     *
     * @return it's name
     */
    String getName();

}
