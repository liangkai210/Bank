package com.kailiang.lms.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.kailiang.lms.bean.BookAuthors;
import com.kailiang.lms.utils.BasicUtil;

public class BookAuthorsDao implements Dao<BookAuthors> {

	private Connection conn = null;

	public BookAuthorsDao(Connection conn) {
		this.conn = conn;
	}

	public void add(BookAuthors t) throws SQLException {
		String sql = "INSERT INTO tbl_book_authors (bookId, authorId) VALUES (?,?)";
		BasicUtil.common(conn, sql, t.getBookId(), t.getAuthorId());
	}

	public void update(BookAuthors t, String property, Object value) throws SQLException {
		String sql = "UPDATE tbl_book_authors SET " + property + " = ? WHERE bookId = ?";
		BasicUtil.common(conn, sql, value, t.getBookId());
	}

	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM tbl_book_authors WHERE bookId = ?";
		BasicUtil.common(conn, sql, id);

	}

	public List<BookAuthors> getList(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_book_authors WHERE " + property + " = ?";
		return BasicUtil.getList(conn, BookAuthors.class, sql, value);
	}

	public BookAuthors get(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		return getList(property, value).get(0);
	}

	public List<BookAuthors> getAll()
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_book_authors";
		return BasicUtil.getAll(conn, BookAuthors.class, sql);
	}

}
