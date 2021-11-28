package me.oskar.regex.node;

public class DisjunctionNode extends Node {

    private final Node left;
    private final Node right;

    public DisjunctionNode(final Node left, final Node right) {
        this.left = left;
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    @Override
    public String toString() {
        return String.format("(DIS %s %s)", left, right);
    }
}
