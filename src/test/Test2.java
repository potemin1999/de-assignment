import com.ilya.de.math.function.Function2Generator;
import com.ilya.de.math.function.Graph;
import com.ilya.de.math.graph.EulerGraphProvider;
import com.ilya.de.math.graph.GraphProvider;
import com.ilya.de.math.graph.RungeKuttaGraphProvider;
import org.junit.Test;

public class Test2 {

    @Test
    public void i_test1(){
        GraphProvider provider = new EulerGraphProvider(2, -5, 0, 0.09,
                Function2Generator.gen("(1-2*y)*exp(x) + y*y + exp(2*x)"));
        Graph[] graph = provider.getGraphs();
    }

    @Test
    public void i_test2(){
        GraphProvider provider = new RungeKuttaGraphProvider(2, -5, 0, 0.09,
                Function2Generator.gen("(1-2*y)*exp(x) + y*y + exp(2*x)"));
        Graph[] graph = provider.getGraphs();
    }

}
