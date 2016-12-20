<%@include file="../../template.html"%>
<%
	String genre_id = request.getParameter("genre_id");
	String genre_name = request.getParameter("genre_name");
%>
	<div class="container">
	<h2>Author Details</h2>
		<hr>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<form class="form-signin" action="updateGenre">
					<input
						type="hidden" name="genre_id" value="<%=genre_id%>">
					<h5 class="form-signin-heading">Update</h5>
					<input type="text" id="inputBook" name="genre_name"
						class="form-control" value="<%=genre_name%>" required autofocus>
					<br>
					<button class="btn btn-lg btn-primary btn-block" type="submit">Update</button>
				</form>
			</div>
			<div class="col-md-4"></div>
		</div>
	</div>
