package com.kailiang.lms.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.kailiang.lms.bean.BookLoans;
import com.kailiang.lms.utils.BasicUtil;

public class BookLoansDao implements Dao<BookLoans> {

	private Connection conn = null;

	public BookLoansDao(Connection conn) {
		this.conn = conn;
	}

	public void add(BookLoans t) throws SQLException {
		String sql = "INSERT INTO tbl_book_loans(bookId, branchId, cardNo, dateOut, dueDate, dateIn) "
				+ "VALUES (?,?,?,?,?,?)";
		BasicUtil.common(conn, sql, t.getBookId(), t.getBranchId(), t.getCardNo(), t.getDateOut(),
		t.getDueDate(), t.getDateIn());
	}
	
	public void update(BookLoans t, String property, Object value)
			throws SQLException, IllegalAccessException, InvocationTargetException {
		String sql = "UPDATE tbl_book_loans SET " + property + " = ? WHERE bookId = ?";
		BasicUtil.common(conn, sql, value, t.getBookId());
	}
	public void update(BookLoans t, String property, Object value, Object value2, Object value3, Object value4)
			throws SQLException, IllegalAccessException, InvocationTargetException {
		String sql = "UPDATE tbl_book_loans SET " + property + " = ? WHERE bookId = ? AND branchId = ? AND cardNo = ?";
		BasicUtil.common(conn, sql, value, t.getBookId(), t.getBranchId(), t.getCardNo());
	}
	
	public void delete(int bookId, int branchId, int cardNo) throws SQLException{
		String sql = "DELETE FROM tbl_book_loans WHERE bookId = ? AND branchId = ? AND cardNo = ?";
		BasicUtil.common(conn, sql, bookId, branchId, cardNo);
	}

	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM tbl_book_loans WHERE bookId = ?";
		BasicUtil.common(conn, sql, id);
	}

	public List<BookLoans> getList(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_book_loans WHERE " + property + " = ?";
		return BasicUtil.getList(conn, BookLoans.class, sql, value);
	}

	public BookLoans get(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException{
		List<BookLoans> bl = getList(property, value); 
		if(bl != null && bl.size() > 0) {
			return bl.get(0);
		}
		return null;
	}
	
	public BookLoans getSingle(String property1, String property2, String property3, Object value1, Object value2, Object value3)
			throws InstantiationException, IllegalAccessException, InvocationTargetException, SQLException {
		String sql = "SELECT * FROM tbl_book_loans WHERE " + property1 + " = ? AND " + property2 + " = ? AND " + property3 + " = ?";
		return BasicUtil.getSingle(conn, BookLoans.class, sql, value1, value2, value3);
	}

	public List<BookLoans> getAll()
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_book_loans";
		return BasicUtil.getAll(conn, BookLoans.class, sql);
	}

	public List<BookLoans> get(String property1, String property2, Object value1, Object value2)
			throws InstantiationException, IllegalAccessException, InvocationTargetException, SQLException {
		String sql = "SELECT * FROM tbl_book_loans WHERE " + property1 + " = ? AND " + property2 + " = ?";
		return BasicUtil.getList(conn, BookLoans.class, sql, value1, value2);
	}
	public List<BookLoans> get(String property1, String property2, String property3, Object value1, Object value2, Object value3)
			throws InstantiationException, IllegalAccessException, InvocationTargetException, SQLException {
		String sql = "SELECT * FROM tbl_book_loans WHERE " + property1 + " = ? AND " + property2 + " = ? AND " + property3 + " = ?";
		return BasicUtil.getList(conn, BookLoans.class, sql, value1, value2, value3);
	}

}
