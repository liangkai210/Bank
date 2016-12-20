<%@page import="java.text.SimpleDateFormat"%>
<%@include file="../../template.html"%>
<%@page import="java.util.Date"%>
<%@page import="com.kailiang.lms.bean.BookLoans"%>
<%@page import="java.util.Map"%>
<%@page import="com.kailiang.lms.bean.Borrower"%>
<%@page import="java.util.List"%>
<%
	List<Borrower> list = (List<Borrower>) request.getAttribute("list");
	Map<Integer, BookLoans> map = (Map<Integer, BookLoans>) request.getAttribute("map");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
%>
<div class="container">
	<h2>Borrowers</h2>
	<hr>
	<div class="row">
		<div class="col-md-4"></div>

		<div class="col-md-4">
			<form class="form-signin" action="operateDate.jsp">
				<h5 class="form-signin-heading"></h5>
				<h5>Borrowers</h5>
				<%
					for (Borrower borrower : list) {
						if (map.containsKey(borrower.getCardNo())) {
				%>
				<br> <input type="radio" name="cardNo"
					value="<%=borrower.getCardNo()%>"> <br> <input
					type="hidden" name="dueDate"
					value="<%=map.get(borrower.getCardNo()).getDueDate()%>"> <input
					type="hidden" name="dateOut"
					value="<%=map.get(borrower.getCardNo()).getDateOut()%>"> <input
					type="hidden" name="bookId"
					value="<%=map.get(borrower.getCardNo()).getBookId()%>"> <input
					type="hidden" name="branchId"
					value="<%=map.get(borrower.getCardNo()).getBranchId()%>">
				<%=borrower.getName()%>
				<br>
				<%=map.get(borrower.getCardNo()).getDueDate()%>
				<br>
				<%
					}
					}
				%>
				<br> <br>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Confirm</button>
			</form>
		</div>
		<div class="col-md-4"></div>
	</div>
</div>
