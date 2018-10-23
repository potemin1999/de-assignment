package com.ilya.de.math.rpn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Token {

    public Token(Operator operator) {
        this.operator = operator;
        this.isOperator = true;
    }

    public Token(Operand operand) {
        this.operand = operand;
    }

    public Token(Func function) {
        this.function = function;
        this.isFunction = true;
    }

    @Getter
    @Setter
    private boolean isOperator = false;

    @Getter
    @Setter
    private boolean isFunction = false;

    @Getter
    @Setter
    private Operator operator;

    @Getter
    @Setter
    private Operand operand;

    @Getter
    @Setter
    private Func function;

    @Override
    public String toString() {
        if (isFunction) {
            return function.toString();
        } else {
            if (isOperator) {
                return operator.toString();
            } else {
                return operand.toString();
            }
        }
    }

}
