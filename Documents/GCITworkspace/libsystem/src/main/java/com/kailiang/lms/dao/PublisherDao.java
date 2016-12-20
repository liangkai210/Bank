package com.kailiang.lms.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.kailiang.lms.bean.Author;
import com.kailiang.lms.bean.Publisher;
import com.kailiang.lms.utils.BasicUtil;

public class PublisherDao implements Dao<Publisher> {

	private Connection conn = null;

	public PublisherDao(Connection conn) {
		this.conn = conn;
	}

	public void add(Publisher t) throws SQLException {
		String sql = "INSERT INTO tbl_publisher(publisherId, publisherName, publisherAddress, publisherPhone) "
				+ "VALUES (?,?,?,?)";
		BasicUtil.common(conn, sql, t.getPublisherId(), t.getPublisherName(), t.getPublisherAddress(),
				t.getPublisherPhone());

	}

	public void update(Publisher t, String property, Object value)
			throws SQLException, IllegalAccessException, InvocationTargetException {
		String sql = "UPDATE tbl_publisher SET " + property + " = ? WHERE publisherId = ?";
		BasicUtil.common(conn, sql, value, t.getPublisherId());

	}

	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM tbl_publisher WHERE publisherId = ?";
		BasicUtil.common(conn, sql, id);

	}

	public List<Publisher> getList(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_publisher WHERE " + property + " = ?";
		return BasicUtil.getList(conn, Publisher.class, sql, value);
	}

	public Publisher get(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		return getList(property, value).get(0);
	}
	public List<Publisher> getQuery(Object value) throws IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
		String sql = "SELECT * FROM tbl_publisher WHERE publisherName like ?";
		return BasicUtil.getList(conn, Publisher.class, sql, value);
	}
	
	public Publisher getSingle(String property, Object value){
		String sql = "SELECT * FROM tbl_publisher WHERE " + property + " = ?";
		return BasicUtil.getSingle(conn, Publisher.class, sql, value);
	}

	public List<Publisher> getAll()
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_publisher";
		return BasicUtil.getAll(conn, Publisher.class, sql);
	}

}
