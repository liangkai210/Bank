<%@include file="../../template.html"%>
<%
	String cardNo = request.getParameter("cardNo");
	String name = request.getParameter("name");
	String address = request.getParameter("address");
	String phone = request.getParameter("phone");
%>
	<div class="container">
	<h2>Borrower Details</h2>
	<hr>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<form class="form-signin" action="updateBorrower">
					<input type="hidden" name="operator" value="update"> <input
						type="hidden" name="cardNo" value="<%=cardNo%>">
					<h5 class="form-signin-heading">Update</h5>
					<input type="text" id="inputBook" name="name"
						class="form-control" value="<%=name%>" required autofocus>
					<br> <input type="text" id="inputBook" name="address"
						class="form-control" value="<%=address%>" required
						autofocus> <br> <input type="text" id="inputBook"
						name="phone" class="form-control"
						value="<%=phone%>" required autofocus>
					<button class="btn btn-lg btn-primary btn-block" type="submit">Update</button>
				</form>
				<form class="form-signin" action="updateBorrower">
					<input type="hidden" name="operator" value="delete"> <input
						type="hidden" name="cardNo" value="<%=cardNo%>">
					<h5 class="form-signin-heading">Delete</h5>
					<input type="text" id="inputBook" name="name"
						class="form-control" value="<%=name%>" required autofocus
						readonly="readonly"> <br>
					<button class="btn btn-lg btn-primary btn-block" type="submit">Delete</button>
				</form>
			</div>
			<div class="col-md-4"></div>
		</div>
	</div>
