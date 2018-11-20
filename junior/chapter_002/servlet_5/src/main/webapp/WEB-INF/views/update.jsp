<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %><html>
<html>
<head>
    <meta charset='UTF-8'>
    <title>Update User</title>
</head>
<body>
<form method='post'>
    <input type='hidden' name='action' value='update'>
    <input type='hidden' name='id' value=<c:out value="${id}"></c:out>>
    Name:
    <input type='text' name='name' value=<c:out value="${name}"></c:out>><br>
    Login:
    <input type='text' name='login' value=<c:out value="${login}"></c:out>><br>
    Email:
    <input type='text' name='email' value=<c:out value="${email}"></c:out>><br>
    <br>
    <br>
    <input type='submit' value='UPDATE'>
</form>
</body>
</html>
