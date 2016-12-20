package com.kailiang.lms.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.kailiang.lms.bean.Author;
import com.kailiang.lms.bean.Genre;
import com.kailiang.lms.utils.BasicUtil;

public class GenreDao implements Dao<Genre> {

	private Connection conn = null;

	public GenreDao(Connection conn) {
		this.conn = conn;
	}

	public void add(Genre t) throws SQLException {
		String sql = "INSERT INTO tbl_genre(genre_id, genre_name) " + "VALUES (?,?)";
		BasicUtil.common(conn, sql, t.getGenre_id(), t.getGenre_name());

	}

	public void update(Genre t, String property, Object value)
			throws SQLException, IllegalAccessException, InvocationTargetException {
		String sql = "UPDATE tbl_genre SET " + property + " = ? WHERE genre_id = ?";
		BasicUtil.common(conn, sql, value, t.getGenre_id());
	}

	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM tbl_genre WHERE genre_id = ?";
		BasicUtil.common(conn, sql, id);

	}
	public List<Genre> getQuery(Object value) throws IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
		String sql = "SELECT * FROM tbl_genre WHERE genre_name like ?";
		return BasicUtil.getList(conn, Genre.class, sql, value);
	}

	public List<Genre> getList(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_genre WHERE " + property + " = ?";
		return BasicUtil.getList(conn, Genre.class, sql, value);
	}

	public Genre get(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		return getList(property, value).get(0);
	}

	public List<Genre> getAll()
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_genre";
		return BasicUtil.getAll(conn, Genre.class, sql);
	}

}
