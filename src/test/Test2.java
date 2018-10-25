import com.ilya.de.math.evaluator.EulerEvaluator;
import com.ilya.de.math.evaluator.RungeKuttaEvaluator;
import com.ilya.de.math.function.Function2Generator;
import com.ilya.de.math.function.Graph;
import com.ilya.de.math.graph.FunctionGraphProviderWithY0;
import com.ilya.de.math.graph.GraphProvider;
import org.junit.Test;

public class Test2 {

    @Test
    public void i_test1() {
        GraphProvider provider = new FunctionGraphProviderWithY0(
                new EulerEvaluator(), 2, -5, 0, 0.09,
                Function2Generator.gen("(1-2*y)*exp(x) + y*y + exp(2*x)"));
        Graph graph = provider.getGraph();
    }

    @Test
    public void i_test2() {
        GraphProvider provider = new FunctionGraphProviderWithY0(
                new RungeKuttaEvaluator(), 2, -5, 0, 0.09,
                Function2Generator.gen("(1-2*y)*exp(x) + y*y + exp(2*x)"));
        Graph graph = provider.getGraph();
    }

}
