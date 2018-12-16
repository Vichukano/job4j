<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <!--JQuery-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
    <!--javascript functions-->
    <script src="/scripts/scripts.js"></script>
    <title>User Login</title>
</head>
<body>
<div class="container">
    <br>
    <c:if test="${error != ''}">
        <div class="col-md-12 text-center">
            <h1><c:out value="${error}"></c:out></h1>
        </div>
    </c:if>
    <br>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <form method='post' action="/signin">
                <input type='hidden' name='action' value=''>
                <input type='hidden' name='id' value=''>
                <div class="form-group" id="login-group">
                    <label for="login">Login:</label>
                    <input type='text' class="form-control" name='login' id="login" value=''><br>
                </div>
                <div class="form-group" id="pass-group">
                    <label for="pass">Password:</label>
                    <input type='text' class="form-control" name='password' id="pass" value=''><br>
                </div>
                <div class="form-group">
                    <button class="btn btn-info" style="width: 100px" type="submit">Login</button>
                </div>
            </form>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <form method="get" action="/registration">
                <div class="form-group">
                    <button class="btn btn-info" style="width: 100px" type="submit">Registration</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="footer navbar-fixed-bottom text-right" style="background-color: lightcyan">
    <div class="col-md-11 text-right">
        <i class="glyphicon glyphicon-envelope"></i>
        <label>vichukano@gmail.com</label><br>
        <i class="glyphicon glyphicon-user"></i>
        <a href="https://vk.com/vichukano" target="_blank">ВКонтакте</a>
    </div>
</div>
</body>
</html>
