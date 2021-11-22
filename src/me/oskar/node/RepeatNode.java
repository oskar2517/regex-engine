package me.oskar.node;

public class RepeatNode extends Node {

    private final Node value;

    public RepeatNode(final Node value) {
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
