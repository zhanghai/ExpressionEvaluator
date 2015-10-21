/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.parser;

import java.util.List;
import java.util.regex.Matcher;

/**
 * A simple stateless lexer implementation.
 *
 * <p>This lexer returns all terminals at once, because it does not make sense to feed terminals
 * into our parse one by one.</p>
 */
public class Lexer {

    private String input;
    private List<TerminalDefinition> definitions;

    private int position;

    private Lexer(String input, List<TerminalDefinition> definitions) {
        this.input = input;
        this.definitions = definitions;
    }

    /**
     * Return a new {@link Lexer} on an input string.
     *
     * @param input The input string.
     * @param definitions Definitions of possible tokens.
     * @return The new {@link Lexer}.
     */
    public static Lexer lex(String input, List<TerminalDefinition> definitions) {
        return new Lexer(input, definitions);
    }

    /**
     * Return the next token from the input string. Throws an {@link IllegalInputException} if
     * remaining input cannot be lexed.
     *
     * @return The next token.
     * @throws IllegalInputException If remaining input cannot be lexed.
     */
    public Terminal next() throws IllegalInputException {

        if (position == input.length()) {
            return null;
        }

        for (TerminalDefinition terminalDefinition : definitions) {
            Matcher matcher = terminalDefinition.getMatcher(input).region(position, input.length());
            if (matcher.lookingAt()) {
                String text = matcher.group();
                position += text.length();
                return new Terminal(terminalDefinition.getType(), text);
            }
        }

        throw new IllegalInputException("Unable to lex input \"" + input + "\" at position "
                + position);
    }
}
