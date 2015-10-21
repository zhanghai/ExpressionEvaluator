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

    /**
     * Create a new {@link Terminal}.
     *
     * @param type The identifying {@link Enum} for this type of terminal.
     * @param text The text from which this token is lexed.
     */
    public Terminal(Enum<?> type, String text) {
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
}
