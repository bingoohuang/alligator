package com.github.bingoohuang.utils;

import com.google.common.collect.Lists;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by bingoohuang on 2017/3/29.
 */
public class SqlSplitter {
    public List<String> split(String sqlsString, char separateChar) {
        List<String> sqls = Lists.newArrayList();

        boolean inQuoted = false;
        int pos = 0;
        val len = sqlsString.length();
        for (int i = 0; i < len; ++i) {
            char ch = sqlsString.charAt(i);
            if (ch == '\\') {
                ++i;
            } else if (ch == '\'') {
                if (inQuoted && i + 1 < len && sqlsString.charAt(i + 1) == '\'') {
                    ++i; // jump espace for literal apostrophe, or single quote
                } else {
                    inQuoted = !inQuoted;
                }
            } else if (!inQuoted && ch == separateChar) {
                tryAddSql(sqls, sqlsString.substring(pos, i));
                pos = i + 1;
            }
        }

        if (pos < len) {
            tryAddSql(sqls, sqlsString.substring(pos));
        }
        return sqls;
    }

    private void tryAddSql(List<String> sqls, String sql) {
        if (StringUtils.isBlank(sql)) return;

        sqls.add(sql.trim());
    }
}
