/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple LALR(n) parser implementation.
 */
public class Parser {

    private Parser() {}

    /**
     * Return a parse tree derived from the input terminals.
     *
     * @param terminals The list of terminals.
     * @param ruleSet The rules for parsing; terminals has an empty rule list.
     * @param startSymbolType The symbol type to start with.
     * @return A parse tree.
     * @throws IllegalInputException If any input cannot be parsed.
     */
    public static ParseTreeNode parse(List<Terminal> terminals,
                                      Map<Enum<?>, List<List<Enum<?>>>> ruleSet,
                                      Enum<?> startSymbolType) throws IllegalInputException {

        ParseResult result = parseInternal(terminals, 0, ruleSet, startSymbolType);
        if (result.position != terminals.size()) {
            throw new IllegalInputException("Not all terminals are consumed, "
                    + (terminals.size() - result.position) + " remaining.");
        }

        return result.node;
    }

    private static ParseResult parseInternal(List<Terminal> terminalList, int position,
                                               Map<Enum<?>, List<List<Enum<?>>>> ruleSet,
                                               Enum<?> targetSymbolType)
            throws IllegalInputException {

        // Check end of input.
        if (position >= terminalList.size()) {
                throw new IllegalInputException("No more terminal when trying to parse symbol type "
                    + targetSymbolType);
        }

        // Test for a direct match.
        Terminal firstTerminal = terminalList.get(position);
        if (firstTerminal.getType() == targetSymbolType) {
            return new ParseResult(new ParseTreeNode(firstTerminal), position + 1);
        }

        // Parse by rules.
        List<List<Enum<?>>> ruleList = ruleSet.get(targetSymbolType);
        // Allocate only one ArrayList before the loop.
        ArrayList<ParseTreeNode> children = new ArrayList<>();
        for (List<Enum<?>> rule : ruleList) {
            boolean matched = true;
            children.clear();
            int nextPosition = position;
            for (Enum<?> ruleTargetSymbolType : rule) {
                try {
                    ParseResult result = parseInternal(terminalList, nextPosition, ruleSet,
                            ruleTargetSymbolType);
                    children.add(result.node);
                    nextPosition = result.position;
                } catch (IllegalInputException e) {
                    matched = false;
                    break;
                }
            }
            if (matched) {
                // Successful match.
                return new ParseResult(new ParseTreeNode(
                        new Nonterminal(targetSymbolType), children), nextPosition);
            }
        }

        throw new IllegalInputException("No applicable rule to parse symbol type "
                + targetSymbolType + ", at position " + position);
    }

    private static class ParseResult {

        public ParseTreeNode node;
        public int position;

        public ParseResult(ParseTreeNode node, int position) {
            this.node = node;
            this.position = position;
        }
    }
}
