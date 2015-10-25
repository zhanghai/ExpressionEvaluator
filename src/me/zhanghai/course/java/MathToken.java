/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java;

/**
 * A token for mathematical expression.
 */
public class MathToken {

    private Enum<?> type;
    private String text;

    public MathToken(Enum<?> type, String text) {
        this.type = type;
        this.text = text;
    }

    /**
     * Get the identifier for this type of symbol.
     *
     * @return The identifier for this type of symbol.
     */
    public Enum<?> getType() {
        return type;
    }

    /**
     * Get the text from which this token is lexed.
     *
     * @return The text from which this token is lexed.
     */
    public String getText() {
        return text;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "MathToken{" +
                "type=" + type +
                ", text='" + text + '\'' +
                '}';
    }
}
