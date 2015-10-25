/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.parser;

import java.util.List;
import java.util.regex.Matcher;

/**
 * A simple stateless tokenizer implementation.
 */
public class Tokenizer {

    private String input;
    private List<TokenDefinition> definitionList;

    private int position;

    /**
     * Create a new {@link Tokenizer}.
     *
     * @param input The input string.
     * @param definitionList The definitions of tokens.
     */
    public Tokenizer(String input, List<TokenDefinition> definitionList) {
        this.input = input;
        this.definitionList = definitionList;
    }

    /**
     * Return the next token from the input string. Throws an {@link IllegalInputException} if
     * remaining input cannot be tokenized.
     *
     * @return The next token.
     * @throws IllegalInputException If remaining input cannot be tokenized.
     */
    public Terminal next() throws IllegalInputException {

        if (position == input.length()) {
            return null;
        }

        for (TokenDefinition tokenDefinition : definitionList) {
            Matcher matcher = tokenDefinition.getMatcher(input).region(position, input.length());
            if (matcher.lookingAt()) {
                String text = matcher.group();
                position += text.length();
                return new Terminal(tokenDefinition.getIdentifier(), text);
            }
        }

        throw new IllegalInputException("Unable to tokenize input \"" + input + "\" at position "
                + position);
    }
}
