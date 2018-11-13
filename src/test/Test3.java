import com.ilya.de.ui.GraphController;
import org.junit.Test;

import java.util.function.Predicate;

public class Test3 {

    @Test
    public void test1() {
        Predicate<String> p = s -> !s.equals("s");
        p = p.and(t -> true);
        System.out.println(p.test("s"));
        System.out.println(p.test("l"));
        System.out.println(p.test("r"));
    }

    @Test
    public void test2() {
        GraphController.FastDecimalFormat formatter = new GraphController.FastDecimalFormat();
        String d1 = formatter.format(12345);
        System.out.println(d1);
        assert d1.equals("12345");
        String d2 = formatter.format(3.112,3);
        System.out.println(d2);
        assert d2.equals("3.112");
    }
}
