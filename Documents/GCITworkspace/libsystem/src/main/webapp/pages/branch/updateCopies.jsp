<%@include file="../../template.html"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.kailiang.lms.bean.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.kailiang.lms.dao.BookCopiesDao"%>
<%@page import="com.kailiang.lms.bean.BookCopies"%>
<%@page import="com.kailiang.lms.dao.BookDao"%>
<%@page import="com.kailiang.lms.bean.Branch"%>
<%@page import="com.kailiang.lms.dao.BranchDao"%>
<%
	String branchId = (String) request.getAttribute("branchId");
	String branchName = (String) request.getAttribute("branchName");
	String branchAddress = (String) request.getAttribute("branchAddress");
	List<BookCopies> list = (List<BookCopies>) request.getAttribute("list");
	Map<Integer, Book> map = (Map<Integer, Book>) request.getAttribute("map");
%>


<div class="container">
	<h2>Branch Details</h2>
	<hr>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">
			<%
				for (BookCopies bookCopy : list) {
			%>
			<hr>
			<form action="addBookCopies">
				<input type="hidden" name="branchId" class="form-control"
					value="<%=bookCopy.getBranchId()%>"> <input type="hidden"
					name="bookId" class="form-control"
					value="<%=bookCopy.getBookId()%>" required autofocus
					readonly="readonly"> <input type="text"
					class="form-control"
					value="<%=map.get(bookCopy.getBookId()).getTitle()%>"
					readonly="readonly"> <input type="text" name="noOfCopies"
					class="form-control" value="<%=bookCopy.getNoOfCopies()%>">
				<input type="hidden" name="operator" class="form-control"
					value="updateCopies"> <br>
				<%
					}
				%>
				<button type="submit">Confirm change copies</button>
			</form>

		</div>
		<div class="col-md-4"></div>
	</div>
</div>
