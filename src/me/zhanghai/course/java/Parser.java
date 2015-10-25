/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java;

import java.util.LinkedList;

/**
 * A simple shunting-yard algorithm operator-precedence parser implementation, according to
 * <a href="https://en.wikipedia.org/wiki/Shunting-yard_algorithm">Shunting-yard algorithm - Wikipedia, the free encyclopedia</a>.
 *
 * @param <T> The type of token.
 */
public class Parser<T extends Parser.Token> {

    private boolean canceled;
    private boolean failed;
    private LinkedList<T> stack = new LinkedList<>();
    private Listener<T> listener;

    /**
     * Create a new {@link Parser}.
     *
     * @param listener Listener for output, parse completion or failure.
     */
    public Parser(Listener<T> listener) {
        this.listener = listener;
    }

    /**
     * Feed this parser with the next token.
     *
     * @param token The next token.
     */
    public void onNextToken(T token) {

        checkCanceledOrFailed();

        Definition definition = token.getDefinition();
        switch (definition.getType()) {

            case NUMBER:
                // Add it to the output queue.
                listener.onOutput(token);
                break;

            case FUNCTION:
                // Push it onto the stack.
                stack.push(token);
                break;

            case FUNCTION_ARGUMENT_SEPARATOR:
                // Until the token at the top of the stack is a left parenthesis, pop operators off
                // the stack onto the output queue.
                while (true) {
                    if (stack.isEmpty()) {
                        // If no left parentheses are encountered, either the separator was
                        // misplaced or parentheses were mismatched.
                        notifyFailed(token, Failure.MISPLACE_SEPARATOR_OR_MISMATCHED_PARENTHESIS);
                        break;
                    }
                    if (stack.peek().getDefinition().getType() == Type.LEFT_PARENTHESIS) {
                        break;
                    } else {
                        listener.onOutput(stack.pop());
                    }
                }
                break;

            case OPERATOR:
                // While there is an operator token, o2, at the top of the operator stack, and
                // either
                // - o1 is left-associative and its precedence is less than or equal to that of o2
                // - o1 is right associative, and has precedence less than that of o2
                // then pop o2 off the operator stack, onto the output queue.
                while (!stack.isEmpty()) {
                    Definition topDefinition = stack.peek().getDefinition();
                    if (topDefinition.getType() == Type.OPERATOR
                            && ((definition.getAssociativity() == Associativity.LEFT
                                    && definition.getPrecedence() <= topDefinition.getPrecedence())
                                || (definition.getAssociativity() == Associativity.RIGHT
                                    && definition.getPrecedence() < topDefinition.getPrecedence()))
                            ) {
                        listener.onOutput(stack.pop());
                    } else {
                        break;
                    }
                }
                // Push o1 onto the operator stack.
                stack.push(token);
                break;

            case LEFT_PARENTHESIS:
                // Push it onto the stack.
                stack.push(token);
                break;

            case RIGHT_PARENTHESIS:
                // Until the token at the top of the stack is a left parenthesis, pop operators off
                // the stack onto the output queue.
                while (true) {
                    if (stack.isEmpty()) {
                        // If the stack runs out without finding a left parenthesis, then there are
                        // mismatched parentheses.
                        notifyFailed(token, Failure.MISMATCHED_PARENTHESIS);
                        break;
                    }
                    if (stack.peek().getDefinition().getType() == Type.LEFT_PARENTHESIS) {
                        break;
                    } else {
                        listener.onOutput(stack.pop());
                        if (canceled) {
                            break;
                        }
                    }
                }
                if (canceled || failed) {
                    break;
                }
                // Pop the left parenthesis from the stack, but not onto the output queue.
                stack.pop();
                // If the token at the top of the stack is a function token, pop it onto the output
                // queue.
                if (!stack.isEmpty() && stack.peek().getDefinition().getType() == Type.FUNCTION) {
                    listener.onOutput(stack.pop());
                }
                break;
        }
    }

    /**
     * Tell this parser to complete the parse.
     */
    public void onNoMoreToken() {

        checkCanceledOrFailed();

        // While there are still operator tokens in the stack:
        while (!stack.isEmpty()) {
            // If the operator token on the top of the stack is a parenthesis, then there are
            // mismatched parentheses.
            Type type = stack.peek().getDefinition().getType();
            if (type == Type.LEFT_PARENTHESIS || type == Type.RIGHT_PARENTHESIS) {
                notifyFailed(null, Failure.MISMATCHED_PARENTHESIS);
                return;
            }
            // Pop the operator onto the output queue.
            listener.onOutput(stack.pop());
            if (canceled) {
                return;
            }
        }

        listener.onCompleted();
    }

    private void checkCanceledOrFailed() {
        if (canceled) {
            throw new IllegalStateException("Parse has already been canceled");
        }
        if (failed) {
            throw new IllegalStateException("Parse has already failed on a previous token");
        }
    }

    /**
     * Cancel the parse; listener won't be called any more until next parse.
     */
    public void cancel() {
        canceled = true;
    }

    private void notifyFailed(T token, Failure failure) {
        failed = true;
        listener.onFailed(token, failure);
    }

    /**
     * Reset this parser for next parse.
     */
    public void reset() {
        canceled = false;
        failed = false;
        stack.clear();
    }

    /**
     * Type of a token.
     */
    public enum Type {
        NUMBER,
        FUNCTION,
        FUNCTION_ARGUMENT_SEPARATOR,
        OPERATOR,
        LEFT_PARENTHESIS,
        RIGHT_PARENTHESIS
    }

    /**
     * Associativity of an operator.
     */
    public enum Associativity {
        LEFT,
        RIGHT
    }

    /**
     * Definition of a token.
     */
    public interface Definition {

        /**
         * Get the type of this token.
         *
         * @return The type of this token, one of {@link Type}.
         */
        Type getType();

        /**
         * Get the associativity of this operator. Will not be called if {@link #getType()} did not
         * return {@link Type#OPERATOR}.
         *
         * @return The associativity of this operator.
         */
        Associativity getAssociativity();

        /**
         * Get the precedence of this operator, larger value means higher precedence. Will not be
         * called if {@link #getType()} did not return {@link Type#OPERATOR}.
         *
         * @return The precedence of this operator.
         */
        int getPrecedence();
    }

    /**
     * A token that can provide its {@link Definition}.
     */
    public interface Token {

        /**
         * Get the definition of this token.
         *
         * @return The definition of this token.
         */
        Definition getDefinition();
    }

    public enum Failure {
        MISPLACE_SEPARATOR_OR_MISMATCHED_PARENTHESIS,
        MISMATCHED_PARENTHESIS
    }

    /**
     * Listener for output, parse completion or failure.
     */
    public interface Listener<T> {

        /**
         * Called upon output of a token.
         *
         * @param token The token.
         */
        void onOutput(T token);

        /**
         * Called when parse completed.
         */
        void onCompleted();

        /**
         * Called when parse failed.
         *
         * @param token The token which caused the failure, may be null if it is caused when
         *              completing the parse.
         * @param failure The failure occurred.
         */
        void onFailed(T token, Failure failure);
    }
}
