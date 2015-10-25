/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Definitions for token.
 */
public enum TokenDefinition implements Tokenizer.Definition, Parser.Definition {

    NUMBER("\\d+", Parser.Type.NUMBER),
    ADD("\\+", Parser.Type.OPERATOR, Parser.Associativity.LEFT, 0),
    SUBTRACT("-", Parser.Type.OPERATOR, Parser.Associativity.LEFT, 0),
    MULTIPLY("\\*", Parser.Type.OPERATOR, Parser.Associativity.LEFT, 1),
    DIVIDE("/", Parser.Type.OPERATOR, Parser.Associativity.LEFT, 1),
    //EXPONENT("\\^", Type.OPERATOR, Associativity.RIGHT, 2),
    LEFT_PARENTHESIS("\\(", Parser.Type.LEFT_PARENTHESIS),
    RIGHT_PARENTHESIS("\\)", Parser.Type.RIGHT_PARENTHESIS);

    private Matcher matcher;
    private Parser.Type type;
    private Parser.Associativity associativity;
    private int precedence;

    TokenDefinition(String regex, Parser.Type type) {
        matcher = Pattern.compile(regex).matcher("");
        this.type = type;
    }

    TokenDefinition(String regex, Parser.Type type, Parser.Associativity associativity,
                    int precedence) {
        this(regex, type);

        this.associativity = associativity;
        this.precedence = precedence;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Matcher getMatcher(CharSequence input) {
        return matcher.reset(input);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Parser.Type getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Parser.Associativity getAssociativity() {
        return associativity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPrecedence() {
        return precedence;
    }
}
