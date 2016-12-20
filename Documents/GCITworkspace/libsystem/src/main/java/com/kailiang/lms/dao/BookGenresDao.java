package com.kailiang.lms.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.kailiang.lms.bean.BookGenres;
import com.kailiang.lms.utils.BasicUtil;

public class BookGenresDao implements Dao<BookGenres> {

	private Connection conn = null;

	public BookGenresDao(Connection conn) {
		this.conn = conn;
	}

	public void add(BookGenres t) throws SQLException {
		String sql = "INSERT INTO tbl_book_genres(genre_id, bookId) VALUES (?,?)";
		BasicUtil.common(conn, sql, t.getGenreId(), t.getBookId());
	}

	public void update(BookGenres t, String property, Object value)
			throws SQLException, IllegalAccessException, InvocationTargetException {
		String sql = "UPDATE tbl_book_genres SET " + property + " = ? WHERE bookId = ?";
		BasicUtil.common(conn, sql, value, t.getBookId());

	}

	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM tbl_book_genres WHERE bookId = ?";
		BasicUtil.common(conn, sql, id);

	}

	public List<BookGenres> getList(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_book_genres WHERE " + property + " = ?";
		return BasicUtil.getList(conn, BookGenres.class, sql, value);
	}

	public BookGenres get(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		return getList(property, value).get(0);
	}

	public List<BookGenres> getAll()
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_book_genres";
		return BasicUtil.getAll(conn, BookGenres.class, sql);
	}

}
