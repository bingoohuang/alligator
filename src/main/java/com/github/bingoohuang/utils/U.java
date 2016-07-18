package com.github.bingoohuang.utils;

import com.google.common.io.Files;
import lombok.SneakyThrows;
import lombok.val;

import javax.sql.DataSource;
import java.io.File;

public class U {
    @SneakyThrows
    public static File getClasspathFile(String classpathFileName) {
        val tempFile = File.createTempFile("stream2file", ".tmp");
        tempFile.deleteOnExit();

        val resStream = U.class.getResourceAsStream(classpathFileName);
        val buffer = new byte[resStream.available()];
        resStream.read(buffer);

        Files.write(buffer, tempFile);
        return tempFile;
    }

    @SneakyThrows
    public static boolean exec(DataSource dataSource, String sql) {
        try (
            val conn = dataSource.getConnection();
            val ps = conn.prepareStatement(sql)) {
            return ps.execute();
        }
    }
}
