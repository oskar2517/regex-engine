package me.oskar.node;

public class LiteralNode extends Node {

    private final String value;

    public LiteralNode(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(LIT %s)", value);
    }
}
