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

    public boolean hasTerminalEdge(final String terminal) {
        return terminalEdges.containsKey(terminal);
    }

    public HashSet<State> epsilonSpan() {
        final var states = new HashSet<State>();
        for (State s : epsilonEdges) {
            // We don't want infinite recursion
            if (s.epsilonEdges.contains(this)) {
                states.add(s);
                states.addAll(s.epsilonEdges);
            } else {
                states.addAll(s.epsilonSpan());
            }
        }
        states.add(this);

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
