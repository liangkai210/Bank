<%@include file="../../template.html"%>
<%
	String authorId = request.getParameter("authorId");
	String authorName = request.getParameter("authorName");
%>
	<div class="container">
	<h2>Author Details</h2>
		<hr>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<form class="form-signin" action="updateAuthor">
					<input type="hidden" name="operator" value="update"> <input
						type="hidden" name="authorId" value="<%=authorId%>">
					<h5 class="form-signin-heading">Update</h5>
					<input type="text" id="inputBook" name="authorName"
						class="form-control" value="<%=authorName%>" required autofocus>
					<br>
					<button class="btn btn-lg btn-primary btn-block" type="submit">Update</button>
				</form>
			</div>
			<div class="col-md-4"></div>
		</div>
		<br>
	<br>
	<br>
	<br><h4>
		<a href="authorMenu.jsp">Back to the main menu</a>
	</h4>
	</div>
