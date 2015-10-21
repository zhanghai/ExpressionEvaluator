/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.course.java.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * A node of a parse tree.
 */
public class ParseTreeNode {

    private Symbol symbol;
    private List<ParseTreeNode> children = new ArrayList<>();

    ParseTreeNode(Symbol symbol, List<ParseTreeNode> children) {
        this.symbol = symbol;
        this.children = children != null ? children : new ArrayList<ParseTreeNode>();
    }

    ParseTreeNode(Symbol symbol) {
        this(symbol, null);
    }

    /**
     * Get the symbol of this node.
     *
     * @return The symbol of this node.
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * Get children of this node.
     *
     * @return Children of this node.
     */
    public List<ParseTreeNode> getChildren() {
        return children;
    }
}
