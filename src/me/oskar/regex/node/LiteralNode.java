package me.oskar.regex.node;

public class LiteralNode extends Node {

    private final char value;

    public LiteralNode(final char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(LIT %s)", value);
    }
}
