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
        val bis = new ByteArrayInputStream("cookieSecurity=4382AD47B18D ".getBytes(UTF_8));
        val properties = new Properties() {{
            load(bis);
        }};
        val value = properties.getProperty("cookieSecurity");
        assertThat(value).isEqualTo("4382AD47B18D");
    }
}
