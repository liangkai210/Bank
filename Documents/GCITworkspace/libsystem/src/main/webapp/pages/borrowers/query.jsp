<%@include file="../../template.html" %>
<script type="text/javascript">
	function searchBorrowers() {

		$.ajax({
			url : "/libsystem/pages/borrowers/queryBorrower",
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
<h2>Find Borrower</h2>
	<hr>
	<div class="row">
		<div class="col-sm-4">
			<br> <br>
			<section class="col-sm-8">
				<h5 class="form-signin-heading">Find Borrower</h5>
				<label for="inputBook" class="sr-only">Borrower Name</label> <input
					onkeyup="searchBorrowers()" type="text" id="inputBook" name="name"
					class="form-control" placeholder="name" required autofocus>
				<br>
			</section>
		</div>
		<div class="col-sm-8" id="resultTable"></div>
	</div>
</div>
