<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Registration</title>
</head>
<body>
<c:if test="${error != ''}">
    <div>
        <h1><c:out value="${error}"></c:out></h1>
    </div>
</c:if>
<form method='post' action="/registration">
    <input type='hidden' name='action' value='add'><br>
    Login:
    <input type='text' name='login' value=''><br>
    Email:
    <input type='text' name='email' value=''><br>
    Password:
    <input type='password' name='password' value=''><br>
    Confirm password:
    <input type='password' name='confirm' value=''><br>
    <br>
    <br>
    <input type='submit' value='REGISTRATION'>
</form>
</body>
</html>
