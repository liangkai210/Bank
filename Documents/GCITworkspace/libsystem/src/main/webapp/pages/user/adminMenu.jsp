<%@include file="../../template.html"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<div class="container">
	<h2>Administrator Menu</h2>
	<hr>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">

			<div class="list-group-item list-group-item-success">
				<a href="http://localhost:8080/libsystem/pages/book/bookMenu.jsp"
					class="list-group-item"> Operations on Books</a> <br> <a
					href="http://localhost:8080/libsystem/pages/publisher/publisherMenu.jsp"
					class="list-group-item"> Operations on Publishers</a> <br> <a
					href="http://localhost:8080/libsystem/pages/borrowers/borMenu.jsp"
					class="list-group-item"> Operations on Borrowers</a> <br> <a
					href="http://localhost:8080/libsystem/pages/author/authorMenu.jsp"
					class="list-group-item"> Operations on Authors</a> <br> <a
					href="http://localhost:8080/libsystem/pages/branch/branchMenu.jsp"
					class="list-group-item"> Operations on Branches</a> <br> <a
					href="http://localhost:8080/libsystem/pages/genre/genreMenu.jsp"
					class="list-group-item"> Operations on Genres</a>
			</div>

		</div>
		<div class="col-md-4"></div>
	</div>
</div>
