package me.oskar.regex.engine.thompson;

import me.oskar.regex.engine.node.*;
import me.oskar.regex.engine.parser.Pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PatternConstructor {

    private final Pattern pattern;
    private final State startState = new State();
    private State endState;

    public PatternConstructor(final Pattern pattern) {
        this.pattern = pattern;
    }

    public State constructNonDeterministic() {
        endState = construct(pattern.getAst(), startState);
        endState.setEndState(true);

        return startState;
    }

    public State transformDeterministic() {
        final var epsilonSpans = new ArrayList<HashSet<State>>();
        epsilonSpans.add(startState.epsilonSpan());

        final var deterministicStates = new HashMap<HashSet<State>, State>();
        final var startState = new State();

        for (var i = 0; i < epsilonSpans.size(); i++) {
            var current = epsilonSpans.get(i);

            if (!deterministicStates.containsKey(current)) {
                State s = deterministicStates.isEmpty() ? startState : new State();

                if (current.contains(endState)) {
                    s.setEndState(true);
                }

                deterministicStates.put(current, s);
            }

            for (final var terminal : pattern.getTerminals()) {
                final var moveStates = new HashSet<State>();
                for (final var s : current) {
                    moveStates.addAll(s.move(terminal));
                }

                final var epsilonMoveStates = new HashSet<State>();
                for (final var s : moveStates) {
                    epsilonMoveStates.addAll(s.epsilonSpan());
                }

                if (!epsilonSpans.contains(epsilonMoveStates)) {
                    epsilonSpans.add(epsilonMoveStates);
                }

                if (!deterministicStates.containsKey(epsilonMoveStates)) {
                    final var s = new State();
                    if (epsilonMoveStates.contains(endState)) {
                        s.setEndState(true);
                    }
                    deterministicStates.put(epsilonMoveStates, s);
                }

                if (!epsilonMoveStates.isEmpty()) {
                    final var originState = deterministicStates.get(current);
                    final var targetState = deterministicStates.get(epsilonMoveStates);
                    originState.addTerminalEdge(terminal, targetState);
                }
            }
        }

        return startState;
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

    private State construct(final RepeatAsteriskNode node, final State targetState) {
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

    private State construct(final RepeatPlusNode node, final State targetState) {
        final var s1 = new State();
        final var s2 = new State();
        final var s3 = construct(node.getValue(), s2);
        final var s4 = new State();
        final var s5 = new State();

        s1.addEpsilonEdge(s2);
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

    private State construct(final OptionalNode node, final State targetState) {
        final var s1 = new State();
        final var s2 = new State();
        final var s3 = construct(node.getValue(), s2);
        final var s4 = new State();
        final var s5 = new State();

        s1.addEpsilonEdge(s2);
        s2.addEpsilonEdge(s4);
        s3.addEpsilonEdge(s4);
        s4.addEpsilonEdge(s5);

        targetState.addEpsilonEdge(s1);

        return s5;
    }

    private State construct(final Node node, final State targetState) {
        if (node instanceof LiteralNode n) {
            return construct(n, targetState);
        } else if (node instanceof GroupNode n) {
            return construct(n, targetState);
        } else if (node instanceof RepeatAsteriskNode n) {
            return construct(n, targetState);
        } else if (node instanceof RepeatPlusNode n) {
            return construct(n, targetState);
        } else if (node instanceof DisjunctionNode n) {
            return construct(n, targetState);
        } else if (node instanceof ConjunctionNode n) {
            return construct(n, targetState);
        } else if (node instanceof OptionalNode n) {
            return construct(n, targetState);
        }

        throw new IllegalStateException(String.format("Unhandled node: %s", node));
    }
}
