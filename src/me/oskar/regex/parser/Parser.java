package me.oskar.regex.parser;

import me.oskar.regex.lexer.Lexer;
import me.oskar.regex.lexer.Token;
import me.oskar.regex.lexer.TokenType;
import me.oskar.regex.node.*;

import java.util.HashSet;

public class Parser {

    private final Lexer lexer;
    private Token currentToken;
    private final HashSet<Character> seenTerminals = new HashSet<>();

    public Parser(final Lexer lexer) {
        this.lexer = lexer;

        nextToken();
    }

    private void nextToken() {
        currentToken = lexer.readToken();
    }

    public Pattern generateAst() {
        return new Pattern(parseGroup(), seenTerminals);
    }

    private Node parseGroup() {
        return new GroupNode(parseDisjunction());
    }

    private Node parseDisjunction() {
        var left = parseConjunction();

        while (currentToken.getType() == TokenType.PIPE) {
            nextToken();
            final var right = parseConjunction();

            left = new DisjunctionNode(left, right);
        }

        return left;
    }

    private Node parseConjunction() {
        var left = parseRepeat();

        while (currentToken.getType() == TokenType.LITERAL || currentToken.getType() == TokenType.LPAREN) {
            final var right = parseRepeat();

            left = new ConjunctionNode(left, right);
        }

        return left;
    }

    private Node parseRepeat() {
        var left = parseFactor();

        switch (currentToken.getType()) {
            case ASTERISK -> {
                nextToken();
                return new RepeatAsteriskNode(left);
            }
            case PLUS -> {
                nextToken();
                return new RepeatPlusNode(left);
            }
            default -> {
                return left;
            }
        }
    }

    private Node parseFactor() {
        switch (currentToken.getType()) {
            case LITERAL -> {
                final var terminal = currentToken.getLiteral().charAt(0);
                seenTerminals.add(terminal);
                final var node = new LiteralNode(terminal);
                nextToken();
                return node;
            }
            case LPAREN -> {
                nextToken();
                final var group = parseGroup();
                if (currentToken.getType() != TokenType.RPAREN) {
                    throw new IllegalStateException("Expected )");
                }
                nextToken();

                return group;
            }
            default -> throw new IllegalStateException(String.format("Unexpected token %s:", currentToken));
        }
    }
}
