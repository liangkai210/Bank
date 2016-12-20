package com.kailiang.lms.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.kailiang.lms.bean.Author;
import com.kailiang.lms.bean.Book;
import com.kailiang.lms.utils.BasicUtil;

public class AuthorDao implements Dao<Author> {

	private Connection conn = null;

	public AuthorDao(Connection conn) {
		this.conn = conn;
	}

	public void add(Author t) throws SQLException {
		String sql = "INSERT INTO tbl_author(authorName) VALUES (?)";
		BasicUtil.common(conn, sql, t.getAuthorName());
	}

	public void update(Author t, String property, Object value) throws SQLException {
		String sql = "UPDATE tbl_author SET " + property + " = ? WHERE authorId = ?";
		BasicUtil.common(conn, sql, value, t.getAuthorId());

	}

	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM tbl_author WHERE authorId = ?";
		BasicUtil.common(conn, sql, id);
	}
	public List<Author> getQuery(Object value) throws IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
		String sql = "SELECT * FROM tbl_author WHERE authorName like ?";
		return BasicUtil.getList(conn, Author.class, sql, value);
	}

	public List<Author> getList(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_author WHERE " + property + " = ?";
		return BasicUtil.getList(conn, Author.class, sql, value);
	}

	public Author get(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		return getList(property, value).get(0);
	}

	public List<Author> getAll()
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_author";
		return BasicUtil.getAll(conn, Author.class, sql);
	}

}
