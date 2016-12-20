package com.kailiang.lms.utils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionManager {

	private static Connection instance;

	private static Integer lock = 0;

	public static Connection getConnection() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = init();
				}
			}
		}
		return instance;
	}

	private static Connection init() {
		Connection con = null;
		ComboPooledDataSource ds = null;
		try {
			ds = new ComboPooledDataSource();
			ds.setDriverClass("com.mysql.jdbc.Driver");
			ds.setJdbcUrl("jdbc:mysql://localhost:3306/library");
			ds.setUser("root");
			ds.setPassword("1234");
			con = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		return con;
	}
}
