<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Login</title>
</head>
<body>
<c:if test="${error != ''}">
    <div>
        <h1><c:out value="${error}"></c:out></h1>
    </div>
</c:if>
<form method='post' action="/signin">
    <input type='hidden' name='action' value=''>
    <input type='hidden' name='id' value=''>
    Login:
    <input type='text' name='login' value=''><br>
    Password:
    <input type='text' name='password' value=''><br>
    <br>
    <br>
    <input type='submit' value='LOGIN'>
</form>
<form method="get" action="/registration">
    <input type='submit' value='REGISTRATION'>
</form>
</body>
</html>
