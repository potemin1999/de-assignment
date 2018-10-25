package com.ilya.de.math.rpn;

import java.util.List;

public class Tokenizer {

    public Tokenizer() {

    }

    public List<Token> parse(String str) throws InvalidTokenException {
        List<Token> tokens = new PrintableLinkedList<>();
        char[] chars = str.toCharArray();
        StringBuilder buffer = new StringBuilder();
        for (char aChar : chars) {
            if (aChar == ',') {
                if (buffer.length() > 0) {
                    String operand = buffer.toString();
                    buffer.delete(0, buffer.length());
                    addOperand(operand, tokens);
                }
                tokens.add(new Token(new Operator(Operator.OPERATOR_PAREN_CLOSE)));
                tokens.add(new Token(new Operator(Operator.OPERATOR_PAREN_OPEN)));
                continue;
            }
            if (aChar == ' ') continue;
            if (aChar == '(' && buffer.length() > 0) {
                String funcName = buffer.toString();
                buffer.delete(0, buffer.length());
                if (funcName.equals("min") || funcName.equals("max")) {
                    tokens.add(new Token(new Func(funcName, true)));
                } else {
                    tokens.add(new Token(new Func(funcName, false)));
                }
            }
            Operator operator = Operator.tryToMakeOperator(aChar);
            if (operator != null) {
                if (buffer.length() > 0) {
                    String buffered = buffer.toString();
                    addOperand(buffered, tokens);
                    buffer.delete(0, buffer.length());
                }
                tokens.add(new Token(operator));
                if (operator.getOpCode() == Operator.OPERATOR_PAREN_OPEN ||
                        operator.getOpCode() == Operator.OPERATOR_PAREN_CLOSE) {
                    tokens.add(new Token(operator));
                }
            } else {
                buffer.append(aChar);
            }
        }
        if (buffer.length() > 0)
            addOperand(buffer.toString(), tokens);
        return tokens;
    }

    private static void addOperand(String operand, List<Token> tokens) {
        if (Character.isDigit(operand.charAt(0))) {
            double value = Double.parseDouble(operand);
            tokens.add(new Token(new Operand(value)));
        } else {
            tokens.add(new Token(new Operand(operand)));
        }
    }

    private class InvalidTokenException extends RuntimeException {
    }

}
