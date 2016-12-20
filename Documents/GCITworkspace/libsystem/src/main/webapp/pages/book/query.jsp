<%@include file="../../template.html" %>
<script type="text/javascript">
	function searchBooks() {

		$.ajax({
			url : "/libsystem/pages/book/queryBook",
			data : {
				title : $('#inputBook').val(),
				isAjax : true
			}
		}).done(function(data) {
			console.log(data);
			$("#resultTable").html(data);
		});
	}
</script>
<div class="container">
<h2>Find Book</h2>
	<hr>
	<div class="row">
		<div class="col-sm-4">
			<br> <br>
			<section class="col-sm-8">
				<h5 class="form-signin-heading">Find Book By Title</h5>
				<label for="inputBook" class="sr-only">Book title</label> <input
					onkeyup="searchBooks()" type="text" id="inputBook" name="title"
					class="form-control" placeholder="title" required autofocus>
				<br>
			</section>
		</div>
		<div class="col-sm-8" id="resultTable"></div>
	</div>
	<br>
	<br>
	<br>
	<br><h4>
		<a href="bookMenu.jsp">Back to the main menu</a>
	</h4>
</div>
