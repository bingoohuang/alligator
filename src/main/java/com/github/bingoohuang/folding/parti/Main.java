package com.github.bingoohuang.folding.parti;

public class Main {
    // https://dzone.com/articles/folding-the-universe-part-i
    public static void main(String... args) {
        int capacity = 400;
        final List<Item> items = List.<Item>list()
                .cons(new Item("map", 9, 150.0))
                .cons(new Item("compass", 13, 35.0))
                .cons(new Item("water", 153, 200.0))
                .cons(new Item("sandwich", 50, 160.0))
                .cons(new Item("glucose", 15, 60.0))
                .cons(new Item("tin", 68, 45.0))
                .cons(new Item("banana", 27, 60.0))
                .cons(new Item("apple", 39, 40.0))
                .cons(new Item("cheese", 23, 30.0))
                .cons(new Item("beer", 52, 10.0))
                .cons(new Item("cream", 11, 70.0))
                .cons(new Item("camera", 32, 30.0))
                .cons(new Item("tshirt", 24, 15.0))
                .cons(new Item("trousers", 48, 10.0))
                .cons(new Item("umbrella", 73, 40.0))
                .cons(new Item("trousers", 42, 70.0))
                .cons(new Item("overclothes", 43, 75.0))
                .cons(new Item("notecase", 22, 80.0))
                .cons(new Item("sunglasses", 7, 20.0))
                .cons(new Item("towel", 18, 12.0))
                .cons(new Item("socks", 4, 50.0))
                .cons(new Item("book", 30, 10.0));
        System.out.println(Knapsack.pack(items, capacity));
    }
}
