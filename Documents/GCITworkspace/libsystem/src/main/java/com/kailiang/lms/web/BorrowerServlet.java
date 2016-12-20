package com.kailiang.lms.web;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kailiang.lms.bean.Book;
import com.kailiang.lms.bean.BookCopies;
import com.kailiang.lms.bean.BookLoans;
import com.kailiang.lms.bean.Branch;
import com.kailiang.lms.bean.Borrower;
import com.kailiang.lms.dao.BookCopiesDao;
import com.kailiang.lms.dao.BookDao;
import com.kailiang.lms.dao.BookLoansDao;
import com.kailiang.lms.dao.BorrowerDao;
import com.kailiang.lms.dao.BranchDao;
import com.kailiang.lms.dao.BorrowerDao;
import com.kailiang.lms.dao.BorrowerDao;

@WebServlet({ "/pages/borrower/branches", "/pages/borrower/bookCopyList", "/pages/borrower/checkout",
		"/pages/borrower/returnBook", "/pages/borrower/returnFinish", })
public class BorrowerServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		switch (getUrl()) {
		case "/pages/borrower/branches":
			return listBranches();
		case "/pages/borrower/bookCopyList":
			return bookCopyList();
		case "/pages/borrower/checkout":
			return checkout();
		case "/pages/borrower/returnBook":
			return returnBook();
		case "/pages/borrower/returnFinish":
			return returnFinish();
		default:
			break;
		}

		return null;
	}

	private String returnFinish() {
		String[] bookId = req.getParameterValues("bookId");
		String branchId = req.getParameter("branchId");
		System.out.println(branchId);
		BookLoansDao bookLDao = new BookLoansDao(conn);
		BookCopiesDao bcDao = new BookCopiesDao(conn);

		try {
			// List<BookLoans> list = new ArrayList<>();
			for (int i = 0; i < bookId.length; i++) {
				bookLDao.delete(Integer.parseInt(bookId[i]));
				BookCopies bookCopies = bcDao.get("bookId", Integer.parseInt(bookId[i]));
				bcDao.update(Integer.parseInt(bookId[i]), Integer.parseInt(branchId), bookCopies.getNoOfCopies() + 1);
			}
			return "/pages/borrower/returnFinish.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String returnBook() {
		String cardNo = req.getParameter("cardNo");
		Map<Integer, Book> map = null;
		BookLoansDao bookLoansDao = null;
		BookDao bookDao = null;
		try {
			bookLoansDao = new BookLoansDao(conn);
			bookDao = new BookDao(conn);
			map = new HashMap<>();
			List<BookLoans> list = bookLoansDao.getList("cardNo", Integer.parseInt(cardNo));
			for (BookLoans bookLoan : list) {
				map.put(bookLoan.getBookId(), bookDao.get("bookId", bookLoan.getBookId()));
			}
			req.setAttribute("list", list);
			req.setAttribute("map", map);
			return "/pages/borrower/returnBookList.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String checkout() {
		String cardNo = req.getParameter("cardNo");
		String branchId = req.getParameter("branchId");
		String[] bookId = req.getParameterValues("bookId");
		String noOfCopies = req.getParameter("noOfCopies");
		BookLoansDao bookloansDao = null;
		BookCopiesDao bcDao = null;
		BookLoans bookLoans = null;
		try {
			bookloansDao = new BookLoansDao(conn);
			bcDao = new BookCopiesDao(conn);
			bookLoans = new BookLoans();
			for (int i = 0; i < bookId.length; i++) {
				bcDao.update(Integer.parseInt(bookId[i]), Integer.parseInt(branchId), Integer.parseInt(noOfCopies) - 1);
				bookLoans.setBookId(Integer.parseInt(bookId[i]));
				bookLoans.setBranchId(Integer.parseInt(branchId));
				bookLoans.setCardNo(Integer.parseInt(cardNo));
				bookLoans.setDateOut(new Date(System.currentTimeMillis()));
				bookLoans.setDueDate(new Date(System.currentTimeMillis() + 7 * 24 * 3600 * 1000));
				;
				bookLoans.setDateIn(null);
				bookloansDao.add(bookLoans);
			}
			Long date = System.currentTimeMillis();
			Long dueDate = System.currentTimeMillis() + 7 * 24 * 3600 * 1000;

			req.setAttribute("dateOut", String.valueOf(date));
			req.setAttribute("dueDate", String.valueOf(dueDate));
			req.setAttribute("cardNo", cardNo);
			req.setAttribute("branchId", branchId);
			req.setAttribute("bookId", bookId);

			// TODO Re-check to borrow again!
			return "/pages/borrower/checkout.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String bookCopyList() {
		String branchId = req.getParameter("branchId");
		BookCopiesDao bcd = null;
		BookDao bd = null;
		try {
			bcd = new BookCopiesDao(conn);
			bd = new BookDao(conn);
			List<BookCopies> bookCopyList = bcd.getList("branchId", Integer.parseInt(branchId));
			Map<Integer, Book> map = new HashMap<>();
			for (BookCopies bookCopy : bookCopyList) {
				map.put(bookCopy.getBookId(), bd.get("bookId", bookCopy.getBookId()));
			}
			req.setAttribute("list", bookCopyList);
			req.setAttribute("map", map);
			req.setAttribute("branchId", branchId);
			Integer cardNo = Integer.parseInt(req.getParameter("cardNo"));
			req.setAttribute("cardNo", cardNo);
			return "/pages/borrower/bookCopies.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String listBranches() {
		BranchDao brDao = new BranchDao(conn);
		try {
			List<Branch> list = brDao.getAll();
			Integer cardNo = Integer.parseInt(req.getParameter("cardNo"));
			req.setAttribute("cardNo", cardNo);
			req.setAttribute("list", list);
			return "/pages/borrower/branchList.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

}
