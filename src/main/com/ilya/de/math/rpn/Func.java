package com.ilya.de.math.rpn;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("WeakerAccess")
@AllArgsConstructor
public class Func {

    @Getter
    private final String name;

    @Getter
    private boolean isBinary = false;

    @Override
    public String toString() {
        return name + (isBinary ? "(DD)D" : "(D)D");
    }

}
