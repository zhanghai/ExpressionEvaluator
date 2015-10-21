/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java;

import me.zhanghai.course.java.parser.IllegalInputException;
import me.zhanghai.course.java.parser.Lexer;
import me.zhanghai.course.java.parser.Parser;
import me.zhanghai.course.java.parser.Terminal;
import me.zhanghai.course.java.parser.TerminalDefinition;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Lexer lexer = new Lexer(Arrays.<TerminalDefinition>asList(ExpressionTerminal.values()));
        Parser parser = new Parser(ExpressionNonterminal.RULE_SET,
                ExpressionNonterminal.START_SYMBOL_TYPE);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            try {
                List<Terminal> terminalList = lexer.lex(input);
                parser.parse(terminalList);
            } catch (IllegalInputException e) {
                e.printStackTrace();
            }
        }
    }
}
