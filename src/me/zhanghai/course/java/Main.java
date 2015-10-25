/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java;

import me.zhanghai.course.java.parser.IllegalInputException;
import me.zhanghai.course.java.parser.Tokenizer;
import me.zhanghai.course.java.parser.OperatorPrecedenceParser;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Tokenizer tokenizer = new Tokenizer(Arrays.<Tokenizer.Definition>asList(ExpressionToken.values()));
        OperatorPrecedenceParser parser = new OperatorPrecedenceParser(ExpressionNonterminal.RULE_SET,
                ExpressionNonterminal.START_SYMBOL_TYPE);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            try {
                List<MathToken> terminalList = tokenizer.lex(input);
                parser.parse(terminalList);
            } catch (IllegalInputException e) {
                e.printStackTrace();
            }
        }
    }
}
