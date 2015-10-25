/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.expressionevaluator;

/**
 * Token.
 */
public class Token implements Parser.Token {

    private TokenDefinition definition;
    private String text;

    public Token(TokenDefinition definition, String text) {
        this.definition = definition;
        this.text = text;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TokenDefinition getDefinition() {
        return definition;
    }

    /**
     * Get the text from which this token is tokenized.
     *
     * @return The text from which this token is tokenized.
     */
    public String getText() {
        return text;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Token{" +
                "definition=" + definition +
                ", text='" + text + '\'' +
                '}';
    }
}
