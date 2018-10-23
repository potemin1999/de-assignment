package com.ilya.de.math.function;

class FunctionClassLoader extends ClassLoader {

    FunctionClassLoader() {
        super(getSystemClassLoader());
    }

    Class<?> define(String className, byte[] classBytes) {
        return defineClass(className, classBytes, 0, classBytes.length);
    }

}
