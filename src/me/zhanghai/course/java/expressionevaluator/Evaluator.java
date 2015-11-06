/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.expressionevaluator;

import java.util.LinkedList;

/**
 * Mathematical expression evaluator.
 */
public class Evaluator {

    private Tokenizer<TokenDefinition> tokenizer = new Tokenizer<>(TokenDefinition.values(),
            new TokenizerListener());
    private Parser<Token> parser = new Parser<>(new ParserListener());

    private LinkedList<Double> stack = new LinkedList<>();

    private Listener listener;

    /**
     * Create a new {@link Evaluator}
     *
     * @param listener Listener for evaluation result or failure.
     */
    public Evaluator(Listener listener) {
        this.listener = listener;
    }

    /**
     * Evaluate an mathematical expression.
     *
     * @param expression The mathematical expression.
     */
    public void evaluate(String expression) {
        reset();
        tokenizer.tokenize(expression);
    }

    private void onParserOutput(Token token) {

        TokenDefinition definition = token.getDefinition();
        switch (definition.getType()) {

            case NUMBER:
                try {
                    stack.push(Double.parseDouble(token.getText()));
                } catch (NumberFormatException e) {
                    notifyFailed("cannot parse number: " + e.getMessage());
                }
                break;

            // Not in spec.
//            case FUNCTION:
//                break;

            case OPERATOR:
                if (stack.size() < 2) {
                    notifyFailed("Insufficient operand, expected 2: " + stack);
                    break;
                }
                double right = stack.pop();
                double left = stack.pop();
                switch (definition) {
                    case ADD:
                        stack.push(left + right);
                        break;
                    case SUBTRACT:
                        stack.push(left - right);
                        break;
                    case MULTIPLY:
                        stack.push(left * right);
                        break;
                    case DIVIDE:
                        stack.push(left / right);
                        break;
                    case MODULO:
                        stack.push(left % right);
                        break;
                    case EXPONENT:
                        stack.push(Math.pow(left, right));
                        break;
                    default:
                        notifyFailed("Unexpected operator: " + definition);
                }
                break;

            default:
                notifyFailed("Unexpected type: " + definition.getType());
        }
    }

    private void onParserCompleted() {
        if (stack.size() == 1) {
            listener.onEvaluated(stack.pop());
        } else {
            notifyFailed("Malformed expression, stack size is not 1 when parser completed: "
                    + stack);
        }
    }

    private void notifyFailed(String cause) {
        listener.onFailed(cause);
        cancel();
    }

    private void cancel() {
        tokenizer.cancel();
        parser.cancel();
    }

    private void reset() {
        tokenizer.reset();
        parser.reset();
        stack.clear();
    }

    /**
     * Listener for evaluation completion or failure.
     */
    public interface Listener {

        /**
         * Called when evaluation completed.
         *
         * @param result Evaluation result.
         */
        void onEvaluated(double result);

        /**
         * Called when tokenziation failed.
         *
         * @param position The position where the failure occurred.
         */
        void onTokenizationFailed(int position);

        /**
         * Called when parse failed.
         *
         * @param token The token which caused the failure, may be null if it is caused when
         *              completing the parse.
         * @param failure The failure occurred.
         */
        void onParseFailed(Token token, Parser.Failure failure);

        /**
         * Called when evaluation (unexpectedly) failed.
         *
         * @param cause The cause of this failure.
         */
        void onFailed(String cause);
    }

    private class TokenizerListener implements Tokenizer.Listener<TokenDefinition> {

        @Override
        public void onToken(TokenDefinition definition, String text) {
            parser.onNextToken(new Token(definition, text));
        }

        @Override
        public void onCompleted() {
            parser.onNoMoreToken();
        }

        @Override
        public void onFailed(int position) {
            listener.onTokenizationFailed(position);
            cancel();
        }
    }

    private class ParserListener implements Parser.Listener<Token> {

        @Override
        public void onOutput(Token token) {
            onParserOutput(token);
        }

        @Override
        public void onCompleted() {
            onParserCompleted();
        }

        @Override
        public void onFailed(Token token, Parser.Failure failure) {
            listener.onParseFailed(token, failure);
            cancel();
        }
    }
}
