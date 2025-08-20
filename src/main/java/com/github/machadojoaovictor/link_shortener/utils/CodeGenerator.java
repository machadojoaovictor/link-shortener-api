package com.github.machadojoaovictor.link_shortener.utils;

import java.security.SecureRandom;

public class CodeGenerator {

    private static final String BASE62_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateBase62(int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(BASE62_ALPHABET.length());
            sb.append(BASE62_ALPHABET.charAt(index));
        }
        return sb.toString();
    }

    public static String toBase62(long num) {
        if (num == 0) return "0";

        StringBuilder sb = new StringBuilder();

        while (num > 0) {
            sb.append(BASE62_ALPHABET.charAt((int) (num % 62)));
            num /= 62;
        }

        return sb.reverse().toString();
    }


}
