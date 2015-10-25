/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.parser;

/**
 * A nonterminal symbol.
 */
public class Nonterminal {

    private Enum<?> type;

    Nonterminal(Enum<?> type) {
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Nonterminal{} " + toString();
    }

    /**
     * Get the identifying {@link Enum} for this type of symbol.
     *
     * @return The identifying {@link Enum} for this type of symbol.
     */
    public Enum<?> getType() {
        return type;
    }
}
