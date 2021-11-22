package me.oskar.thompson;

import me.oskar.node.*;
import me.oskar.parser.Pattern;

import java.util.ArrayList;
import java.util.HashSet;

public class PatternConstructor {

    private final Pattern pattern;
    private final State startState = new State();

    public PatternConstructor(final Pattern pattern) {
        this.pattern = pattern;
    }

    public void constructNonDeterministic() {
        var endState = construct(pattern.getAst(), startState);
        endState.setEndState(true);
    }

    public void transformDeterministic() {
        final var epsilonSpans = new ArrayList<HashSet<State>>();
        epsilonSpans.add(startState.epsilonSpan());

        for (var i = 0; i < Math.pow(2, State.instanceCount); i++) {
            var current = epsilonSpans.get(i);

            for (String terminal : pattern.getTerminals()) {
                final var moveStates = new HashSet<State>();
                for (State s : current) {
                    moveStates.addAll(s.move(terminal));
                }

                final var epsilonMoveStates = new HashSet<State>();
                for (State s : moveStates) {
                    epsilonMoveStates.addAll(s.epsilonSpan());
                }

                epsilonSpans.add(epsilonMoveStates);

                if (!current.isEmpty()) {
                    System.out.printf("%s -> %s (%s)\n", current, epsilonMoveStates, terminal);
                }
            }
        }
    }

    private State construct(final LiteralNode node, final State targetState) {
        final var s1 = new State();
        final var s2 = new State();

        s1.addTerminalEdge(node.getValue(), s2);

        targetState.addEpsilonEdge(s1);

        return s2;
    }

    private State construct(final GroupNode node, final State targetState) {
        return construct(node.getValue(), targetState);
    }

    private State construct(final ConjunctionNode node, final State targetState) {
        final var s1 = new State();
        final var s2 = construct(node.getLeft(), s1);
        final var s3 = construct(node.getRight(), s2);
        final var s4 = new State();
        s3.addEpsilonEdge(s4);

        targetState.addEpsilonEdge(s1);

        return s4;
    }

    private State construct(final RepeatNode node, final State targetState) {
        final var s1 = new State();
        final var s2 = new State();
        final var s3 = construct(node.getValue(), s2);
        final var s4 = new State();
        final var s5 = new State();

        s1.addEpsilonEdge(s2);
        s2.addEpsilonEdge(s4);
        s4.addEpsilonEdge(s2);
        s3.addEpsilonEdge(s4);
        s4.addEpsilonEdge(s5);

        targetState.addEpsilonEdge(s1);

        return s5;
    }

    private State construct(final DisjunctionNode node, final State targetState) {
        final var s1 = new State();
        final var s2 = construct(node.getLeft(), s1);
        final var s3 = construct(node.getRight(), s1);
        final var s4 = new State();

        s2.addEpsilonEdge(s4);
        s3.addEpsilonEdge(s4);

        targetState.addEpsilonEdge(s1);

        return s4;
    }

    private State construct(final Node node, final State targetState) {
        if (node instanceof LiteralNode n) {
            return construct(n, targetState);
        } else if (node instanceof GroupNode n) {
            return construct(n, targetState);
        } else if (node instanceof RepeatNode n) {
            return construct(n, targetState);
        } else if (node instanceof DisjunctionNode n) {
            return construct(n, targetState);
        } else if (node instanceof ConjunctionNode n) {
            return construct(n, targetState);
        }

        throw new IllegalStateException(String.format("Unhandled node: %s", node));
    }
}
