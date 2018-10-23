package com.ilya.de.math.rpn;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class Operator {


    @Getter
    @Setter
    private int opCode;

    @Getter
    @Setter
    private int precedence;

    @Getter
    @Setter
    private boolean isRightAssociative;

    public Operator(int opCode) {
        this.opCode = opCode;
        this.precedence = getOperatorPrecedence(opCode);
        this.isRightAssociative = isOperatorRightAssociative(opCode);
    }

    public static final int OPERATOR_ADDITION = 0x0201;
    public static final int OPERATOR_SUBTRACTION = 0x0202;
    public static final int OPERATOR_MULTIPLICATION = 0x0303;
    public static final int OPERATOR_DIVISION = 0x0304;
    public static final int OPERATOR_PAREN_OPEN = 0x0005;
    public static final int OPERATOR_PAREN_CLOSE = 0x0006;
    public static final int OPERATOR_POWER = 0x1407;

    private static final Map<Character, Integer> operatorMap = new HashMap<>();

    static {
        operatorMap.put('+', OPERATOR_ADDITION);
        operatorMap.put('-', OPERATOR_SUBTRACTION);
        operatorMap.put('*', OPERATOR_MULTIPLICATION);
        operatorMap.put('/', OPERATOR_DIVISION);
        operatorMap.put('(', OPERATOR_PAREN_OPEN);
        operatorMap.put(')', OPERATOR_PAREN_CLOSE);
        operatorMap.put('^', OPERATOR_POWER);
    }

    public static Operator tryToMakeOperator(char c) {
        if (!operatorMap.containsKey(c)) {
            return null;
        } else {
            return new Operator(operatorMap.get(c));
        }
    }

    private static int getOperatorPrecedence(int opCode) {
        return (0x0f00 & opCode) >> 8;
    }

    private static boolean isOperatorRightAssociative(int opCode) {
        return (0x1000 & opCode) == 0x1000;
    }

    public String toString() {
        switch (opCode) {
            case OPERATOR_ADDITION:
                return "+";
            case OPERATOR_SUBTRACTION:
                return "-";
            case OPERATOR_PAREN_OPEN:
                return "(";
            case OPERATOR_PAREN_CLOSE:
                return ")";
            case OPERATOR_MULTIPLICATION:
                return "*";
            case OPERATOR_DIVISION:
                return "/";
            case OPERATOR_POWER:
                return "^";
            default:
                return "_";
        }
    }

}
