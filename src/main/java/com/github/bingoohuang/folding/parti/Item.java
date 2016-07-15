package com.github.bingoohuang.folding.parti;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    public final String name;
    public final int weight;
    public final double value;

    public String toString() {
        return String.format("item(%s, %s, %s)", name, weight, value);
    }
}
