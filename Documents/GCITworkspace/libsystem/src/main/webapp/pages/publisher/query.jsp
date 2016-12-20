<%@include file="../../template.html" %>
<script type="text/javascript">
	function searchPublishers() {

		$.ajax({
			url : "/libsystem/pages/publisher/queryPublisher",
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
<h2>Find Publisher</h2>
	<hr>
	<div class="row">
		<div class="col-sm-4">
			<br> <br>
			<section class="col-sm-8">
				<h5 class="form-signin-heading">Find Publisher</h5>
				<label for="inputBook" class="sr-only">Publisher Name</label> <input
					onkeyup="searchPublishers()" type="text" id="inputBook" name="publisherName"
					class="form-control" placeholder="publisherName" required autofocus>
				<br>
			</section>
		</div>
		<div class="col-sm-8" id="resultTable"></div>
	</div>
</div>
