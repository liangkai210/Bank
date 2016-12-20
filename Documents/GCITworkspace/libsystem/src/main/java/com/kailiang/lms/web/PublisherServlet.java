package com.kailiang.lms.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kailiang.lms.bean.Publisher;
import com.kailiang.lms.bean.Publisher;
import com.kailiang.lms.dao.GenreDao;
import com.kailiang.lms.dao.PublisherDao;
import com.kailiang.lms.dao.PublisherDao;

@WebServlet({ "/pages/publisher/addPublisher", "/pages/publisher/allPublisher", "/pages/publisher/updatePublisher",
		"/pages/publisher/deletePublisher", "/pages/publisher/queryPublisher", "/pages/publisher/detail" })
public class PublisherServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		switch (getUrl()) {
		case "/pages/publisher/addPublisher":
			return addPublisher();
		case "/pages/publisher/allPublisher":
			return allPublisher();
		case "/pages/publisher/updatePublisher":
			return updatePublisher();
		case "/pages/publisher/detail":
			return detail();
		case "/pages/publisher/deletePublisher":
			return deletePublisher();

		default:
			break;
		}
		return null;
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
		if (this.getUrl().equals("/pages/publisher/queryPublisher")) {
			queryPublisher();
		}
	}

	private String deletePublisher() {
		String publisherId = req.getParameter("publisherId");
		PublisherDao pDao = new PublisherDao(conn);
		try {
			pDao.delete(Integer.parseInt(publisherId));
			return "deleteFinish.jsp";
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private void queryPublisher() {
		String publisherName = req.getParameter("title");
		try {
			List<Publisher> list = new PublisherDao(conn).getQuery("%" + publisherName + "%");
			req.setAttribute("publishers", list);
			StringBuilder sb = new StringBuilder();
			sb.append("<table class='table'");
			sb.append("<tr><th>#</th><th>Publisher Name</th><th>Publisher Address</th><th>Publisher Phone</th><th>Edit Publisher</th><th>Delete Publisher</th></tr>");
			for (Publisher publisher : list) {
				sb.append("<tr><td>" + (list.indexOf(publisher) + 1) + "</td>");
				sb.append("<td>" + publisher.getPublisherName() + "</td>");
				sb.append("<td>" + publisher.getPublisherAddress() + "</td>");
				sb.append("<td>" + publisher.getPublisherPhone() + "</td>");
				sb.append("<td><a class='btn btn-success' href='/libsystem/pages/publisher/detail.jsp?publisherId="
						+ publisher.getPublisherId() + "&publisherName=" + publisher.getPublisherName()
						+ "&publisherAddress=" + publisher.getPublisherAddress() + "&publisherPhone="
						+ publisher.getPublisherPhone() + "'>Edit</a></td>");
				sb.append("<td><a class='btn btn-danger' href='/libsystem/pages/publisher/deletePublisher?publisherId="
						+ publisher.getPublisherId() + "&publisherName=" + publisher.getPublisherName()
						+ "'>Delete</a></td>");
			}
			resp.getWriter().append(sb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String detail() {
		String publisherId = req.getParameter("publisherId");
		PublisherDao pubDao = new PublisherDao(conn);
		try {
			Publisher publisher = pubDao.get("publisherId", publisherId);
			String publisherName = publisher.getPublisherName();
			String publisherAddress = publisher.getPublisherAddress();
			String publisherPhone = publisher.getPublisherPhone();
			req.setAttribute("publisherName", publisherName);
			req.setAttribute("publisherAddress", publisherAddress);
			req.setAttribute("publisherPhone", publisherPhone);
			req.setAttribute("publisherId", publisherId);
			return "detail.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String updatePublisher() {
		String publisherId = req.getParameter("publisherId");
		String publisherName = req.getParameter("publisherName");
		String publisherAddress = req.getParameter("publisherAddress");
		String publisherPhone = req.getParameter("publisherPhone");
		PublisherDao pubDao = new PublisherDao(conn);
		Publisher publisher;
		try {

			publisher = pubDao.get("publisherId", Integer.parseInt(publisherId));
			pubDao.update(publisher, "publisherName", publisherName);
			pubDao.update(publisher, "publisherAddress", publisherAddress);
			pubDao.update(publisher, "publisherPhone", publisherPhone);
			return "updateFinish.jsp";
		} catch (NumberFormatException | IllegalAccessException | InvocationTargetException | InstantiationException
				| SQLException e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}

	}

	private String allPublisher() {
		try {
			req.setAttribute("publishers", new PublisherDao(conn).getAll());
			return "list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String addPublisher() {
		String publisherName = req.getParameter("publisherName");
		String publisherAddress = req.getParameter("publisherAddress");
		String publisherPhone = req.getParameter("publisherPhone");
		PublisherDao pubDao = null;
		Publisher publisher = null;
		if (publisherName == null || publisherName.trim().length() == 0) {
			req.setAttribute("message", "Publihser' name cannot be empty!");
			return "publisherMenu.jsp";
		}
		if (publisherAddress == null || publisherAddress.trim().length() == 0) {
			req.setAttribute("message", "Publihser' address cannot be empty!");
			return "publisherMenu.jsp";
		}
		if (publisherPhone == null || publisherPhone.trim().length() == 0) {
			req.setAttribute("message", "Publihser' phone cannot be empty!");
			return "publisherMenu.jsp";
		}
		try {
			publisher = new Publisher();
			publisher.setPublisherName(publisherName);
			publisher.setPublisherAddress(publisherAddress);
			publisher.setPublisherPhone(publisherPhone);
			pubDao = new PublisherDao(conn);
			pubDao.add(publisher);
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
		return "addFinish.jsp";
	}

}
