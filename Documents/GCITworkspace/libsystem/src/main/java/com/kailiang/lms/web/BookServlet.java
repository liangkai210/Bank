package com.kailiang.lms.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kailiang.lms.bean.Author;
import com.kailiang.lms.bean.Book;
import com.kailiang.lms.bean.BookAuthors;
import com.kailiang.lms.bean.BookCopies;
import com.kailiang.lms.bean.BookGenres;
import com.kailiang.lms.bean.Genre;
import com.kailiang.lms.bean.Publisher;
import com.kailiang.lms.dao.AuthorDao;
import com.kailiang.lms.dao.BookAuthorsDao;
import com.kailiang.lms.dao.BookCopiesDao;
import com.kailiang.lms.dao.BookDao;
import com.kailiang.lms.dao.BookGenresDao;
import com.kailiang.lms.dao.BranchDao;
import com.kailiang.lms.dao.GenreDao;
import com.kailiang.lms.dao.PublisherDao;
import com.kailiang.lms.utils.ConnectionManager;

@WebServlet({ "/pages/book/addBook", "/pages/book/saveBook", "/pages/book/queryBook", "/pages/book/updateBook",
		"/pages/book/findBook", "/pages/book/storeBookInDB", "/pages/book/deleteBook" })
public class BookServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	Connection conn = ConnectionManager.getConnection();

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

	@Override
	public String execute() {

		switch (getUrl()) {
		case "/pages/book/addBook":
			return addBook();
		case "/pages/book/saveBook":
			return saveBook();
		case "/pages/book/updateBook":
			return updateBook();
		case "/pages/book/findBook":
			return findBook();
		case "/pages/book/deleteBook":
			return deleteBook();
		case "/pages/book/storeBookInDB":
			return storeBookInDB();
		default:
			return PAGE_NOT_FOUND;
		}
	}

	private void executeAjax() {
		if (this.getUrl().equals("/pages/book/queryBook")) {
			queryBook();
		}
	}

	private String storeBookInDB() {
		String bookName = req.getParameter("title");
		String pubId = req.getParameter("publisherId");
		String[] authorIds = req.getParameterValues("authorId");
		String[] genreIds = req.getParameterValues("genre_id");
		String[] branchIds = req.getParameterValues("branchId");
		System.out.println(bookName);
		Book book = new Book();
		BookDao bookDao = new BookDao(conn);
		book.setTitle(bookName);
		book.setPubId(Integer.parseInt(pubId));
		try {
			Integer bookId = bookDao.addWithId(book);
			if (authorIds != null) {
				BookAuthorsDao baDao = new BookAuthorsDao(conn);
				for (int i = 0; i < authorIds.length; i++) {
					BookAuthors bookAuthors = new BookAuthors();
					bookAuthors.setBookId(bookId);
					bookAuthors.setAuthorId(Integer.parseInt(authorIds[i]));
					baDao.add(bookAuthors);
				}
			}
			if (genreIds != null && genreIds.length > 0) {
				BookGenresDao bgDao = new BookGenresDao(conn);
				for (int i = 0; i < genreIds.length; i++) {
					BookGenres bookGenres = new BookGenres();
					bookGenres.setBookId(bookId);
					bookGenres.setGenreId(Integer.parseInt(genreIds[i]));
					bgDao.add(bookGenres);
				}
			}
			if (branchIds != null) {
				BookCopiesDao bookCopiesDao = new BookCopiesDao(conn);
				BookCopies bookCopy = new BookCopies();
				for (String branId : branchIds) {
//					bookCopiesDao.update(bookId, Integer.parseInt(branId), 1);
					bookCopy.setBookId(bookId);
					bookCopy.setBranchId(Integer.parseInt(branId));
					bookCopy.setNoOfCopies(1);
					bookCopiesDao.add(bookCopy);
				}
			}
			return "addFinish.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			rollBack(conn);
			return INTERNAL_SERVER_ERROR;
		}
	}

	private void queryBook() {
		String title = req.getParameter("title");
		String bookId = req.getParameter("bookId");
		try {
			List<Book> list = new BookDao(conn).getQuery("%" + title + "%");
			req.setAttribute("books", list);
			StringBuilder sb = new StringBuilder();
			sb.append("<table class='table'");
			sb.append("<tr><th>#</th><th>Book Title</th><th>Edit Book</th><th>Delete Book</th></tr>");
			for (Book book : list) {
				sb.append("<tr><td>" + (list.indexOf(book) + 1) + "</td>");
				sb.append("<td>" + book.getTitle() + "</td>");
				sb.append("<td><a class='btn btn-success' href='/libsystem/pages/book/updateBook?bookId="
						+ book.getBookId() + "&bookTitle=" + book.getTitle() + "'>Edit</a></td>");
				sb.append("<td><a class='btn btn-danger' href='/libsystem/pages/book/deleteBook?bookId="
						+ book.getBookId() + "&bookTitle=" + book.getTitle() + "'>Delete</a></td>");
			}
			resp.getWriter().append(sb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String findBook() {
		String title = req.getParameter("title");
		if (title == null || title.trim().length() == 0) {
			req.setAttribute("message", "Title cannot be empty!");
			return "query.jsp";
		}
		try {
			req.setAttribute("books", new BookDao(conn).getList("title", title));
			return "list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String addBook() {
		String title = req.getParameter("title");
		if (title == null || title.trim().length() == 0) {
			req.setAttribute("message", "Title cannot be empty!");
			return "add.jsp";
		}
		try {
			req.setAttribute("title", title);
			System.out.println(title);
			req.setAttribute("publishers", new PublisherDao(conn).getAll());
			req.setAttribute("authors", new AuthorDao(conn).getAll());
			req.setAttribute("genres", new GenreDao(conn).getAll());
			req.setAttribute("branches", new BranchDao(conn).getAll());
			return "addRelated.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String saveBook() {
		String bookName = req.getParameter("bookTitle");
		String bId = req.getParameter("bookId");
		String pubId = req.getParameter("publisherId");
		String[] authorIds = req.getParameterValues("authorId");
		String[] genreIds = req.getParameterValues("genre_id");
		String[] branchIds = req.getParameterValues("branchId");

		Book book = new Book();
		BookDao bookDao = new BookDao(conn);
		try {
			// saveWithId as add books
			book.setBookId(Integer.parseInt(bId));
			bookDao.update(book, "pubId", Integer.parseInt(pubId));
			bookDao.update(book, "title", bookName);
			if (authorIds != null) {
				BookAuthorsDao baDao = new BookAuthorsDao(conn);
				baDao.delete(Integer.parseInt(bId));
				for (int i = 0; i < authorIds.length; i++) {
					BookAuthors bookAuthors = new BookAuthors();
					bookAuthors.setBookId(book.getBookId());
					bookAuthors.setAuthorId(Integer.parseInt(authorIds[i]));
					baDao.add(bookAuthors);
				}
			}
			if (genreIds != null && genreIds.length > 0) {
				BookGenresDao bgDao = new BookGenresDao(conn);
				bgDao.delete(Integer.parseInt(bId));
				for (int i = 0; i < genreIds.length; i++) {
					BookGenres bookGenres = new BookGenres();
					bookGenres.setBookId(book.getBookId());
					bookGenres.setGenreId(Integer.parseInt(genreIds[i]));
					bgDao.add(bookGenres);
				}
			}
			if (branchIds != null) {
				BookCopiesDao bookCopiesDao = new BookCopiesDao(conn);
				for (String branId : branchIds) {
					bookCopiesDao.update(Integer.parseInt(bId), Integer.parseInt(branId), 0);
				}
			}
			return "updateFinish.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			rollBack(conn);
			return INTERNAL_SERVER_ERROR;
		}
	}

	private String updateBook() {
		String bookId = req.getParameter("bookId");
		System.out.println(bookId);
		String title = req.getParameter("bookTitle");
		BookDao bDao = new BookDao(conn);
		BookAuthorsDao baDao = null;
		BookGenresDao bgDao = null;
		PublisherDao pubDao = null;
		AuthorDao auDao = null;
		GenreDao genDao = null;
		Book book;
		try {
			book = bDao.getSingle("bookId", Integer.parseInt(bookId));
			baDao = new BookAuthorsDao(conn);
			bgDao = new BookGenresDao(conn);
			pubDao = new PublisherDao(conn);
			auDao = new AuthorDao(conn);
			genDao = new GenreDao(conn);
			Publisher publisher = pubDao.getSingle("publisherId", book.getPubId());
			List<BookAuthors> baList = baDao.getList("bookId", book.getBookId());
			List<BookGenres> bgList = bgDao.getList("bookId", book.getBookId());
			List<Author> authors = auDao.getAll();
			List<Genre> genres = genDao.getAll();
			List<Publisher> publishers = pubDao.getAll();

			List<Author> authorList = new ArrayList<>();
			List<Genre> genreList = new ArrayList<>();

			for (BookAuthors bookAuthors : baList) {
				List<Author> aus = auDao.getList("authorId", bookAuthors.getAuthorId());
				authorList.add(aus.get(0));
			}
			for (BookGenres bookGenres : bgList) {
				List<Genre> gens = genDao.getList("genre_id", bookGenres.getGenreId());
				genreList.add(gens.get(0));
			}
			req.setAttribute("publishers", publishers);
			req.setAttribute("publisher", publisher);
			req.setAttribute("authors", authors);
			req.setAttribute("genres", genres);
			req.setAttribute("authorList", authorList);
			req.setAttribute("genreList", genreList);
			req.setAttribute("publisherId", publisher.getPublisherId());
			req.setAttribute("publisherName", publisher.getPublisherName());
			req.setAttribute("bookTitle", title);
			req.setAttribute("bookId", bookId);

			// new BookDao(conn).update(book, "title", title);
		} catch (NumberFormatException | IllegalAccessException | InvocationTargetException | InstantiationException
				| SQLException e) {
			e.printStackTrace();
		}
		return "/pages/book/detail.jsp";
	}

	private String deleteBook() {
		String bookId = req.getParameter("bookId");
		try {
			new BookDao(conn).delete(Integer.parseInt(bookId));
			return "deleteFinish.jsp";
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			return INTERNAL_SERVER_ERROR;
		}
	}

	private void rollBack(Connection conn) {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
