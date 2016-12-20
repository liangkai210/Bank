<%@include file="../../template.html"%>
<%
	String title = request.getParameter("title");
%>

<div class="container">
	<h2>Add Book</h2>
	<hr>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">
			<h4>
				ADD BOOK:
				<%=title%>
				Success!
			</h4>
			<a href="add.jsp" class="list-group-item list-group-item-success">
				Add another book</a><br> <a href="bookMenu.jsp"
				class="list-group-item list-group-item-danger"> Return to Main
				Menu</a>
		</div>
		<div class="col-md-4"></div>
	</div>
</div>
