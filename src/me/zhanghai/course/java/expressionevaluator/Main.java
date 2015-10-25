/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.expressionevaluator;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Evaluator evaluator = new Evaluator(new EvaluatorListener());

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            evaluator.evaluate(input);
        }
    }

    private static class EvaluatorListener implements Evaluator.Listener {

        private DecimalFormat decimalFormat;

        public EvaluatorListener() {
            decimalFormat = new DecimalFormat();
            decimalFormat.setMaximumFractionDigits(10);
        }

        @Override
        public void onEvaluated(double result) {
            System.out.println(decimalFormat.format(result));
        }

        @Override
        public void onTokenizationFailed(int position) {
            System.err.println("Tokenization failed at position: " + position);
        }

        @Override
        public void onParseFailed(Token token, Parser.Failure failure) {
            System.err.println("Parse failed for token: " + token + ", because of " + failure);
        }

        @Override
        public void onFailed(String cause) {
            System.err.println("Evaluation failed: " + cause);
        }
    }
}
