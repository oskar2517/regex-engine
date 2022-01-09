package me.oskar.regex.engine;

import me.oskar.regex.engine.graphviz.Graphviz;
import me.oskar.regex.engine.lexer.Lexer;
import me.oskar.regex.engine.parser.Parser;
import me.oskar.regex.engine.thompson.PatternConstructor;
import me.oskar.regex.engine.thompson.State;

public class Regex {

    private final String regex;
    private State dfaStartState;

    public Regex(final String regex, final boolean debug) {
        this.regex = regex;

        init(debug);
    }

    public Regex(final String regex) {
        this.regex = regex;

        init(false);
    }

    private void init(final boolean debug) {
        final var lexer = new Lexer(regex);
        final var parser = new Parser(lexer);

        final var pattern = parser.generateAst();

        final var patternConstructor = new PatternConstructor(pattern);
        final var nfaStartState = patternConstructor.constructNonDeterministic();
        dfaStartState = patternConstructor.transformDeterministic();

        if (debug) {
            System.out.println("Generated NFA:");
            System.out.println(new Graphviz(nfaStartState, "nfa_regex").generate());
            System.out.println();

            System.out.println("Generated DFA:");
            System.out.println(new Graphviz(dfaStartState, "dfa_regex").generate());
            System.out.println();
        }
    }

    public boolean test(final String s) {
        return dfaStartState.test(s);
    }
}
