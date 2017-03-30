package com.github.bingoohuang.utils;

import org.junit.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by bingoohuang on 2017/3/29.
 */
public class SqlSplitterTest {
    @Test
    public void test1() {
        String sql = "create table aaa; drop table aaa;";
        List<String> split = new SqlSplitter().split(sql, ';');
        assertThat(split).containsExactly("create table aaa", "drop table aaa");
    }

    @Test
    public void test() {
        String sql = "ALTER TABLE `tt_l_mbrcard_chg` \n" +
                "ADD COLUMN `PREFERENTIAL_WAY` CHAR(3) NULL COMMENT '优惠方式:0:现金券;1:减免,2:赠送金额 ;' AFTER `PAY_TYPE`; ";

        List<String> split = new SqlSplitter().split(sql, ';');
        assertThat(split).containsExactly("ALTER TABLE `tt_l_mbrcard_chg` \n" +
                "ADD COLUMN `PREFERENTIAL_WAY` CHAR(3) NULL COMMENT '优惠方式:0:现金券;1:减免,2:赠送金额 ;' AFTER `PAY_TYPE`");
    }

    @Test
    public void testDoubleSingleQuotes() {
        String sql = "ALTER TABLE `tt_l_mbrcard_chg` \n" +
                "ADD COLUMN `PREFERENTIAL_WAY` CHAR(3) NULL COMMENT '优惠方式:''0:现金券;1:减免,2:赠送金额 ;' AFTER `PAY_TYPE`; ";

        List<String> split = new SqlSplitter().split(sql, ';');
        assertThat(split).containsExactly("ALTER TABLE `tt_l_mbrcard_chg` \n" +
                "ADD COLUMN `PREFERENTIAL_WAY` CHAR(3) NULL COMMENT '优惠方式:''0:现金券;1:减免,2:赠送金额 ;' AFTER `PAY_TYPE`");
    }
}
