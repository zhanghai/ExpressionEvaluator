/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.parser;

import java.util.List;
import java.util.Map;

/**
 * A simple LALR(n) parser implementation.
 */
public class Parser {

    private List<Terminal> terminals;
    private Map<Enum<?>, List<Enum<?>>> rules;
    private Enum<?> startSymbol;

    public Parser(List<Terminal> terminals, Map<Enum<?>, List<Enum<?>>> rules, Enum<?> startSymbol) {
        this.terminals = terminals;
        this.rules = rules;
        this.startSymbol = startSymbol;
    }

    /**
     * Return a new {@link Parser} on a list of terminals.
     *
     * @param terminals The list of terminals.
     * @param definitions Definitions of possible terminals.
     * @return The new {@link Parser}.
     */
    public static Parser parse(List<Terminal> terminals, List<TerminalDefinition<T>> definitions) {
        return new Parser(terminals, definitions);
    }
}
