<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="en">
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
    <script type="text/javascript" src='<c:url value="/scripts/select.js"/>'></script>
    <title>User Registration</title>
</head>
<body>
<div class="container">
    <c:if test="${error != ''}">
        <br>
        <div class="col-md-12 text-center">
            <h1><c:out value="${error}"></c:out></h1>
        </div>
    </c:if>
    <div class="col-md-3"></div>
    <div class="col-md-6">
        <form method='post' action="/registration">
            <input type='hidden' name='action' value='add'><br>
            <div class="form-group" id="login-group">
                <label for="login">Login:</label>
                <input type='text' class="form-control" name='login' id="login" value=''><br>
            </div>
            <div class="form-group" id="email-group">
                <label for="email">Email:</label>
                <input type='text' class="form-control" name='email' id="email" value=''><br>
            </div>
            <div class="form-group" id="pass-group">
                <label for="pass">Password:</label>
                <input type='text' class="form-control" name='password' id="pass" value=''><br>
            </div>
            <div class="form-group" id="confirm-group">
                <label for="confirm">Confirm password:</label>
                <input type='text' class="form-control" name='confirm' id="confirm" value=''><br>
            </div>
            <div class="form-group" id="country-group">
                <label for="confirm">Choose country:</label>
                <select class="custom-select mr-sm-2" id="selectCountry">
                    <option selected>Choose...</option>
                    <option value="1">Russia</option>
                    <option value="2">USA</option>
                    <option value="3">Australia</option>
                </select>
            </div>
            <div class="form-group" id="city-group">
                <label for="confirm">Choose city:</label>
                <select class="custom-select mr-sm-2" id="selectCity">
                    <option selected>Choose...</option>
                    <!--Insert data dynamically-->
                </select>
            </div>
            <br>
            <div class="form-group">
                <button class="btn btn-info" style="width: 100px" type="submit">Registration</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
