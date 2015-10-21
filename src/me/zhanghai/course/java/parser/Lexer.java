/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * A simple stateless lexer implementation.
 *
 * <p>This lexer returns all terminals at once, because it does not make sense to feed terminals
 * into our parse one by one.</p>
 */
public class Lexer {

    private Lexer() {}

    /**
     * Return a list of terminals derived from the input string.
     *
     * @param input The input string.
     * @param definitionList Definitions of tokens.
     * @return A list of terminals.
     * @throws IllegalInputException If any input cannot be lexed.
     */
    public static List<Terminal> lex(String input, List<TerminalDefinition> definitionList)
            throws IllegalInputException {

        int position = 0;
        List<Terminal> terminalList = new ArrayList<>();

        while (position < input.length()) {
            boolean matched = false;
            for (TerminalDefinition terminalDefinition : definitionList) {
                Matcher matcher = terminalDefinition.getMatcher(input)
                        .region(position, input.length());
                if (matcher.lookingAt()) {
                    String text = matcher.group();
                    terminalList.add(new Terminal(terminalDefinition.getType(), text));
                    position += text.length();
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                throw new IllegalInputException("Unable to lex input \"" + input + "\" at position "
                        + position);
            }
        }

        return terminalList;
    }
}
