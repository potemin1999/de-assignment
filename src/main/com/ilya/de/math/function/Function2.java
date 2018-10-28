package com.ilya.de.math.function;

public interface Function2 {

    /**
     * actually, SuppressWarnings was added for these IDE,
     * which can't deal with code generation in runtime
     *
     * @param x arg
     * @param y arg
     * @return value of function f(x,y)
     */
    @SuppressWarnings({"EmptyMethod", "unused"})
    double func(double x, double y);

}
