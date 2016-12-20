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
	String branchId = request.getParameter("branchId");
	String branchName = request.getParameter("branchName");
	String branchAddress = request.getParameter("branchAddress");
%>

<div class="container">
	<hr>
	<div class="row">
		<h3>Branch Details</h3>
		<div class="col-md-4"></div>
		<div class="col-md-4">

			<form class="form-signin" action="updateBranch">
				<input type="hidden" name="operator" value="update"> <input
					type="hidden" name="branchId" value="<%=branchId%>">
				<h5 class="form-signin-heading">Update</h5>
				<input type="text" name="branchName" class="form-control"
					value="<%=branchName%>" required autofocus> <br> <input
					type="text" name="branchAddress" class="form-control"
					value="<%=branchAddress%>" required autofocus> <br>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Update</button>
			</form>
			<form class="form-signin" action="updateBranch">
				<input type="hidden" name="operator" value="delete"> <input
					type="hidden" name="branchId" value="<%=branchId%>">
				<h5 class="form-signin-heading">Delete</h5>
				<input type="text" id="branchId" name="branchName"
					class="form-control" value="<%=branchName%>" required autofocus
					readonly="readonly"> <br>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Delete</button>
			</form>
		</div>
		<div class="col-md-4"></div>
	</div>
</div>
