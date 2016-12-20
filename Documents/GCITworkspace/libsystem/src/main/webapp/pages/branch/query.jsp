<%@include file="../../template.html"%>
<div class="container">
	<h2>Find Branch</h2>
	<hr>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">
			<form class="form-signin" action="findBranch">
				<h5 class="form-signin-heading">Find Branch By Name</h5>
				<label for="inputBook" class="sr-only">Branch name</label> <input
					type="text" id="inputBook" name="branchName" class="form-control"
					placeholder="title" required autofocus> <br>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Confirm</button>
			</form>
			<form class="form-signin" action="allBranch">
				<input type="hidden" name="pageNo" value="1" />
				<h5 class="form-signin-heading">List All Branches</h5>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Confirm</button>
			</form>
		</div>
		<div class="col-md-4"></div>
	</div>
</div>
