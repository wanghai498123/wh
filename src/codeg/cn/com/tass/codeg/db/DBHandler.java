package cn.com.tass.codeg.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cn.com.tass.codeg.ConfigConsts;

public class DBHandler {
	private static Connection conn;

	public static final Connection createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + ConfigConsts.DB_HOST + ":" + ConfigConsts.DB_PORT
					+ "/" + ConfigConsts.DB_NAME, ConfigConsts.DB_USER, ConfigConsts.DB_PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static final void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}