package com.kailiang.lms.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kailiang.lms.bean.Genre;
import com.kailiang.lms.dao.GenreDao;

@WebServlet({ "/pages/genre/addGenre", "/pages/genre/queryGenre", "/pages/genre/allGenre", "/pages/genre/updateGenre",
		"/pages/genre/deleteGenre" })
public class GenreServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		switch (getUrl()) {
		case "/pages/genre/addGenre":
			return addGenre();
		case "/pages/genre/allGenre":
			return allGenre();
		case "/pages/genre/updateGenre":
			return updateGenre();
		case "/pages/genre/deleteGenre":
			return deleteGenre();

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
		if (this.getUrl().equals("/pages/genre/queryGenre")) {
			queryGenre();
		}
	}

	private void queryGenre() {
		String genre_name = req.getParameter("title");
		try {
			List<Genre> list = new GenreDao(conn).getQuery("%" + genre_name + "%");
			req.setAttribute("genres", list);
			StringBuilder sb = new StringBuilder();
			sb.append("<table class='table'");
			sb.append("<tr><th>#</th><th>Genre Name</th><th>Edit Genre</th><th>Delete Genre</th></tr>");
			for (Genre genre : list) {
				sb.append("<tr><td>" + (list.indexOf(genre) + 1) + "</td>");
				sb.append("<td>" + genre.getGenre_name() + "</td>");
				sb.append("<td><a class='btn btn-success' href='/libsystem/pages/genre/detail.jsp?genre_id="
						+ genre.getGenre_id() + "&genre_name=" + genre.getGenre_name() + "'>Edit</a></td>");
				sb.append("<td><a class='btn btn-danger' href='/libsystem/pages/genre/deleteGenre?genre_id="
						+ genre.getGenre_id() + "&genre_name=" + genre.getGenre_name() + "'>Delete</a></td>");
			}
			resp.getWriter().append(sb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String deleteGenre() {
		String genre_id = req.getParameter("genre_id");
		GenreDao gDao = new GenreDao(conn);
		try {
			gDao.delete(Integer.parseInt(genre_id));
			return "deleteFinish.jsp";
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String updateGenre() {
		String genreId = req.getParameter("genre_id");
		String genreName = req.getParameter("genre_name");
		System.out.println(genreId);
		System.out.println(genreName);
		GenreDao gDao = new GenreDao(conn);
		Genre genre;
		try {

			genre = gDao.get("genre_id", Integer.parseInt(genreId));
			gDao.update(genre, "genre_name", genreName);
			return "updateFinish.jsp";
		} catch (NumberFormatException | IllegalAccessException | InvocationTargetException | InstantiationException
				| SQLException e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}

	}

	private String allGenre() {
		try {
			req.setAttribute("genres", new GenreDao(conn).getAll());
			return "list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String addGenre() {
		String genreName = req.getParameter("genre_name");
		GenreDao genreDao = null;
		Genre genre = null;
		if (genreName == null || genreName.trim().length() == 0) {
			req.setAttribute("message", "Genre' name cannot be empty!");
			return "genreMenu.jsp";
		}
		try {
			genre = new Genre();
			genre.setGenre_name(genreName);
			genreDao = new GenreDao(conn);
			genreDao.add(genre);
			return "addFinish.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

}
