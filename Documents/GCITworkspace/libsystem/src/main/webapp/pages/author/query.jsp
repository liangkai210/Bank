<%@include file="../../template.html" %>
<script type="text/javascript">
	function searchAuthors() {

		$.ajax({
			url : "/libsystem/pages/author/queryAuthor",
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
<h2>Find Author</h2>
	<hr>
	<div class="row">
		<div class="col-sm-4">
			<br> <br>
			<section class="col-sm-8">
				<h5 class="form-signin-heading">Find Author</h5>
				<label for="inputBook" class="sr-only">Author Name</label> <input
					onkeyup="searchAuthors()" type="text" id="inputBook" name="authorName"
					class="form-control" placeholder="authorName" required autofocus>
				<br>
			</section>
		</div>
		<div class="col-sm-8" id="resultTable"></div>
	</div>
	<br>
	<br>
	<br>
	<br><h4>
		<a href="authorMenu.jsp">Back to the main menu</a>
	</h4>
</div>
