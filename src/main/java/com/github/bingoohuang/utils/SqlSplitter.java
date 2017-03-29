package com.github.bingoohuang.utils;

import com.google.common.collect.Lists;
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
        int ii = sqlsString.length();
        for (int i = 0; i < ii; ++i) {
            char ch = sqlsString.charAt(i);
            if (ch == '\\') {
                ++i;
            } else if (ch == '`' || ch == '\'') {
                inQuoted = !inQuoted;
            } else if (ch == separateChar) {
                if (!inQuoted) {
                    String sql = sqlsString.substring(pos, i);
                    if (StringUtils.isNotBlank(sql)) sqls.add(sql.trim());
                    pos = i + 1;
                }
            }
        }

        if (pos < ii) {
            String sql = sqlsString.substring(pos);
            if (StringUtils.isNotBlank(sql)) sqls.add(sql.trim());
        }


        return sqls;
    }
}
