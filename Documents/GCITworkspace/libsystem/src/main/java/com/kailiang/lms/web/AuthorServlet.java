package com.kailiang.lms.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kailiang.lms.bean.Author;
import com.kailiang.lms.bean.Book;
import com.kailiang.lms.dao.AuthorDao;
import com.kailiang.lms.dao.BookDao;

@WebServlet({ "/pages/author/addAuthor", "/pages/author/findAuthor", "/pages/author/allAuthor",
		"/pages/author/updateAuthor", "/pages/author/deleteAuthor", "/pages/author/queryAuthor" })
public class AuthorServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		switch (getUrl()) {
		case "/pages/author/addAuthor":
			return addAuthor();
		case "/pages/author/allAuthor":
			return allAuthor();
		case "/pages/author/findAuthor":
			return findAuthor();
		case "/pages/author/updateAuthor":
			return updateAuthor();
		case "/pages/author/deleteAuthor":
			return deleteAuthor();

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
		if (this.getUrl().equals("/pages/author/queryAuthor")) {
			queryAuthor();
		}
	}

	private void queryAuthor() {
		String authorName = req.getParameter("title");
		System.out.println(authorName);
		try {
			List<Author> list = new AuthorDao(conn).getQuery("%" + authorName + "%");
			req.setAttribute("authors", list);
			StringBuilder sb = new StringBuilder();
			sb.append("<table class='table'");
			sb.append("<tr><th>#</th><th>Author Name</th><th>Edit Author</th><th>Delete Author</th></tr>");
			for (Author author : list) {
				sb.append("<tr><td>" + (list.indexOf(author) + 1) + "</td>");
				sb.append("<td>" + author.getAuthorName() + "</td>");
				sb.append("<td><a class='btn btn-success' href='/libsystem/pages/author/detail.jsp?authorId="
						+ author.getAuthorId() + "&authorName=" + author.getAuthorName() + "'>Edit</a></td>");
				sb.append("<td><a class='btn btn-danger' href='/libsystem/pages/author/deleteAuthor?authorId="
						+ author.getAuthorId() + "&authorName=" + author.getAuthorName() + "'>Delete</a></td>");
			}
			resp.getWriter().append(sb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String updateAuthor() {
		String authorId = req.getParameter("authorId");
		String authorName = req.getParameter("authorName");
		AuthorDao aDao = new AuthorDao(conn);
		Author author;
		try {

			author = aDao.get("authorId", Integer.parseInt(authorId));
			aDao.update(author, "authorName", authorName);
			return "updateFinish.jsp";
		} catch (NumberFormatException | IllegalAccessException | InvocationTargetException | InstantiationException
				| SQLException e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}

	}

	private String deleteAuthor() {
		String authorId = req.getParameter("authorId");
		AuthorDao aDao = new AuthorDao(conn);
		try {
			aDao.delete(Integer.parseInt(authorId));
			return "deleteFinish.jsp";
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String findAuthor() {
		String authorName = req.getParameter("authorName");
		try {
			req.setAttribute("authors", new AuthorDao(conn).getList("authorName", authorName));
			return "list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String allAuthor() {
		try {
			req.setAttribute("authors", new AuthorDao(conn).getAll());
			return "list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String addAuthor() {
		String authorName = req.getParameter("authorName");
		AuthorDao authorDao = null;
		Author author = null;
		if (authorName == null || authorName.trim().length() == 0) {
			req.setAttribute("message", "Author' name cannot be empty!");
			return "authorMenu.jsp";
		}
		try {
			author = new Author();
			author.setAuthorName(authorName);
			authorDao = new AuthorDao(conn);
			authorDao.add(author);
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
		return "addFinish.jsp";
	}

}
