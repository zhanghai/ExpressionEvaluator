/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.parser;

/**
 * Base class for {@link Terminal} and {@link Nonterminal}.
 */
public abstract class Symbol {

    private Enum<?> type;

    /**
     * Create a new {@link Symbol}.
     *
     * @param type The identifying {@link Enum} for this type of symbol.
     */
    public Symbol(Enum<?> type) {
        this.type = type;
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
