package com.github.bingoohuang.folding.parti;

public class Item {
    public final String name;
    public final int weight;
    public final double value;
    public Item(String name, int weight, double value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }
    public String toString() {
        return String.format("item(%s, %s, %s)", name, weight, value);
    }
}
