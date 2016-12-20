package com.kailiang.lms.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.kailiang.lms.bean.Branch;
import com.kailiang.lms.utils.BasicUtil;

public class BranchDao implements Dao<Branch> {
	
	private Connection conn = null;
	private Integer pageNo;
	private final Integer pageSize = 10;

	public BranchDao(Connection conn) {
		this.conn = conn;
	}

	public void add(Branch t) throws SQLException {
		String sql = "INSERT INTO tbl_library_branch(branchId, branchName, branchAddress) " + "VALUES (?,?,?)";
		BasicUtil.common(conn, sql, t.getBranchId(), t.getBranchName(), t.getBranchAddress());

	}

	public void update(Branch t, String property, Object value)
			throws SQLException, IllegalAccessException, InvocationTargetException {
		String sql = "UPDATE tbl_library_branch SET " + property + " = ? WHERE branchId = ?";
		BasicUtil.common(conn, sql, value, t.getBranchId());

	}

	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM tbl_library_branch WHERE branchId = ?";
		BasicUtil.common(conn, sql, id);

	}

	public List<Branch> getList(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_library_branch WHERE " + property + " = ?";
		return BasicUtil.getList(conn, Branch.class, sql, value);
	}

	public Branch get(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		return getList(property, value).get(0);
	}

	public List<Branch> getAll()
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		String sql = "SELECT * FROM tbl_library_branch";
		
		if(pageNo != null && pageNo > 0) {
			int limit = (pageNo - 1) * pageSize;
			
			sql += " LIMIT " + limit + ", " + pageSize;
		}
		
		return BasicUtil.getAll(conn, Branch.class, sql);
	}
	
	public int getCount() throws InstantiationException, IllegalAccessException, InvocationTargetException, SQLException {
		String sql = "SELECT * FROM tbl_library_branch";
		return BasicUtil.getAll(conn, Branch.class, sql).size();
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

}
