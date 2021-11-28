package me.oskar;

import me.oskar.lexer.Lexer;
import me.oskar.parser.Parser;
import me.oskar.thompson.PatternConstructor;

public class Main {

    public static void main(String[] args) {
        final var regex = "a(b|c)*";

        final var lexer = new Lexer(regex);
        final var parser = new Parser(lexer);

        final var pattern = parser.generateAst();
        System.out.println(pattern.getAst());

        final var patternConstructor = new PatternConstructor(pattern);
        patternConstructor.constructNonDeterministic();
        patternConstructor.transformDeterministic();
    }
}
