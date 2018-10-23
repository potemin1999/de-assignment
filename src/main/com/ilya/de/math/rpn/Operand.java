package com.ilya.de.math.rpn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Operand {

    public Operand(double value) {
        this.value = value;
    }

    public Operand(String variable) {
        this.isVariable = true;
        this.variable = variable;
    }

    @Getter
    @Setter
    private boolean isVariable = false;

    @Getter
    @Setter
    private double value = 0; //1.0

    @Getter
    @Setter
    private String variable = ""; //x

    @Override
    public String toString() {
        return isVariable ? variable : value + "";
    }

}
