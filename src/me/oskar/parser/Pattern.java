package me.oskar.parser;

import me.oskar.node.Node;

import java.util.HashSet;

public class Pattern {

    private final Node ast;
    private final HashSet<String> terminals;

    public Pattern(final Node ast, final HashSet<String> terminals) {
        this.ast = ast;
        this.terminals = terminals;
    }

    public Node getAst() {
        return ast;
    }

    public HashSet<String> getTerminals() {
        return terminals;
    }
}
