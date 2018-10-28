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

    }
}
