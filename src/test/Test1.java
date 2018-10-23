import com.ilya.de.math.function.Function2;
import com.ilya.de.math.function.Function2Generator;
import org.junit.Test;

public class Test1 {

    @Test
    public void a_test1(){
        String func = "(sin(4*x)+exp(y)) / sin ( min( 3 /2+16*x,y) )";
        Function2 function = new Function2Generator().generate(func);
        double res1 = function.func(1,2);
        System.out.println(res1+"");
        Function2 function2 = (x,y) -> (Math.sin(4*x) + Math.exp(y)) / Math.sin( Math.min(3d / 2 + 16*x, y));
        double res2 = function2.func(1,2);
        System.out.println(res2+"");
        assert res1 == res2;
    }

    @Test
    public void a_test2(){
        String func = "3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3";
        Function2 function = new Function2Generator().generate(func);
        System.out.println(function.func(3,4));
        assert function.func(3,4) == 3.0001220703125;
    }

    @Test
    public void a_test3(){
        String func = " exp(x) + 1";
        Function2 function2 = new Function2Generator().generate(func);
        double res = function2.func(1,2);
        System.out.println(res);
        assert Double.compare(res,3.718281828459045) == 0;
    }

    @Test
    public void a_test4(){
        double constant = Function2Generator.gen("(0-5-( 1/(exp(0-5)-2) )").func(0,0);
        System.out.println(constant);
        assert constant == -4.498309819075485;
    }

    @Test
    public void z_speedComparison(){
        String func = "(sin(y) + exp(6*x)+cos(y))/(22*sin(x)+45)";
        Function2 func1 = Function2Generator.gen(func);
        Function2 func2 = (Function2) (x,y) -> (Math.sin(y) + Math.exp(6*x)+Math.cos(y))/(22*Math.sin(x)+45);
        for (int i = 0; i < 100; i++) {
            func1.func(i,i);
            func2.func(i,i);
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 2000000; i++) {
            func1.func(i,i);
        }
        long middleTime = System.currentTimeMillis();
        for (int i = 0; i < 2000000; i++) {
            func2.func(i,i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("written function: "+(middleTime-startTime)+" result:"+func1.func(30,20));
        System.out.println("implemented function: "+(endTime-middleTime)+" result:"+func2.func(30,20));
        assert func1.func(30,20) == func2.func(30,20);
    }

    @Test
    public void z_speedGeneration(){
        String func = "(1-2*y)*exp(x) + y*y + exp(2*x)";
        for (int i = 0; i < 5; i++) {
            Function2Generator.gen(func).func(i,i);
        }
        long startTime = System.currentTimeMillis();
        long iterationCount = 1000;
        for (int i = 0; i < iterationCount; i++) {
            Function2Generator.gen(func).func(i,i);
        }
        System.out.println("average generation time: "+(System.currentTimeMillis()-startTime)*(1f/iterationCount) );
    }
}
