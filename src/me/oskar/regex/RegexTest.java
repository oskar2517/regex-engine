package me.oskar.regex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegexTest {

    @Test
    public void test() {
        assertTrue(new Regex("a").test("a"));
        assertFalse(new Regex("a").test("b"));
        assertFalse(new Regex("a").test(""));

        assertTrue(new Regex("a?").test("a"));
        assertTrue(new Regex("a?").test(""));
        assertFalse(new Regex("a?").test("aa"));

        assertTrue(new Regex("abcd").test("abcd"));
        assertFalse(new Regex("abcd").test("abc"));

        assertTrue(new Regex("a|b").test("a"));
        assertTrue(new Regex("a|b").test("b"));
        assertFalse(new Regex("a|b").test("c"));

        assertTrue(new Regex("a*").test(""));
        assertTrue(new Regex("a*").test("aaaaa"));
        assertFalse(new Regex("a*").test("bbb"));

        assertFalse(new Regex("a+").test(""));
        assertTrue(new Regex("a+").test("aaaaa"));
        assertFalse(new Regex("a+").test("bbb"));

        assertTrue(new Regex("a(b|c)+de").test("abde"));
        assertTrue(new Regex("a(b|c)+de").test("abcde"));
        assertTrue(new Regex("a(b|c)+de").test("abcde"));
        assertFalse(new Regex("a(b|c)+de").test("ade"));

        assertTrue(new Regex("(abc)|(def)").test("abc"));
        assertTrue(new Regex("(abc)|(def)").test("def"));
        assertFalse(new Regex("(abc)|(def)").test("ab"));

        assertTrue(new Regex("(abc)(def)").test("abcdef"));
    }
}