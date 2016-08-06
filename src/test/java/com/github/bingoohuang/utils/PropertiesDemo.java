package com.github.bingoohuang.utils;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Properties;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.truth.Truth.assertThat;

public class PropertiesDemo {
    @Test @SneakyThrows
    public void testPropertyValue() {
        val bis = new ByteArrayInputStream("key= value ".getBytes(UTF_8));
        val properties = new Properties() {{ load(bis); }};
        val value = properties.getProperty("key");
        assertThat(value).isEqualTo("value ");
    }
}
