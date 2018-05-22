package com.spbstu.hw8.entities;

import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MetalAndColorEntity {
    public int[] summary;
    public String[] elements;
    public String color;
    public String metals;
    public String[] vegetables;

    private static final Collector<CharSequence,?,String> COMMA = Collectors.joining(", ");

    public String summaryAsString() {
        return String.format("Summary: %d", Arrays.stream(summary).sum());
    }
    public String elementsAsString() {
        return String.format("Elements: %s", Arrays.stream(elements).collect(COMMA));
    }
    public String colorAsString() {
        return String.format("Color: %s", color);
    }
    public String metalsAsString() {
        return String.format("Metal: %s", metals);
    }
    public String vegetablesAsString() {
        return String.format("Vegetables: %s", Arrays.stream(vegetables).collect(COMMA));
    }
}
