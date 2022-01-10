package me.oskar.regex.engine.graphviz;

import me.oskar.regex.engine.thompson.State;

import java.util.HashSet;

public class Graphviz {

    private final State startState;
    private final String graphName;
    private final HashSet<State> visitedStates = new HashSet<>();

    public Graphviz(final State startState, final String graphName) {
        this.startState = startState;
        this.graphName = graphName;
    }

    public String generate() {
        final var edgeSb = new StringBuilder();
        generate(startState, edgeSb);

        final var graphSb = new StringBuilder();
        graphSb.append(String.format("digraph %s {\r\n", graphName));
        graphSb.append("  node [shape=doublecircle, style=solid];");
        for (final var cs : visitedStates) {
            if (cs.isEndState()) {
                graphSb.append(String.format(" %s", cs.getId()));
            }
        }
        graphSb.append(";\r\n");
        graphSb.append("  node [shape=point, style=invis]; ENTRY;\r\n");
        graphSb.append("  node [shape=circle, style=solid];\r\n");
        graphSb.append(String.format("  ENTRY -> %s;\r\n", startState.getId()));
        graphSb.append(edgeSb);
        graphSb.append("}");

        return graphSb.toString();
    }

    private void generate(final State s, final StringBuilder out) {
        final var visited = visitedStates.contains(s);
        visitedStates.add(s);

        if (!visited) {
            for (final var entry : s.getTerminalEdges().entrySet()) {
                for (final var cs : entry.getValue()) {
                    out.append(String.format("  %s -> %s [label=\"%s\"]\r\n", s.getId(), cs.getId(), entry.getKey()));
                    generate(cs, out);
                }
            }
            for (final var cs : s.getEpsilonEdges()) {
                out.append(String.format("  %s -> %s [label=<&epsilon;>]\r\n", s.getId(), cs.getId()));
                generate(cs, out);
            }
        }
    }
}
