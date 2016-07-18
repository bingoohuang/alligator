package com.github.bingoohuang.shardingjdbc;

import com.dangdang.ddframe.rdb.sharding.config.yaml.api.YamlShardingDataSource;
import com.github.bingoohuang.utils.U;
import lombok.SneakyThrows;
import lombok.val;

import javax.sql.DataSource;
import java.sql.PreparedStatement;

public class ShardingJdbcMain {
    @SneakyThrows
    public static void main(final String[] args) {
        val tempFile = U.getClasspathFile("/sharding-config/sharding.yaml");
        val dataSource = new YamlShardingDataSource(tempFile);

        clearTable(dataSource);
        prepareData(dataSource);
        printJoinSelect(dataSource);
        printGroupBy(dataSource);
        printCount(dataSource);
    }

    @SneakyThrows
    private static void clearTable(DataSource dataSource) {
        U.exec(dataSource, "DELETE FROM t_order");
        U.exec(dataSource, "DELETE FROM t_order_item");
    }

    @SneakyThrows
    private static void prepareData(DataSource dataSource) {
        val orderSql = "INSERT INTO t_order(order_id, user_id, status) VALUES (?, ?, ?)";
        val itemSql = "INSERT INTO t_order_item(item_id, order_id, user_id) VALUES (?, ?, ?)";

        try (
            val conn = dataSource.getConnection()) {
            try (
                val psOrder = conn.prepareStatement(orderSql);
                val psItem = conn.prepareStatement(itemSql)) {

                for (int i = 1000; i <= 1008; i += 2) {
                    addOrder(psOrder, i, 10, "INIT"); // db0.t_order_0
                    addItem(psItem, i * 100 + 1, i, 10);// db0.t_order_item_0

                    addOrder(psOrder, i, 11, "INIT");// db1.t_order_0
                    addItem(psItem, i * 100 + 1, i, 11);// db1.t_order_item_0
                }

                for (int i = 1001; i <= 1009; i += 2) {
                    addOrder(psOrder, i, 10, "INIT"); // db0.t_order_1
                    addItem(psItem, i * 100 + 1, i, 10); // db0.t_order_item_1

                    addOrder(psOrder, i, 11, "INIT"); // db1.t_order_1
                    addItem(psItem, i * 100 + 1, i, 11);// db1.t_order_item_1
                }
            }
        }
    }

    @SneakyThrows
    private static void addItem(PreparedStatement ps,
                                int itemId, int orderId, int userId) {
        ps.setInt(1, itemId);
        ps.setInt(2, orderId);
        ps.setInt(3, userId);
        ps.executeUpdate();
    }

    @SneakyThrows
    private static void addOrder(PreparedStatement ps,
                                 int orderId, int userId, String status) {
        ps.setInt(1, orderId);
        ps.setInt(2, userId);
        ps.setString(3, status);
        ps.executeUpdate();
    }

    @SneakyThrows
    private static void printCount(DataSource dataSource) {
        val sql = "SELECT count(*) FROM t_order";

        try (
            val conn = dataSource.getConnection();
            val ps = conn.prepareStatement(sql);
            val rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.println("count(*): " + rs.getInt(1));
            }
        }
    }

    @SneakyThrows
    private static void printJoinSelect(DataSource dataSource) {
        val sql = "SELECT i.* FROM t_order o " +
            "JOIN t_order_item i " +
            "ON o.order_id = i.order_id " +
            "WHERE o.user_id = ? AND o.order_id = ?";
        try (
            val conn = dataSource.getConnection();
            val ps = conn.prepareStatement(sql)) {
            ps.setInt(1, 10);
            ps.setInt(2, 1001);
            try (val rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getInt(1)
                        + ", " + rs.getInt(2) + ", " + rs.getString(3));
                }
            }
        }
    }

    @SneakyThrows
    private static void printGroupBy(DataSource dataSource) {
        val sql = "SELECT o.user_id, COUNT(*) " +
            "FROM t_order o " +
            "JOIN t_order_item i " +
            "ON o.order_id=i.order_id " +
            "GROUP BY o.user_id desc";
        try (
            val conn = dataSource.getConnection();
            val preparedStatement = conn.prepareStatement(sql);
            val rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                System.out.println("user_id: " + rs.getInt(1)
                    + ", count: " + rs.getInt(2));
            }
        }
    }

}
