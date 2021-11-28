package me.oskar.regex.node;

public class OptionalNode extends Node {

    private final Node value;

    public OptionalNode(final Node value) {
        this.value = value;
    }

    public Node getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(OPT %s)", value);
    }
}
