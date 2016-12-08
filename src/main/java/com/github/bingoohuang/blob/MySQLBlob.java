package com.github.bingoohuang.blob;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;
import org.n3r.idworker.Sid;

import java.io.*;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author bingoohuang [bingoohuang@gmail.com] Created on 2016/11/13.
 */
public class MySQLBlob {
    @SneakyThrows public static void main(String[] args) {
        demoBlob();
    }

    private static void copyFile() throws IOException {
        val classLoader = MySQLBlob.class.getClassLoader();
        val is = classLoader.getResourceAsStream("blob.xlsx");

        String id = Sid.next();
        File file = new File(id + ".xlsx");
        val output = new FileOutputStream(file);
        copyStream(is, output);
        output.close();

        System.out.println(file);
    }

    private static void copyStream(InputStream is, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        for (; ; ) {
            int n = is.read(buffer);
            if (n < 0) break;
            output.write(buffer, 0, n);
        }
    }

    private static void demoBlob() throws SQLException, IOException {
        String url = "jdbc:mysql://192.168.99.100:13306/dba?useUnicode=true&&characterEncoding=UTF-8&connectTimeout=3000&socketTimeout=3000&autoReconnect=true";
        String username = "root";
        String pwd = "my-secret-pw";
        @Cleanup val conn = DriverManager.getConnection(url, username, pwd);

        // create table last_excel (id varchar(30), excel longblob);
        val sql = "insert into last_excel(id, excel) values(?, ?)";
        @Cleanup val ps = conn.prepareStatement(sql);

        String id = Sid.next();
        val classLoader = MySQLBlob.class.getClassLoader();
        @Cleanup val is = classLoader.getResourceAsStream("blob.xlsx");

        ps.setString(1, id);
        ps.setBinaryStream(2, is);
        ps.executeUpdate();

        val sql2 = "select excel from last_excel where id = ?";
        @Cleanup val ps2 = conn.prepareStatement(sql2);
        ps2.setString(1, id);
        @Cleanup val rs = ps2.executeQuery();
        if (rs.next()) {
            @Cleanup InputStream input = rs.getBinaryStream(1);
            File file = new File(id + ".xlsx");
            @Cleanup val output = new FileOutputStream(file);
            copyStream(input, output);
            System.out.println(file);
        }
    }
}
