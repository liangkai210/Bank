<%@include file="../../template.html" %>
<script type="text/javascript">
	function searchGenres() {

		$.ajax({
			url : "/libsystem/pages/genre/queryGenre",
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
<h2>Find Genre</h2>
	<hr>
	<div class="row">
		<div class="col-sm-4">
			<br> <br>
			<section class="col-sm-8">
				<h5 class="form-signin-heading">Find Genre</h5>
				<label for="inputBook" class="sr-only">Genre Name</label> <input
					onkeyup="searchGenres()" type="text" id="inputBook" name="genre_name"
					class="form-control" placeholder="genre_name" required autofocus>
				<br>
			</section>
		</div>
		<div class="col-sm-8" id="resultTable"></div>
	</div>
</div>
