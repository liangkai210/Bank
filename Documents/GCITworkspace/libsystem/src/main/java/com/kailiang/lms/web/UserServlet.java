package com.kailiang.lms.web;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.kailiang.lms.bean.Borrower;
import com.kailiang.lms.dao.BorrowerDao;

@WebServlet({ "/verification" })
public class UserServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		switch (getUrl()) {
		case "/verification":
			return verification();

		default:
			break;
		}
		return null;
	}

	private String verification() {
		String path = req.getContextPath();
		String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path + "/";
		String type = req.getParameter("type");
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		if ("admin".equals(type)) {
			if ("1234".equals(password)) {
				return "pages/user/adminMenu.jsp";
			} else {
				return INTERNAL_SERVER_ERROR;
			}
		} else if ("librarian".equals(type)) {
			if ("12345".equals(password)) {
				return "pages/user/librarianMenu.jsp";
			} else {
				return INTERNAL_SERVER_ERROR;
			}
		} else {
			BorrowerDao bd = new BorrowerDao(conn);
			List<Borrower> borrowers;
			try {
				borrowers = bd.get("cardNo", "name", Integer.parseInt(password), username);
				if (borrowers == null || borrowers.size() == 0) {
					return INTERNAL_SERVER_ERROR;
				} else {
					req.setAttribute("cardNo", borrowers.get(0).getCardNo());
					return "pages/borrower/borrowerMenu.jsp";
				}
			} catch (NumberFormatException | InstantiationException | IllegalAccessException | InvocationTargetException
					| SQLException e) {
				e.printStackTrace();
				return INTERNAL_SERVER_ERROR;
			}
		}
	}
}