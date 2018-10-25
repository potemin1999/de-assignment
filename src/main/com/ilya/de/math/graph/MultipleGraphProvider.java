package com.ilya.de.math.graph;

import com.ilya.de.math.function.Graph;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

@Deprecated
public class MultipleGraphProvider implements GraphProvider {

    private Graph[] graphs;
    private List<GraphProvider> providers;

    public MultipleGraphProvider() {
        providers = new LinkedList<>();
    }

    public MultipleGraphProvider add(GraphProvider provider) {
        providers.add(provider);
        graphs = new Graph[providers.size()];
        return this;
    }

    public MultipleGraphProvider remove(GraphProvider provider) {
        providers.remove(provider);
        graphs = new Graph[providers.size()];
        return this;
    }

    public MultipleGraphProvider colors(Color[] colors) {
        int counter = 0;
        for (GraphProvider provider : providers) {
            //do nothing
        }
        return this;
    }

    @Override
    public Graph getGraph() {
        return graphs[0];
    }

}
