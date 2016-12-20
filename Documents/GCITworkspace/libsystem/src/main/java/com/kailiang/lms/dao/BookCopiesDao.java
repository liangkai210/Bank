package com.kailiang.lms.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.kailiang.lms.bean.BookCopies;
import com.kailiang.lms.utils.BasicUtil;

public class BookCopiesDao implements Dao<BookCopies> {
	
	private Connection conn = null;
	
	public BookCopiesDao(Connection conn){
		this.conn = conn;
	}

	public void add(BookCopies t) throws SQLException {
		String sql = "INSERT INTO tbl_book_copies(bookId, branchId, noOfCopies) VALUES (?,?,?)";
		BasicUtil.common(conn, sql, t.getBookId(), t.getBranchId(), t.getNoOfCopies());

	}

	public void update(BookCopies t, String property, Object value) throws SQLException {

		String sql = "UPDATE tbl_book_copies SET " + property + " = ? WHERE bookId = ? AND branchId = ?";
		BasicUtil.common(conn, sql, value, t.getBookId(), t.getBranchId());

	}

	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM tbl_book_copies WHERE bookId = ?";
		BasicUtil.common(conn, sql, id);

	}

	public void update(int bookId, int branchId, int copies) {
		String sql = "UPDATE tbl_book_copies SET noOfCopies = ? WHERE bookId = ? AND branchId = ?";
		try {
			BasicUtil.common(conn, sql, copies, bookId, branchId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<BookCopies> getList(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_book_copies WHERE " + property + " = ?";
		return BasicUtil.getList(conn, BookCopies.class, sql, value);
	}

	public BookCopies get(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		return getList(property, value).get(0);
	}

	public List<BookCopies> getAll()
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_book_copies";
		return BasicUtil.getAll(conn, BookCopies.class, sql);
	}

}
