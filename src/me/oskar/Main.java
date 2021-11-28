package me.oskar;

import me.oskar.regex.Regex;

public class Main {

    public static void main(String[] args) {
        final var regex = new Regex("a(b|c)+de");

        System.out.println(regex.test("abbcbbde"));
        System.out.println(regex.test("ade"));
        System.out.println(regex.test("de"));
    }
}
