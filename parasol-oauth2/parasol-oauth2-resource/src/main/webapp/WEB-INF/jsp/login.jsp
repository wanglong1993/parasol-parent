<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>金桐开放平台</title>
<link type="text/css" rel="stylesheet"
	href="webjars/bootstrap/3.0.3/css/bootstrap.min.css" />
<script type="text/javascript" src="webjars/jquery/1.9.0/jquery.min.js"></script>
<script type="text/javascript"
	src="webjars/bootstrap/3.0.3/js/bootstrap.min.js"></script>
</head>

<body>
	<h1>${client_id}</h1>
	<div class="container">
		<h1>金桐开放平台</h1>
		<c:if test="${not empty param.authentication_error}">
			<h1>Woops!</h1>
			<p class="error">Your login attempt was not successful.</p>
		</c:if>
		<c:if test="${not empty param.authorization_error}">
			<h1>Woops!</h1>
			<p class="error">You are not permitted to access that resource.</p>
		</c:if>
		<div class="form-horizontal">
			<p>We've got a grand total of 2 users: marissa and paul. Go ahead
				and log in. Marissa's password is "koala" and Paul's password is
				"emu".</p>
			<form action="/login.jhtml" method="post">
				<fieldset>
					<legend>
						<h2>Login</h2>
					</legend>
					<div class="form-group">
						<input type="text" placeholder="手机号/邮箱" id="login_pe_code"
							name="username" required="" value="foo">
					</div>
					<div class="form-group">
						<input type="password" maxlength="19" placeholder="登录密码"
							name="password" id="login_reg_pw" value="bar" required="">
					</div>
					<button class="btn btn-primary" type="submit">Login</button>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</fieldset>
			</form>

		</div>
		<div class="footer">
			<a href="http://www.gintong.com" target="_blank">金桐网</a>
		</div>

	</div>


</body>
</html>
