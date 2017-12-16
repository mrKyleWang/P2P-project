package com.itheima.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils {

	// 使用c3p0连接池
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	// 将Connection与ThreadLocal绑定
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static Connection getConnection() throws SQLException {
		Connection conn = tl.get();
		if (conn == null) {
			conn = dataSource.getConnection();
			tl.set(conn);
		}
		return conn;
	}

	// 开启事务
	public static void startTransaction() throws SQLException {
		Connection conn = getConnection();
		conn.setAutoCommit(false);
	}

	// 事务提交
	public static void commit() throws SQLException {
		Connection conn = getConnection();
		conn.commit();
	}

	// 事务回滚
	public static void rollback() throws SQLException {
		Connection conn = getConnection();
		conn.rollback();
	}

	// 关闭资源
	public static void close() throws SQLException {
		Connection conn = getConnection();
		conn.close();
		tl.remove();
	}
}
