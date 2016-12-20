package com.kailiang.lms.utils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;

import com.mysql.jdbc.Statement;

public class BasicUtil {

	public static <T> List<T> getAll(Connection conn, Class<T> clazz, String sql)
			throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException {
		PreparedStatement pstmt = null;
		List<T> results = null;
		conn = ConnectionManager.getConnection();
		pstmt = conn.prepareStatement(sql);
		results = new ArrayList<T>();
		conn.setAutoCommit(false);

		// get resultSet and RSMD
		ResultSet rst = pstmt.executeQuery();
		ResultSetMetaData rsmd = rst.getMetaData();
		// Deal with RST, moving pointer forward
		while (rst.next()) {
			// create new instance as reflection
			T entity = clazz.newInstance();
			// get key and value, using reflection to assign values. Also
			// could
			// get AS name.
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				String columnLabel = rsmd.getColumnLabel(i + 1);
				Object columnValue = rst.getObject(i + 1);
				 ReflectionUtils.setFieldValue(entity, columnLabel,
				 columnValue);
//				BeanUtils.setProperty(entity, columnLabel, columnValue);
			}
			results.add(entity);
		}
		conn.commit();
		return results;

	}

	public static void common(Connection conn, String sql, Object... args) throws SQLException {
		PreparedStatement pstmt = null;
		conn = ConnectionManager.getConnection();
		pstmt = conn.prepareStatement(sql);
		conn.setAutoCommit(false);
		for (int i = 0; i < args.length; i++) {
			pstmt.setObject(i + 1, args[i]);
		}
		pstmt.executeUpdate();
		conn.commit();
	}

	public static Integer addWithId(Connection conn, String sql, Object... args) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		conn.setAutoCommit(false);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				pstmt.setObject(i + 1, args[i]);
			}
		}
		pstmt.executeUpdate();
		conn.commit();
		ResultSet res = pstmt.getGeneratedKeys();
		Integer id = 0;
		if (res.next()) {
			id = res.getInt(1);
		}
		return id;
	}

	public static <T> List<T> getList(Connection conn, Class<T> clazz, String sql, Object... args)
			throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException {
		List<T> results = new ArrayList<T>();
		// get the connection
		PreparedStatement pstmt = null;

		conn = ConnectionManager.getConnection();
		pstmt = conn.prepareStatement(sql);
		conn.setAutoCommit(false);
		// get the preparedStatement
		for (int i = 0; i < args.length; i++) {
			pstmt.setObject(i + 1, args[i]);
		}
		// get resultSet and RSMD
		ResultSet rst = pstmt.executeQuery();
		ResultSetMetaData rsmd = rst.getMetaData();
		// Deal with RST, moving pointer forward
		while (rst.next()) {
			// create new instance as reflection
			T entity = clazz.newInstance();
			// get key and value, using reflection to assign values. Also
			// could
			// get AS name.
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				String columnLabel = rsmd.getColumnLabel(i + 1);
				Object columnValue = rst.getObject(i + 1);
				 ReflectionUtils.setFieldValue(entity, columnLabel,
				 columnValue);
//				BeanUtils.setProperty(entity, columnLabel, columnValue);
			}
			results.add(entity);
		}
		conn.commit();
		return results;
	}

	public static Matcher isMatch(String s) {
		String pattern = "(\\d+)";
		Pattern pat = Pattern.compile(pattern);
		return pat.matcher(s);
	}

	public static <T> T getSingle(Connection conn, Class<T> clazz, String sql, Object... args) {
		T entity = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		try {
			// 1. get the resultSet object
			connection = ConnectionManager.getConnection();
			pstmt = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				pstmt.setObject(i + 1, args[i]);
			}
			rst = pstmt.executeQuery();
			// 2. get resultSetMetaData object
			ResultSetMetaData rsmd = rst.getMetaData();
			// 3. make a Map<String(SQL column AS name), Object(value)>
			// 4. fill the values
			while (rst.next()) {
				// create new instance as reflection
				entity = clazz.newInstance();
				// get key and value, using reflection to assign values. Also
				// could
				// get AS name.
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					String columnLabel = rsmd.getColumnLabel(i + 1);
					Object columnValue = rst.getObject(i + 1);
					 ReflectionUtils.setFieldValue(entity, columnLabel,
					 columnValue);
//					BeanUtils.setProperty(entity, columnLabel, columnValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

}
