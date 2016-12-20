<%@include file="../../template.html"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<div class="container">
<h2>Librarian Menu</h2>
	<hr>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">
			<div class="list-group-item list-group-item-success">
				<a
					href="http://localhost:8080/libsystem/pages/branch/branchMenu.jsp"
					class="list-group-item"> Operations on Branches</a> <br>
			</div>
		</div>
		<div class="col-md-4"></div>
	</div>
</div>
