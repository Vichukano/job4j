<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %><html>

<html>
<head>
    <meta charset='UTF-8'>
    <title>Create User</title>
</head>
<body>
<form method='post' action="/create">
    <input type='hidden' name='action' value='add'>
    <input type='hidden' name='id' value=''>
    Login:
    <input type='text' name='login' value=''><br>
    Email:
    <input type='text' name='email' value=''><br>
    Password:
    <input type='text' name='password' value=''><br>
    Set role:
    <select name="role">
        <option value="Admin">Admin</option>
        <option value="User">User</option>
    </select>
    <br>
    <br>
    <input type='submit' value='SAVE'>
</form>
</body>
</html>
