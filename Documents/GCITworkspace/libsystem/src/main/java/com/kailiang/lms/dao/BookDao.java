package com.kailiang.lms.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.kailiang.lms.bean.Book;
import com.kailiang.lms.utils.BasicUtil;

public class BookDao implements Dao<Book> {

	private Connection conn = null;

	public BookDao(Connection conn) {
		this.conn = conn;
	}

	public Integer addWithId(Book t) throws SQLException {
		String sql = "INSERT INTO tbl_book(title, pubId) VALUES (?,?)";
		return BasicUtil.addWithId(conn, sql, t.getTitle(), t.getPubId());
	}

	public void add(Book t) throws SQLException {
		System.out.println(t.getTitle() + " " + t.getPubId());
		String sql = "INSERT INTO tbl_book(title, pubId) VALUES (?,?)";
		BasicUtil.common(conn, sql, t.getTitle(), t.getPubId());
	}

	public void update(Book t, String property, Object value) throws SQLException {
		String sql = "UPDATE tbl_book SET " + property + " = ? WHERE bookId = ?";
		BasicUtil.common(conn, sql, value, t.getBookId());

	}
	public Integer updateWithId(Book t, String property, Object value) throws SQLException {
		String sql = "UPDATE tbl_book SET " + property + " = ? WHERE bookId = ?";
		return BasicUtil.addWithId(conn, sql, value, t.getBookId());
		
	}

	public void delete(int id) throws SQLException{
		String sql = "DELETE FROM tbl_book WHERE bookId = ?";
		BasicUtil.common(conn, sql, id);
	}

	public List<Book> getList(String property, Object value) throws IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
		String sql = "SELECT * FROM tbl_book WHERE " + property + " = ?";
		return BasicUtil.getList(conn, Book.class, sql, value);
	}
	public List<Book> getQuery(Object value) throws IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
		String sql = "SELECT * FROM tbl_book WHERE title like ?";
		return BasicUtil.getList(conn, Book.class, sql, value);
	}
	
	public Book getSingle(String property, Object value){
		String sql = "SELECT * FROM tbl_book WHERE " + property + " = ?";
		return BasicUtil.getSingle(conn, Book.class, sql, value);
	}

	public Book get(String property, Object value) throws IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
		return getList(property, value).get(0);
	}

	public List<Book> getAll() throws InstantiationException, IllegalAccessException, InvocationTargetException, SQLException {
		String sql = "SELECT * FROM tbl_book";
		return BasicUtil.getAll(conn, Book.class, sql);
	}

}
