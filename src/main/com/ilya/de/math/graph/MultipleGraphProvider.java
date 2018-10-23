package com.ilya.de.math.graph;

import com.ilya.de.math.function.Graph;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

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
            provider.getGraphs()[0].setColor(colors[counter++]);
        }
        return this;
    }

    @Override
    public Graph[] getGraphs() {
        if (providers.size() == 0) {
            return new Graph[0];
        } else {
            int counter = 0;
            for (GraphProvider provider : providers) {
                graphs[counter++] = provider.getGraphs()[0];
            }
            return graphs;
        }
    }

}
