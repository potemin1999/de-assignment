package com.ilya.de.math.rpn;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ShuntingYard {

    public List<Token> convertToRPN(List<Token> input) {
        List<Token> stack = new ArrayList<>(input.size());
        List<Token> output = new PrintableLinkedList<Token>();
        for (Token token : input) {
            if (token.isFunction()) {
                stack.add(token);
                continue;
            }
            if (!token.isOperator()) {
                output.add(token);
                continue;
            }
            if (token.getOperator().getOpCode() == Operator.OPERATOR_PAREN_OPEN) {
                stack.add(token);
                continue;
            }
            if (token.getOperator().getOpCode() == Operator.OPERATOR_PAREN_CLOSE) {
                while (!stack.isEmpty() && (stack.get(stack.size() - 1).isFunction() ||
                        stack.get(stack.size() - 1).getOperator().getOpCode() != Operator.OPERATOR_PAREN_OPEN)) {
                    output.add(stack.remove(stack.size() - 1));
                }
                stack.remove(stack.size() - 1);
                continue;
            }
            //token is an operator or function
            Token top = stack.isEmpty() ? null : stack.get(stack.size() - 1);
            while (top != null
                    && (top.isFunction() || top.getOperator().getPrecedence() > token.getOperator().getPrecedence() ||
                    ((top.getOperator().getPrecedence() == token.getOperator().getPrecedence())
                            && !top.getOperator().isRightAssociative()))
                    && (top.isFunction() || top.getOperator().getOpCode() != Operator.OPERATOR_PAREN_OPEN)) {
                output.add(stack.remove(stack.size() - 1));
                top = stack.isEmpty() ? null : stack.get(stack.size() - 1);
            }
            stack.add(token);
        }
        while (!stack.isEmpty()) {
            output.add(stack.remove(stack.size() - 1));
        }
        return output;
    }

}
