package com.github.bingoohuang.folding.parti;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Knapsack {
    private final double value;
    private final List<Item> items;
    private final int weight;
    private final int available;

    private Knapsack add(Item item) {
        return new Knapsack(this.value + item.value,
                this.items.cons(item),
                this.weight + item.weight,
                this.available - item.weight);
    }

    private boolean canAccept(Item item) {
        return item.weight <= this.available;
    }

    private Knapsack maxValue(Knapsack that) {
        return this.value >= that.value ? this : that;
    }

    public String toString() {
        return String.format("Total value: %s\nTotal weight: %s\nItems:\n%s",
                value, weight, items.foldRight("",
                        (item, string) -> String.format("\t%s\n%s", item, string)));
    }

    private static Knapsack empty(int capacity) {
        return new Knapsack(0.0, List.list(), 0, capacity);
    }

    static Knapsack pack(List<Item> items, Knapsack knapsack) {
        return items.head().flatMap(
                item -> items.tail().map(
                        itemList -> knapsack.canAccept(item)
                                ? pack(itemList, knapsack).maxValue(
                                pack(itemList, knapsack.add(item)))
                                : pack(itemList, knapsack)))
                .foldRight(knapsack, (a, b) -> a);
    }

    public static Knapsack pack(List<Item> items, int knapsackCapacity) {
        return pack(items, empty(knapsackCapacity));
    }
}