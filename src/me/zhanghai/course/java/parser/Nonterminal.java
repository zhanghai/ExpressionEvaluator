/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.parser;

/**
 * A nonterminal symbol.
 */
public class Nonterminal extends Symbol {

    Nonterminal(Enum<?> type) {
        super(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Nonterminal{} " + super.toString();
    }
}
