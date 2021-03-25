<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link href="https://fonts.googleapis.com/css?family=Roboto:300,400&display=swap" rel="stylesheet">

<link rel="stylesheet" href="fonts/icomoon/style.css">

<link rel="stylesheet" href="css/owl.carousel.min.css">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="css/bootstrap.min.css">

<!-- Style -->
<link rel="stylesheet" href="css/style.css">
<title>블로그</title>
</head>
<body>
	<div class="content">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-6 contents">
					<div class="row justify-content-center">
						<div class="col-md-12">
							<div class="form-block">
								<div class="mb-4">
									<h3>Sign Up</h3>
								</div>
								<form action="/join" method="post">
									<div class="form-group first">
										<label for="username">Username</label> 
										<input type="text" class="form-control" id="username" name="username" required="required">
									</div>
									<div class="form-group first">
										<label for="email">Email</label> 
										<input type="text" class="form-control" id="email" name="email">
									</div>
									<div class="form-group">
										<label for="password">Password</label> 
										<input type="password" class="form-control" id="password" name="password" required="required">
									</div>
									<div class="d-flex mb-5 align-items-center">					
										<span class="ml-auto"><a href="/loginForm" class="forgot-pass">Sign In</a></span>
									</div>

									<input type="submit" value="Register" class="btn btn-pill text-white btn-block btn-success"> 
									<span class="d-block text-center my-4 text-muted"> or register with</span>

									<div class="social-login text-center">
										<a href="/oauth2/authorization/facebook" class="facebook"> <span class="icon-facebook mr-3"></span></a> 
										<a href="/oauth2/authorization/naver" ><span><img src="images/naver.png"  width="50" height="50"></span></a> 
										<a href="/oauth2/authorization/kakao" ><span><img src="images/kakao.png"  width="50" height="50"></span></a> 
										<a href="/oauth2/authorization/google" class="google"> <span class="icon-google mr-3"></span></a>
									</div>
								</form>
							</div>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>


	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/popper.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/main.js"></script>
</body>
</html>