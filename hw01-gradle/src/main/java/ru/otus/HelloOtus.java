package ru.otus;

import com.google.common.base.Joiner;

public class HelloOtus {
    @SuppressWarnings("java:S106")
    public static void main(String[] args) {
        String joined = Joiner.on(", ").join("МИР", "ТРУД", "JAVA");
        System.out.println(joined);
    }
}
