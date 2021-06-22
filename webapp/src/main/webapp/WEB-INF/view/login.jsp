<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">

</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
			<a class="navbar-toggler" href="?lang=en"><img src="static/flags/flag_uk.png"/></a>
			<a class="navbar-toggler" href="?lang=fr"><img src="static/flags/flag_fr.png"/></a>        	
        </div>
    </header>
	<div>
		<form th:action="@{/login}" method="post" style="max-width: 400px; margin: 0 auto;">
		    <p>
		        Username : <input type="text" name="username" required />  
		    </p>
		    <p>
		        Password : <input type="password" name="password" required />
		    </p>
		    <p>
		        <input type="submit" value="Login" />
		    </p>
		</form>
	</div>
</div>
</body>
</html>