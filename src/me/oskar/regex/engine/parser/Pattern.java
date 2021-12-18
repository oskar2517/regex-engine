package me.oskar.regex.engine.parser;

import me.oskar.regex.engine.node.Node;

import java.util.HashSet;

public class Pattern {

    private final Node ast;
    private final HashSet<Character> terminals;

    public Pattern(final Node ast, final HashSet<Character> terminals) {
        this.ast = ast;
        this.terminals = terminals;
    }

    public Node getAst() {
        return ast;
    }

    public HashSet<Character> getTerminals() {
        return terminals;
    }
}
