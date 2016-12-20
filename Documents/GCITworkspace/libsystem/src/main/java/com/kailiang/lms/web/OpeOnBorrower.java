package com.kailiang.lms.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kailiang.lms.bean.BookLoans;
import com.kailiang.lms.bean.Borrower;
import com.kailiang.lms.dao.BookLoansDao;
import com.kailiang.lms.dao.BorrowerDao;

@WebServlet({ "/pages/borrowers/addBorrower", "/pages/borrowers/findBorrower", "/pages/borrowers/allBorrower",
		"/pages/borrowers/updateBorrower", "/pages/borrowers/detail", "/pages/borrowers/showLoans",
		"/pages/borrowers/updateDate", "/pages/borrowers/queryBorrower" })
public class OpeOnBorrower extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		switch (getUrl()) {
		case "/pages/borrowers/addBorrower":
			return addBorrower();
		case "/pages/borrowers/allBorrower":
			return allBorrower();
		case "/pages/borrowers/findBorrower":
			return findBorrower();
		case "/pages/borrowers/updateBorrower":
			return updateBorrower();
		case "/pages/borrowers/detail":
			return detail();
		case "/pages/borrowers/updateDate":
			return updateDate();
		case "/pages/borrowers/showLoans":
			return showLoans();
		case "/pages/borrowers/deleteBorrower":
			return deleteBorrower();

		default:
			break;
		}
		return null;
	}

	private String deleteBorrower() {
		String cardNo = req.getParameter("cardNo");
		BorrowerDao pDao = new BorrowerDao(conn);
		try {
			pDao.delete(Integer.parseInt(cardNo));
			return "deleteFinish.jsp";
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.req = req;
		this.resp = resp;

		if (req.getParameter("isAjax") == null) {
			dispatch(req, resp, this.execute());
		} else {
			executeAjax();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	private void executeAjax() {
		if (this.getUrl().equals("/pages/borrowers/queryBorrower")) {
			queryBorrower();
		}
	}

	private void queryBorrower() {
		String name = req.getParameter("title");
		try {
			List<Borrower> list = new BorrowerDao(conn).getQuery("%" + name + "%");
			req.setAttribute("borrowers", list);
			StringBuilder sb = new StringBuilder();
			sb.append("<table class='table'");
			sb.append(
					"<tr><th>#</th><th>Borrower Name</th><th>Borrower Address</th><th>Borrower Phone</th><th>Edit Borrower</th><th>Delete Borrower</th></tr>");
			for (Borrower borrower : list) {
				sb.append("<tr><td>" + (list.indexOf(borrower) + 1) + "</td>");
				sb.append("<td>" + borrower.getName() + "</td>");
				sb.append("<td>" + borrower.getAddress() + "</td>");
				sb.append("<td>" + borrower.getPhone() + "</td>");
				sb.append("<td><a class='btn btn-success' href='/libsystem/pages/borrowers/detail.jsp?cardNo="
						+ borrower.getCardNo() + "&name=" + borrower.getName() + "&address="
						+ borrower.getAddress() + "&phone=" + borrower.getPhone() + "'>Edit</a></td>");
				sb.append("<td><a class='btn btn-danger' href='/libsystem/pages/borrowers/deleteBorrower?cardNo="
						+ borrower.getCardNo() + "&name=" + borrower.getName() + "'>Delete</a></td>");
			}
			resp.getWriter().append(sb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String showLoans() {
		BorrowerDao brDao = new BorrowerDao(conn);
		BookLoansDao blDao = new BookLoansDao(conn);
		try {
			List<Borrower> list = brDao.getAll();
			System.out.println(list.size());
			Map<Integer, BookLoans> map = new HashMap<>();
			for (Borrower borrower : list) {
				BookLoans bl = blDao.get("cardNo", borrower.getCardNo());
				if (bl != null) {
					map.put(borrower.getCardNo(), bl);
				}
			}
			req.setAttribute("list", list);
			req.setAttribute("map", map);
			System.out.println(list);
			System.out.println(map);
			return "/pages/borrowers/record.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String updateDate(){
		String cardNo = req.getParameter("cardNo");
		String bookId = req.getParameter("bookId");
		String branchId = req.getParameter("branchId");
		String newDueDate = req.getParameter("dueDate");
		BookLoansDao blDao = new BookLoansDao(conn);
		java.util.Date date = new Date(System.currentTimeMillis());
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			date = format.parse(newDueDate);
			java.sql.Date dueDate = new Date(date.getTime());
			BookLoans bookLoans = blDao.getSingle("cardNo", "bookId", "branchId", Integer.parseInt(cardNo),
					Integer.parseInt(bookId), Integer.parseInt(branchId));
			blDao.update(bookLoans, "dueDate", dueDate, Integer.parseInt(bookId), Integer.parseInt(branchId),
					Integer.parseInt(cardNo));
			return "/pages/borrowers/successOverride.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String detail() {
		String cardNo = req.getParameter("cardNo");
		BorrowerDao borDao = new BorrowerDao(conn);
		try {
			Borrower borrower = borDao.get("cardNo", cardNo);
			String name = borrower.getName();
			String address = borrower.getAddress();
			String phone = borrower.getPhone();
			req.setAttribute("name", name);
			req.setAttribute("address", address);
			req.setAttribute("phone", phone);
			req.setAttribute("cardNo", cardNo);
			return "detail.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String updateBorrower() {
		String cardNo = req.getParameter("cardNo");
		String name = req.getParameter("name");
		String address = req.getParameter("address");
		BorrowerDao brDao = new BorrowerDao(conn);
			Borrower borrower;
			try {

				borrower = brDao.get("cardNo", Integer.parseInt(cardNo));
				brDao.update(borrower, "name", name);
				brDao.update(borrower, "address", address);
				return "/pages/borrowers/updateFinish.jsp";
			} catch (NumberFormatException | IllegalAccessException | InvocationTargetException | InstantiationException
					| SQLException e) {
				e.printStackTrace();
				return INTERNAL_SERVER_ERROR;
		}
	}

	private String findBorrower() {
		String name = req.getParameter("name");
		try {
			req.setAttribute("borrowers", new BorrowerDao(conn).getList("name", name));
			return "list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String allBorrower() {
		try {
			req.setAttribute("borrowers", new BorrowerDao(conn).getAll());
			return "/pages/borrowers/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String addBorrower() {
		String name = req.getParameter("name");
		String address = req.getParameter("address");
		String phone = req.getParameter("phone");
		BorrowerDao brDao = null;
		Borrower borrower = null;
		if (name == null || name.trim().length() == 0) {
			req.setAttribute("message", "Borrower' name cannot be empty!");
			return "borMenu.jsp";
		}
		if (address == null || address.trim().length() == 0) {
			req.setAttribute("message", "Borrower' address cannot be empty!");
			return "borMenu.jsp";
		}
		if (phone == null || phone.trim().length() == 0) {
			req.setAttribute("message", "Borrower's phone cannot be empty!");
			return "borMenu.jsp";
		}
		try {
			borrower = new Borrower();
			borrower.setName(name);
			borrower.setAddress(address);
			borrower.setPhone(phone);
			brDao = new BorrowerDao(conn);
			brDao.add(borrower);
			return "addFinish.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

}
