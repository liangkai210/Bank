<%@include file="../../template.html" %>
<%
	Integer cardNo = (Integer)request.getAttribute("cardNo");
%>
	<div class="container">
	<h2>Borrower Menu</h2>
		<hr>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
			<form action="/libsystem/pages/borrower/branches">
				<input type="hidden" name="cardNo" value="<%=cardNo%>">
				<button class="btn btn-lg btn-primary btn-block" type="submit">Borrow Book</button>
			</form>
			<br><br>
			<form action="/libsystem/pages/borrower/returnBook">
				<input type="hidden" name="cardNo" value="<%=cardNo%>">
				<button class="btn btn-lg btn-primary btn-block" type="submit">Return Book</button>
			</form>
			</div>
			<div class="col-md-4"></div>
		</div>
	</div>
