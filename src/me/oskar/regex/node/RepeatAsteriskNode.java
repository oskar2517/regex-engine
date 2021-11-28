package me.oskar.regex.node;

public class RepeatAsteriskNode extends Node {

    private final Node value;

    public RepeatAsteriskNode(final Node value) {
        this.value = value;
    }

    public Node getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(REP %s)", value.toString());
    }
}
