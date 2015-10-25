/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple shunting-yard algorithm operator-precedence parser implementation.
 */
public class OperatorPrecedenceParser {

    private Map<Enum<?>, List<List<Enum<?>>>> ruleSet;
    private Enum<?> startSymbolType;

    /**
     * Create a new {@link OperatorPrecedenceParser}.
     *
     * @param ruleSet The rules for parsing non-terminals.
     * @param startSymbolType The symbol type to start with.
     */
    public OperatorPrecedenceParser(Map<Enum<?>, List<List<Enum<?>>>> ruleSet, Enum<?> startSymbolType) {
        this.ruleSet = ruleSet;
        this.startSymbolType = startSymbolType;
    }

    /**
     * Return a parse tree derived from the input terminals.
     *
     * @param terminalList The list of terminals.
     * @return A parse tree.
     * @throws IllegalInputException If any input cannot be parsed.
     */
    public ParseTreeNode parse(List<Terminal> terminalList) throws IllegalInputException {
        return parseInternal(terminalList, 0, startSymbolType, true).node;
    }

    private ParseResult parseInternal(List<Terminal> terminalList, int position,
                                      Enum<?> targetSymbolType, boolean shouldConsumeAll)
            throws IllegalInputException {

        // Check end of input.
        if (position >= terminalList.size()) {
                throw new IllegalInputException("No more terminal when trying to parse symbol type "
                    + targetSymbolType);
        }

        // Test for a direct match.
        Terminal firstTerminal = terminalList.get(position);
        if (firstTerminal.getType() == targetSymbolType) {
            if (shouldConsumeAll && position < terminalList.size() - 1) {
                throw new IllegalInputException(
                        "Cannot consume all the input because target symbol type is terminal "
                                + targetSymbolType + " at position " + position);
            }
            return new ParseResult(new ParseTreeNode(firstTerminal), position + 1);
        }

        // Parse by rules.
        List<List<Enum<?>>> ruleList = ruleSet.get(targetSymbolType);
        if (ruleList != null) {
            // Allocate only one ArrayList before the loop.
            ArrayList<ParseTreeNode> children = new ArrayList<>();
            for (List<Enum<?>> rule : ruleList) {
                boolean matched = true;
                children.clear();
                int nextPosition = position;
                for (int i = 0; i < rule.size(); ++i) {
                    try {
                        ParseResult result = parseInternal(terminalList, nextPosition,
                                rule.get(i), shouldConsumeAll && i == rule.size() - 1);
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
        }

        throw new IllegalInputException("No applicable rule to parse symbol type "
                + targetSymbolType + " at position " + position);
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