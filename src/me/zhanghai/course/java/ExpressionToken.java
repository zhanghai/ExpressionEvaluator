/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java;

import me.zhanghai.course.java.parser.TokenDefinition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ExpressionToken implements TokenDefinition {

    NUMBER("\\d+"),
    BINARY_OPERATOR("(\\+|-|\\*|/)"),
    LEFT_PARENTHESIS("\\("),
    RIGHT_PARENTHESIS("\\)");

    private Matcher matcher;

    ExpressionToken(String regex) {
        matcher = Pattern.compile(regex).matcher("");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExpressionToken getIdentifier() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Matcher getMatcher(CharSequence input) {
        return matcher.reset(input);
    }
}
