package me.oskar.regex.engine.node;

public class RepeatPlusNode extends Node {

    private final Node value;

    public RepeatPlusNode(final Node value) {
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
