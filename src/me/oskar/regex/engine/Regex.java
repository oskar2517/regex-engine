package me.oskar.regex.engine;

import me.oskar.regex.engine.lexer.Lexer;
import me.oskar.regex.engine.parser.Parser;
import me.oskar.regex.engine.thompson.PatternConstructor;
import me.oskar.regex.engine.thompson.State;

public class Regex {

    private final String regex;
    private State startState;

    public Regex(final String regex) {
        this.regex = regex;

        init();
    }

    private void init() {
        final var lexer = new Lexer(regex);
        final var parser = new Parser(lexer);

        final var pattern = parser.generateAst();

        final var patternConstructor = new PatternConstructor(pattern);
        patternConstructor.constructNonDeterministic();
        startState = patternConstructor.transformDeterministic();
    }

    public boolean test(final String s) {
        return startState.test(s);
    }
}
