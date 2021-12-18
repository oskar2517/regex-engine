package me.oskar.regex.engine.node;

public class GroupNode extends Node {

    private final Node value;

    public GroupNode(final Node value) {
        this.value = value;
    }

    public Node getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(GROUP %s)", value);
    }
}
