/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.parser;

/**
 * A terminal symbol.
 */
public class Terminal extends Symbol {

    private String text;

    Terminal(Enum<?> type, String text) {
        super(type);
        this.text = text;
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
        return "Terminal{" +
                "text='" + text + '\'' +
                "} " + super.toString();
    }
}
