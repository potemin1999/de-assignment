package com.ilya.de.math.graph;

import com.ilya.de.math.function.Graph;

public interface GraphProvider {

    Graph getGraph(boolean redraw);

    String getName();

}
