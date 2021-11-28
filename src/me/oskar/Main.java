package me.oskar;

import me.oskar.regex.Regex;

public class Main {

    public static void main(String[] args) {
        final var regex = new Regex("a?");

        System.out.println(regex.test(""));
        System.out.println(regex.test("aa"));
        System.out.println(regex.test("de"));
    }
}
