package me.oskar;

import me.oskar.regex.Regex;

public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: regex <pattern> <input>");
            System.exit(1);
        }

        final var pattern = args[0];
        final var input = args[1];

        final var regex = new Regex(pattern);
        System.out.println(regex.test(input));
    }
}
