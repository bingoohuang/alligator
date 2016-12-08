package com.github.bingoohuang.binlog;


import com.github.shyiko.mysql.binlog.BinaryLogClient;
import lombok.val;

import java.io.IOException;

public class BinlogDemo {
    public static void main(String[] args) throws IOException {
        val client = new BinaryLogClient("192.168.99.100", 13306, "repl", "repl");
        client.registerEventListener(event -> {
            System.out.println("header:[[" + event.getHeader() + "]]");
            System.out.println("data  :[[" + event.getData() + "]]");
        });
        client.connect();
    }
}