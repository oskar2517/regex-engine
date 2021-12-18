package me.oskar.regex.engine.lexer;

public record Token(TokenType type, String literal) {

    public String getLiteral() {
        return literal;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", type, literal);
    }
}
