package com.ilya.de.math.function;

import com.ilya.de.math.rpn.*;
import lombok.NoArgsConstructor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;

public class Function2Generator {

    private final Tokenizer tokenizer;
    private final ShuntingYard shuntingYard;
    private final boolean debug;
    private static int classCounter = 0;
    private static final FunctionClassLoader funcLoader = new FunctionClassLoader();

    public static Function2 gen(String func) {
        return new Function2Generator().generate(func);
    }

    public Function2Generator() {
        this(false);
    }

    @SuppressWarnings("SameParameterValue")
    private Function2Generator(boolean debug) {
        this.debug = debug;
        tokenizer = new Tokenizer();
        shuntingYard = new ShuntingYard();
    }

    public Function2 generate(String str) {
        List<Token> tokens = tokenizer.parse(str);
        List<Token> rpnTokens = shuntingYard.convertToRPN(tokens);
        return generate(rpnTokens);
    }

    private void log(Object... args) {
        if (debug) {
            StringBuilder sb = new StringBuilder();
            for (Object obj : args) {
                sb.append(obj);
            }
            System.out.println(sb.toString());
        }
    }

    @SuppressWarnings("unchecked")
    private Function2 generate(List<Token> rpn) {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        String className = "com.ilya.de.math.function.Function2_gen" + (classCounter++) + "";
        String parentName = "java/lang/Object";
        String interfaceName = "com.ilya.de.math.function.Function2".replace('.', '/');
        String slashedClassName = className.replace('.', '/');
        writer.visit(V1_8, ACC_PUBLIC, "" + slashedClassName + "", null,
                parentName, new String[]{interfaceName});
        generateConstructor(writer, parentName);
        generateFunc(writer, rpn);
        Class<Function2> classInstance = (Class<Function2>) funcLoader.define(
                className, writer.toByteArray());
        try {
            return classInstance.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void generateConstructor(ClassWriter writer, String parentName) {
        MethodVisitor visitor = writer.visitMethod(ACC_PUBLIC,
                "<init>", "()V", null, null);
        visitor.visitCode();
        visitor.visitVarInsn(ALOAD, 0);
        visitor.visitMethodInsn(INVOKESPECIAL, parentName,
                "<init>", "()V", false);
        visitor.visitInsn(RETURN);
        visitor.visitMaxs(1, 1);
        visitor.visitEnd();
    }

    private void generateFunc(ClassWriter writer, List<Token> rpn) {
        MethodVisitor method = writer.visitMethod(ACC_FINAL | ACC_PUBLIC,
                "func", "(DD)D", null, null);
        int xArgIndex = 1;
        int yArgIndex = 3;
        int currentStackSize = 0;
        int maxStackSize = 0;
        method.visitCode();
        for (Token token : rpn) {
            if (!token.isOperator()) {
                currentStackSize++;
                maxStackSize = maxStackSize < currentStackSize ? currentStackSize : maxStackSize;

                if (token.isFunction()) {
                    method.visitMethodInsn(INVOKESTATIC, "java/lang/Math",
                            token.getFunction().getName(), token.getFunction().isBinary() ? "(DD)D" : "(D)D",
                            false);
                    log("invoking function ", token.getFunction().getName(), " on stack");
                    continue;
                }
                Operand operand = token.getOperand();
                if (operand.isVariable()) {
                    log("pushing var +", operand.getVariable(), " on stack");
                    if (operand.getVariable().equals("x")) {
                        method.visitVarInsn(DLOAD, xArgIndex);
                        continue;
                    }
                    if (operand.getVariable().equals("y")) {
                        method.visitVarInsn(DLOAD, yArgIndex);
                    }
                } else {
                    method.visitLdcInsn(operand.getValue());
                    log("pushing constant +", operand.getValue(), " on stack");
                }

            } else {
                Operator operator = token.getOperator();
                log("executing operator ", operator.toString(), " on stack");
                if (operator.getOpCode() == Operator.OPERATOR_ADDITION) {
                    method.visitInsn(DADD);
                    continue;
                }
                if (operator.getOpCode() == Operator.OPERATOR_SUBTRACTION) {
                    method.visitInsn(DSUB);
                    continue;
                }
                if (operator.getOpCode() == Operator.OPERATOR_MULTIPLICATION) {
                    method.visitInsn(DMUL);
                    continue;
                }
                if (operator.getOpCode() == Operator.OPERATOR_DIVISION) {
                    method.visitInsn(DDIV);
                }
                if (operator.getOpCode() == Operator.OPERATOR_POWER) {
                    method.visitMethodInsn(INVOKESTATIC,
                            "java/lang/Math", "pow", "(DD)D", false);
                }
            }
        }
        if (debug) {
            method.visitMethodInsn(INVOKESTATIC,
                    "com/ilya/de/math/function/Function2Generator$Function2Impl",
                    "breakpoint", "()V", false);
        }
        method.visitInsn(DRETURN);
        method.visitMaxs(maxStackSize, yArgIndex + 1);
        method.visitEnd();
    }


    @SuppressWarnings("unused")
    @NoArgsConstructor
    public static abstract class Function2Impl implements Function2 {

        public static void breakpoint() {
            System.currentTimeMillis();
        }

        public abstract double func(double x, double y);

    }

}
