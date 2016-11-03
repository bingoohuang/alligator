package com.github.bingoohuang.utils;

import com.google.common.io.Files;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

import javax.sql.DataSource;
import java.io.File;

public class U {
    @SneakyThrows
    public static File getClasspathFile(String classpathFileName) {
        val tempFile = File.createTempFile("stream2file", ".tmp");
        tempFile.deleteOnExit();

        @Cleanup val resStream = U.class.getResourceAsStream(classpathFileName);
        val buffer = new byte[resStream.available()];
        resStream.read(buffer);

        Files.write(buffer, tempFile);
        return tempFile;
    }

    @SneakyThrows
    public static boolean exec(DataSource dataSource, String sql) {
        @Cleanup val conn = dataSource.getConnection();
        @Cleanup val ps = conn.prepareStatement(sql);
        return ps.execute();
    }
}
