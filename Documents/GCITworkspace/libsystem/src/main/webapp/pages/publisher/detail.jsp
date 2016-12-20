<%@include file="../../template.html"%>

<%
	String publisherId = request.getParameter("publisherId");
	String publisherName = request.getParameter("publisherName");
	String publisherAddress = request.getParameter("publisherAddress");
	String publisherPhone = request.getParameter("publisherPhone");
%>
	<div class="container">
	<h2>Publisher Details</h2>
	<hr>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<form class="form-signin" action="updatePublisher">
					<input type="hidden" name="operator" value="update"> <input
						type="hidden" name="publisherId" value="<%=publisherId%>">
					<h5 class="form-signin-heading">Update</h5>
					<input type="text" id="inputBook" name="publisherName"
						class="form-control" value="<%=publisherName%>" required autofocus>
					<br> <input type="text" id="inputBook" name="publisherAddress"
						class="form-control" value="<%=publisherAddress%>" required
						autofocus> <br> <input type="text" id="inputBook"
						name="publisherPhone" class="form-control"
						value="<%=publisherPhone%>" required autofocus>
						<br>
					<button class="btn btn-lg btn-primary btn-block" type="submit">Update</button>
				</form>
			</div>
			<div class="col-md-4"></div>
		</div>
	</div>
