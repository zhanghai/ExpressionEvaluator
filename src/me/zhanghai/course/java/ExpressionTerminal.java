/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java;

import me.zhanghai.course.java.parser.TerminalDefinition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ExpressionTerminal implements TerminalDefinition {

    NUMBER("\\d+"),
    BINARY_OPERATOR("(\\+|-|\\*|/)"),
    LEFT_PARENTHESIS("\\("),
    RIGHT_PARENTHESIS("\\)");

    private Matcher matcher;

    ExpressionTerminal(String regex) {
        matcher = Pattern.compile(regex).matcher("");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExpressionTerminal getType() {
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
