<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
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
    <script type="text/javascript">
        <%@include file="/scripts/select.js"%>
    </script>
    <title>Create User</title>
</head>
<body>
<br>
<br>
<div class="container">
    <div class="col-md-3"></div>
    <div class="col-md-6">
        <p id="warning"></p>
        <form method='post' action="/create">
            <input type='hidden' name='action' value='add'>
            <input type='hidden' name='id' value=''>
            <div class="form-group" id="login-group">
                <label for="login">Login:</label>
                <input type='text' class="form-control" id="login" name='login' value=''><br>
            </div>
            <div class="form-group" id="email-group">
                <label for="email">Email:</label>
                <input type='text' class="form-control" name='email' id="email" value=''><br>
            </div>
            <div class="form-group" id="pass-group">
                <label for="pass">Password:</label>
                <input type='text' class="form-control" name='password' id="pass" value=''><br>
            </div>
            <div class="form-group" id="role-group">
                <label for="role">Choose role:</label>
            <select class="custom-select mr-sm-2" name="role" id="role">
                <option value="Admin">Admin</option>
                <option value="User">User</option>
            </select>
            </div>
            <div class="form-group" id="country-group">
                <label for="selectCountry">Choose country:</label>
                <select class="custom-select mr-sm-2" name ="country" id="selectCountry" required>
                    <!--Insert data dynamically-->
                </select>
            </div>
            <div class="form-group" id="city-group">
                <label for="selectCity">Choose city:</label>
                <select class="custom-select mr-sm-2" name="city" id="selectCity" required>
                    <option value="" disabled selected>Choose...</option>
                    <!--Insert data dynamically-->
                </select>
            </div>
            <br>
            <br>
            <div class="form-group">
                <button class="btn btn-info" id="submit" style="width: 100px" type="submit" onclick=" return validate()">
                    Create
                </button>
            </div>
        </form>
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
