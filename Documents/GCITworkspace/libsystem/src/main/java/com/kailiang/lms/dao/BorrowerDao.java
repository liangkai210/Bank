package com.kailiang.lms.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.kailiang.lms.bean.Borrower;
import com.kailiang.lms.bean.Publisher;
import com.kailiang.lms.utils.BasicUtil;

public class BorrowerDao implements Dao<Borrower> {

	private Connection conn = null;

	public BorrowerDao(Connection conn) {
		this.conn = conn;
	}

	public void add(Borrower t) throws SQLException {
		String sql = "INSERT INTO tbl_borrower(cardNo, name, address, phone) VALUES (?,?,?,?)";
		BasicUtil.common(conn, sql, t.getCardNo(), t.getName(), t.getAddress(), t.getPhone());
	}

	public void update(Borrower t, String property, Object value)
			throws SQLException, IllegalAccessException, InvocationTargetException {
		String sql = "UPDATE tbl_borrower SET " + property + " = ? WHERE cardNo = ?";
		BasicUtil.common(conn, sql, value, t.getCardNo());

	}

	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM tbl_borrower WHERE cardNo = ?";
		BasicUtil.common(conn, sql, id);
	}

	public List<Borrower> getList(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_borrower WHERE " + property + " = ?";
		return BasicUtil.getList(conn, Borrower.class, sql, value);
	}

	public List<Borrower> get(String property1, String property2, Object value1, Object value2)
			throws InstantiationException, IllegalAccessException, InvocationTargetException, SQLException {
		String sql = "SELECT * FROM tbl_borrower WHERE " + property1 + " = ? AND " + property2 + " = ?";
		return BasicUtil.getList(conn, Borrower.class, sql, value1, value2);
	}
	public List<Borrower> getQuery(Object value) throws IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
		String sql = "SELECT * FROM tbl_borrower WHERE name like ?";
		return BasicUtil.getList(conn, Borrower.class, sql, value);
	}

	public Borrower get(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		return getList(property, value).get(0);
	}

	public List<Borrower> getAll()
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_borrower";
		return BasicUtil.getAll(conn, Borrower.class, sql);
	}

}
