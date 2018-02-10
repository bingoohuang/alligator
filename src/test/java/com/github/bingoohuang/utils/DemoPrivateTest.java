package com.github.bingoohuang.utils;

import org.joor.Reflect;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;


/**
 * @author bingoohuang [bingoohuang@gmail.com] Created on 2016/11/14.
 */
public class DemoPrivateTest {
    public static class Adder {
        private int add(int a, int b) {
            return a + b;
        }
    }

    @Test public void testAdd() {
        Adder adder = new Adder();
        int result = Reflect.on(adder).call("add", 1, 2).get();
        assertThat(result).isEqualTo(3);
    }
}
