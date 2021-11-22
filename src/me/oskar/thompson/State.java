package me.oskar.thompson;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class State {

    public static int instanceCount = 0;
    private int id;
    private boolean endState = false;

    private final HashSet<State> epsilonEdges = new HashSet<>();
    private final Map<String, HashSet<State>> terminalEdges = new HashMap<>();

    public State() {
        id = instanceCount++;
    }

    public void addEpsilonEdge(final State state) {
        epsilonEdges.add(state);
    }

    public void addTerminalEdge(final String terminal, final State state) {
        if (!terminalEdges.containsKey(terminal)) {
            terminalEdges.put(terminal, new HashSet<>());
        }

        terminalEdges.get(terminal).add(state);
    }

    public HashSet<State> epsilonSpan() {
        final var states = new HashSet<State>();
        states.add(this);
        for (State s : epsilonEdges) {
            // TODO: fix cycle properly
            if (!s.epsilonEdges.contains(this)) {
                states.addAll(s.epsilonSpan());
            }
        }

        return states;
    }

    public HashSet<State> move(final String terminal) {
        final var states = new HashSet<State>();

        if (!terminalEdges.containsKey(terminal)) {
            return states;
        }

        states.addAll(terminalEdges.get(terminal));

        return states;
    }

    public boolean isEndState() {
        return endState;
    }

    public void setEndState(boolean endState) {
        this.endState = endState;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("(State %s)", id);
    }
}
