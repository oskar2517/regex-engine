package me.oskar.regex.lexer;

public class Lexer {

    private static final char EOF = '\0';

    private final String code;
    private char currentChar = EOF;
    private int position = 0;

    public Lexer(final String code) {
        this.code = code;
    }


    private void readChar() {
        if (position >= code.length()) {
            currentChar = EOF;
        } else {
            currentChar = code.charAt(position);
        }

        position++;
    }

    public void printTokens() {
        while (true) {
            final var token = readToken();
            System.out.println(token);
            if (token.getType() == TokenType.EOF) {
                break;
            }
        }
    }

    public Token readToken() {
        readChar();

        if (position > code.length()) {
            return new Token(TokenType.EOF, String.valueOf(currentChar));
        }

        return switch (currentChar) {
            case '(' -> new Token(TokenType.LPAREN, "(");
            case ')' -> new Token(TokenType.RPAREN, ")");
            case '+' -> new Token(TokenType.PLUS, "+");
            case '*' -> new Token(TokenType.ASTERISK, "*");
            case '[' -> new Token(TokenType.LBRACK, "[");
            case ']' -> new Token(TokenType.RBRACK, "]");
            case '?' -> new Token(TokenType.QUESTION_MARK, "?");
            case '|' -> new Token(TokenType.PIPE, "|");
            default -> new Token(TokenType.LITERAL, String.valueOf(currentChar));
        };
    }
}
