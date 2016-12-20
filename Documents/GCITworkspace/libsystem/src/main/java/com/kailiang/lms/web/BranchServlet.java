package com.kailiang.lms.web;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import com.kailiang.lms.bean.Book;
import com.kailiang.lms.bean.BookCopies;
import com.kailiang.lms.bean.Branch;
import com.kailiang.lms.dao.BookCopiesDao;
import com.kailiang.lms.dao.BookDao;
import com.kailiang.lms.dao.BranchDao;

@WebServlet({ "/pages/branch/addBranch", "/pages/branch/findBranch", "/pages/branch/allBranch",
		"/pages/branch/updateBranch", "/pages/branch/addBookCopies", "/pages/branch/showBookCopies",
		"/pages/branch/copyDetail", "/pages/branch/choice" })
public class BranchServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		switch (getUrl()) {
		case "/pages/branch/addBranch":
			return addBranch();
		case "/pages/branch/allBranch":
			return allBranch();
		case "/pages/branch/findBranch":
			return findBranch();
		case "/pages/branch/updateBranch":
			return updateBranch();
		case "/pages/branch/addBookCopies":
			return addBookCopies();
		case "/pages/branch/copyDetail":
			return copyDetail();
		case "/pages/branch/choice":
			return choice();

		default:
			break;
		}
		return null;
	}

	private String choice() {
		String branchId = req.getParameter("branchId");
		BranchDao brDao = new BranchDao(conn);
		try {
			Branch branch = brDao.get("branchId", branchId);
			String branchName = branch.getBranchName();
			String branchAddress = branch.getBranchAddress();
			req.setAttribute("branchId", branchId);
			req.setAttribute("branchName", branchName);
			req.setAttribute("branchAddress", branchAddress);
			
			return "choice.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String addBookCopies() {
		String branchId = req.getParameter("branchId");
		String[] bookId = req.getParameterValues("bookId");
		String[] noOfCopies = req.getParameterValues("noOfCopies");
		BookCopiesDao bcd = new BookCopiesDao(conn);
		try {
			for(int i = 0; i < bookId.length; i++){
				if(bcd.get("bookId", bookId[i]).getNoOfCopies() != Integer.parseInt(noOfCopies[i])){
				bcd.update(Integer.parseInt(bookId[i]), Integer.parseInt(branchId), Integer.parseInt(noOfCopies[i]));
				}else{
					bcd.update(Integer.parseInt(bookId[i]), Integer.parseInt(branchId), bcd.get("bookId", bookId[i]).getNoOfCopies());
				}
			}
			return "addCopySuc.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String copyDetail() {
		String branchId = req.getParameter("branchId");
		System.out.println(branchId);
		BookCopiesDao bcd = new BookCopiesDao(conn);
		BookDao bd = new BookDao(conn);
		try {
			List<BookCopies> list = bcd.getList("branchId", Integer.parseInt(branchId));
			Map<Integer, Book> map = new HashMap<>();
			for (BookCopies bookCopy : list) {
				map.put(bookCopy.getBookId(), bd.get("bookId", bookCopy.getBookId()));
			}
			req.setAttribute("list", list);
			req.setAttribute("map", map);
			return "updateCopies.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String updateBranch() {
		String branchId = req.getParameter("branchId");
		String branchName = req.getParameter("branchName");
		String branchAddress = req.getParameter("branchAddress");
		String selection = req.getParameter("operator");
		BranchDao brDao = new BranchDao(conn);
		if (selection.equals("update")) {
			Branch branch;
			try {
				branch = brDao.get("branchId", Integer.parseInt(branchId));
				brDao.update(branch, "branchName", branchName);
				brDao.update(branch, "branchAddress", branchAddress);
			} catch (NumberFormatException | IllegalAccessException | InvocationTargetException | InstantiationException
					| SQLException e) {
				e.printStackTrace();
			}
		}
		if (selection.equals("delete")) {
			try {
				brDao.delete(Integer.parseInt(branchId));
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		}
		req.setAttribute("selection", selection);
		return "operations.jsp";
	}

	private String findBranch() {
		String branchName = req.getParameter("branchName");
		try {
			req.setAttribute("branches", new BranchDao(conn).getList("branchName", branchName));
			return "branchDetail.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String allBranch() {
		try {
			
			int pageNo = Integer.parseInt(req.getParameter("pageNo"));
			BranchDao branchDao = new BranchDao(conn);
			branchDao.setPageNo(pageNo);
			
			List<Branch> branches = branchDao.getAll();
			req.setAttribute("branches", branches);
			req.setAttribute("count", branchDao.getCount());
			req.setAttribute("pageNo", pageNo);
			req.setAttribute("pageSize", branchDao.getPageSize());
			return "list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String addBranch() {
		String branchName = req.getParameter("branchName");
		String branchAddress = req.getParameter("branchAddress");
		BranchDao branchDao = null;
		Branch branch = null;
		if (branchName == null || branchName.trim().length() == 0) {
			req.setAttribute("message", "Branch' name cannot be empty!");
			return "branchMenu.jsp";
		}
		try {
			branch = new Branch();
			branch.setBranchName(branchName);
			branch.setBranchAddress(branchAddress);
			branchDao = new BranchDao(conn);
			branchDao.add(branch);
			return "addFinish.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

}
